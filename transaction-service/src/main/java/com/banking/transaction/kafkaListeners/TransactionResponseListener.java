package com.banking.transaction.kafkaListeners;

import com.banking.transaction.TransactionService;
import com.banking.transaction.dto.TransactionCompletion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionResponseListener {

    private final TransactionService transactionService;
    public TransactionResponseListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @KafkaListener(topics = "transaction.completed", groupId = "transaction-service-group")
    public void processTransactionResponse(TransactionCompletion status) {
        log.info("Received transaction response: {}", status.transactionId());
        transactionService.updateTransactionStatus(status);
    }
}
