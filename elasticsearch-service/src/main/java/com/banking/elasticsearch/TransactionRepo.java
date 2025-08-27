package com.banking.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TransactionRepo extends ElasticsearchRepository<Transaction, Long> {
    Iterable<Transaction> findByUserId(Long userId);
}
