package com.banking.transaction;

import com.banking.transaction.dto.TransactionCompletion;
import com.banking.transaction.dto.TransactionInitiated;
import com.banking.transaction.dto.TransactionRequest;
import com.banking.transaction.enums.TransactionStatus;
import com.banking.transaction.enums.TransactionType;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Transactional
@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final KafkaTemplate<String, TransactionInitiated> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(TransactionRepo transactionRepo, KafkaTemplate<String, TransactionInitiated> kafkaTemplate) {
        this.transactionRepo = transactionRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Transaction saveTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        String fromId = transactionRequest.fromId();
        String toId = transactionRequest.toId();
        if(fromId!=null&&toId!=null){
            transaction.setType(TransactionType.TRANSFER);
        }
        else if(fromId!=null){
            transaction.setType(TransactionType.WITHDRAWAL);
        }
        else if(toId!=null){
            transaction.setType(TransactionType.DEPOSIT);
        }
        transaction.setUserId(transactionRequest.userId());
        transaction.setUserEmail(transactionRequest.userEmail());
        transaction.setFromId(transactionRequest.fromId());
        transaction.setToId(transactionRequest.toId());
        transaction.setAmount(transactionRequest.amount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setDescription(transactionRequest.description());
        transaction.setIdempotencyKey(transactionRequest.idempotencyKey());
        return transactionRepo.save(transaction);
    }

    public String initiateTransaction(TransactionRequest transactionRequest) {
        if(transactionRepo.existsByIdempotencyKey(transactionRequest.idempotencyKey())){
            return "Duplicate Transaction";
        }
        Transaction transaction=saveTransaction(transactionRequest);
        try{
            TransactionInitiated transactionInitiated =new TransactionInitiated(
                    transaction.getTransactionId(),
                    transaction.getUserId(),
                    transaction.getFromId(),
                    transaction.getToId(),
                    transaction.getAmount(),
                    transaction.getType()
            );
            kafkaTemplate.send("transaction.initiated", transactionInitiated);
            logger.info("Sent transaction to Kafka: {}", transactionInitiated.transactionId());
        }
        catch (Exception e){
            logger.error("Failed to send transaction to Kafka", e);
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepo.save(transaction);
            return "Transaction Failed";
        }
        return "Transaction Initiated";
    }

    public Iterable<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public void updateTransactionStatus(TransactionCompletion status) {
        Transaction transaction = transactionRepo.findById(status.transactionId()).orElseThrow(() -> new RuntimeException("Transaction not found"));
        if(status.success()) transaction.setStatus(TransactionStatus.COMPLETED);
        else transaction.setStatus(TransactionStatus.FAILED);
        transactionRepo.save(transaction);
    }
}
