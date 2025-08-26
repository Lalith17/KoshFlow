package com.banking.transaction.dto;

import java.math.BigDecimal;

public record TransactionRequest(
        Long userId,
        String userEmail,
        String fromId,
        String toId,
        BigDecimal amount,
        String description,
        String idempotencyKey
) {
}
