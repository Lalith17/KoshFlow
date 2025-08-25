package com.banking.account;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String accountNumber;
    private Long userId;
    private String accountType;
    private BigDecimal balance;
}
