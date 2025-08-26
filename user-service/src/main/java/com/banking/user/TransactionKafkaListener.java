package com.banking.user;

import com.banking.user.dto.TransactionCompletion;
import com.banking.user.rabbitmq.NotificationProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionKafkaListener {
    private final NotificationProducer notificationProducer;
    public TransactionKafkaListener(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }
    @KafkaListener(topics = "transaction.completed", groupId = "user-service-group")
    public void processTransactionResponse(TransactionCompletion status) {
        log.info("Received transaction response at User-Service: {}", status.transactionId());
        notificationProducer.sendNotification(status);
    }
}
