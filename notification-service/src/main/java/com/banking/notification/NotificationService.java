package com.banking.notification;

import com.banking.notification.dto.NewUserCreated;
import com.banking.notification.dto.TransactionCompletedNotification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final JavaMailSender mailSender;
    private final String contactEmail = "mslalith17@gmail.com";

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTransactionNotification(TransactionCompletedNotification notificationRequest) {
        String subject = notificationRequest.type()+
                (notificationRequest.success() ? " Successful: " : " Failed: ");
        String body = "Dear " + notificationRequest.name() + ",\n\n" +
                "Your " + notificationRequest.type() + " transaction (ID: " + notificationRequest.transactionId() + ") was " +
                (notificationRequest.success() ? "successful." : "unsuccessful.") + "\n\n" +
                "Message: " + notificationRequest.message() + "\n\n";
        sendNotification(notificationRequest.email(), subject, body);
    }

    public void sendNewUserNotification(NewUserCreated newUserCreated) {
        String subject = "Welcome to KoshFlow Banking!";
        String body = "Dear " + newUserCreated.name() + ",\n\n" +
                "Welcome to KoshFlow Banking! We're thrilled to have you on board.\n\n" +
                "Here are some features you can explore:\n" +
                "- Secure and easy transactions\n" +
                "- 24/7 customer support\n" +
                "- Personalized financial insights\n\n";
        sendNotification(newUserCreated.email(), subject, body);
    }

    private void sendNotification(String email, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        body=body+
                "Thank you for choosing KoshFlow Banking. We look forward to serving you!\n" +
                "Best regards,\n" +
                "KoshFlow Banking Team"+
                "\n\n(Note: This is an automated message, please do not reply.)"+
                "\n\nFor support, contact us at "+ contactEmail;
        message.setText(body);
        mailSender.send(message);
    }

}
