package com.banking.notification.dto;

public record TransactionCompletedNotification(
        String name,
        String email,
        Long transactionId,
        String type,
        boolean success,
        String message
) {
}
