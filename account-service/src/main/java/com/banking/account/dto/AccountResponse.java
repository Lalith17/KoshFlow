package com.banking.account.dto;

import java.math.BigDecimal;

public record AccountResponse(
        boolean exists,
        BigDecimal balance
) {
}
