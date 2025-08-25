package com.banking.transaction;

import com.banking.transaction.clients.AccountClient;
import com.banking.transaction.dto.AccountRequest;
import com.banking.transaction.dto.TransactionRequest;
import com.banking.transaction.enums.TransactionStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Transactional
@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final AccountClient accountClient;

    public TransactionService(TransactionRepo transactionRepo, AccountClient accountClient) {
        this.transactionRepo = transactionRepo;
        this.accountClient = accountClient;
    }

    public void saveTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setFromId(transactionRequest.fromId());
        transaction.setToId(transactionRequest.toId());
        transaction.setAmount(transactionRequest.amount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setType(transactionRequest.type());
        transaction.setDescription(transactionRequest.description());
        transaction.setIdempotencyKey(transactionRequest.idempotencyKey());
        transactionRepo.save(transaction);
    }

    public String initiateTransaction(TransactionRequest transactionRequest) {
        if(transactionRepo.existsByIdempotencyKey(transactionRequest.idempotencyKey())){
            return "Duplicate Transaction";
        }
        AccountRequest accountRequestFrom = accountClient.checkAccountExistsAndBalance(transactionRequest.fromId());
        AccountRequest accountRequestTo = accountClient.checkAccountExistsAndBalance(transactionRequest.toId());
        if(accountRequestFrom.exists()&&accountRequestTo.exists()){
            if(accountRequestFrom.balance().compareTo(transactionRequest.amount()) >= 0){
                saveTransaction(transactionRequest);
                return "Transaction Successful";
            } else {
                return "Insufficient Balance in the source account";
            }
        }
        return "One or both accounts do not exist";
    }

    public Iterable<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }
}
