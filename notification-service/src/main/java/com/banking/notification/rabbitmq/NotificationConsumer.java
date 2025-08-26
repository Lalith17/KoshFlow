package com.banking.notification.rabbitmq;

import com.banking.notification.NotificationService;
import com.banking.notification.dto.NotificationDto;
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
    public void consumeMessage(NotificationDto notificationDto) {
        log.info("Message received from sendNotificationQueue: {}", notificationDto);
        notificationService.sendNotification(notificationDto);
    }
}
