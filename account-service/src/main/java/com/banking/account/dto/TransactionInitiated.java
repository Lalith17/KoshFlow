package com.banking.account.dto;

import com.banking.account.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionInitiated(
        Long transactionId,
        String fromId,
        String toId,
        BigDecimal amount,
        TransactionType type
) {
}
