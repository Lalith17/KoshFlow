package com.banking.elasticsearch;

import com.banking.elasticsearch.enums.TransactionStatus;
import com.banking.elasticsearch.enums.TransactionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@Document(indexName = "transactions")
public class Transaction {
    @Id
    private Long transactionId;
    private Long userId;
    private String fromId;
    private String toId;
    private BigDecimal amount;
    @Field(type = FieldType.Date)
    private Instant timestamp;
    private TransactionStatus status;
    private TransactionType type;
    private String description;
    private String idempotencyKey;
}
