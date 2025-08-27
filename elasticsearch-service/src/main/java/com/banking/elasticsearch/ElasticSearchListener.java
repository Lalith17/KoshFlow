package com.banking.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticSearchListener {
    private final TransactionService transactionService;
    public ElasticSearchListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @KafkaListener(topics = "elasticsearch.initiated", groupId = "elasticsearch-service-group")
    public void processTransactionResponse(Transaction transaction) {
        log.info("Received transaction response at elastic search: {}", transaction.getTransactionId());
        transactionService.saveTransaction(transaction);
    }
}
