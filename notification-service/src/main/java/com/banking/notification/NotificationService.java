package com.banking.notification;

import com.banking.notification.dto.NotificationDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(NotificationDto notificationRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationRequest.email());
        String subject = notificationRequest.type()+
                (notificationRequest.success() ? " Successful: " : " Failed: ");
        String body = "Dear " + notificationRequest.name() + ",\n\n" +
                "Your " + notificationRequest.type() + " transaction (ID: " + notificationRequest.transactionId() + ") was " +
                (notificationRequest.success() ? "successful." : "unsuccessful.") + "\n\n" +
                "Message: " + notificationRequest.message() + "\n\n" +
                "Thank you for banking with us.\n" +
                "Best regards,\n" +
                "KoshFlow Banking Team"+
                "\n\n(Note: This is an automated message, please do not reply.)"+
                "\n\nFor support, contact us at mslalith17@gmail.com";
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

}
