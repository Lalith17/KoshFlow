package com.banking.account.dto;

import com.banking.account.enums.TransactionType;

public record TransactionCompletion(
        Long transactionId,
        Long userId,
        TransactionType type,
        boolean success,
        String message
) {
}
