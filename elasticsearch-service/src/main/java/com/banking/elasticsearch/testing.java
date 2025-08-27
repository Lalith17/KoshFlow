package com.banking.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class testing {
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public testing(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(Transaction transaction) {
        transaction.setTimestamp(Instant.now());
        kafkaTemplate.send("elasticsearch.completed", transaction);
        log.info("Sent transaction to topic: {}", transaction.getTransactionId());
    }
}
