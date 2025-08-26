package com.banking.user.rabbitmq;

import com.banking.user.User;
import com.banking.user.UserService;
import com.banking.user.dto.NotificationDto;
import com.banking.user.dto.TransactionCompletion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;

    public NotificationProducer(RabbitTemplate rabbitTemplate, UserService userService) {
        this.rabbitTemplate = rabbitTemplate;
        this.userService = userService;
    }

    public void sendNotification(TransactionCompletion transactionCompletion){
        Optional<User> user= userService.getUserById(transactionCompletion.userId());
        if(user.isEmpty()){
            return;
        }
        NotificationDto notificationDto = new NotificationDto(
                user.get().getName(),
                user.get().getEmail(),
                transactionCompletion.transactionId(),
                transactionCompletion.type(),
                transactionCompletion.success(),
                transactionCompletion.message()
        );
        rabbitTemplate.convertAndSend("sendNotificationQueue",notificationDto);
        log.info("Notification sent to RabbitMQ for user: {}", user.get().getEmail());
    }
}
