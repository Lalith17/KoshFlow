package com.banking.user.dto;

public record NotificationDto(
        String name,
        String email,
        Long transactionId,
        String type,
        boolean success,
        String message
) {
}
