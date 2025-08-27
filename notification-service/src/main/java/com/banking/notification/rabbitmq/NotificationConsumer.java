package com.banking.notification.rabbitmq;

import com.banking.notification.NotificationService;
import com.banking.notification.dto.NewUserCreated;
import com.banking.notification.dto.TransactionCompletedNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "sendNotificationQueue")
    public void setTransactionCompletedNotification(TransactionCompletedNotification transactionCompletedNotification) {
        log.info("Message received from sendNotificationQueue: {}", transactionCompletedNotification);
        notificationService.sendTransactionNotification(transactionCompletedNotification);
    }

    @RabbitListener(queues = "newUserCreatedQueue")
    public void setNewUserCreated(NewUserCreated newUserCreated) {
        log.info("Message received from NewUserCreated queue: {}", newUserCreated);
        notificationService.sendNewUserNotification(newUserCreated);
    }
}
