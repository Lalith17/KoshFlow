package com.banking.elasticsearch;

import com.banking.elasticsearch.enums.TransactionStatus;
import com.banking.elasticsearch.enums.TransactionType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface TransactionRepo extends ElasticsearchRepository<Transaction, Long> {

    // Get all transactions for a user
    List<Transaction> findByUserId(Long userId);

    // Get latest 10 transactions for a user
    List<Transaction> findTop10ByUserIdOrderByTimestampDesc(Long userId);

    // Search by status
    List<Transaction> findByStatus(TransactionStatus status);

    // Search by type
    List<Transaction> findByType(TransactionType type);

    // Search by amount range
    List<Transaction> findByAmountBetween(BigDecimal min, BigDecimal max);

    // Search by time range
    List<Transaction> findByTimestampBetween(Instant start, Instant end);
}
