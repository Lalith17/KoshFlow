package com.banking.transaction.dto;

import com.banking.transaction.enums.TransactionType;

public record TransactionCompletion(
        Long transactionId,
        TransactionType type,
        boolean success,
        String message
) {
}
