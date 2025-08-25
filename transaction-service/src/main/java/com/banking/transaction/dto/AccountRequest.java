package com.banking.transaction.dto;

import java.math.BigDecimal;

public record AccountRequest(
        boolean exists,
        BigDecimal balance
) {
}
