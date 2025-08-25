package com.banking.transaction.dto;

import com.banking.transaction.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionRequest(
        String fromId,
        String toId,
        BigDecimal amount,
        TransactionType type,
        String description,
        String idempotencyKey
) {
}
