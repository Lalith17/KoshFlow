package com.banking.transaction.dto;

import com.banking.transaction.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionInitiated(
        Long transactionId,
        Long userId,
        String fromId,
        String toId,
        BigDecimal amount,
        TransactionType type
) {
}
