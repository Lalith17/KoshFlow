package com.banking.user.dto;

public record TransactionCompletion(
        Long transactionId,
        Long userId,
        String type,
        boolean success,
        String message
) {
}
