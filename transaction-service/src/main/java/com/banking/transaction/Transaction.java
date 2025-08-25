package com.banking.transaction;

import com.banking.transaction.enums.TransactionStatus;
import com.banking.transaction.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    private String fromId;
    private String toId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransactionStatus status;
    private TransactionType type;
    private String description;
    private String idempotencyKey;
}
