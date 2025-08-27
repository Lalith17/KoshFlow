package com.banking.elasticsearch;

import com.banking.elasticsearch.enums.TransactionStatus;
import com.banking.elasticsearch.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;

    public TransactionService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepo.save(transaction);
    }

    public Iterable<Transaction> getAllUserTransactions(Long userId) {
        return transactionRepo.findByUserId(userId);
    }

    public Iterable<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public Iterable<Transaction> getRecentTransactions(Long userId) {
        return transactionRepo.findTop10ByUserIdOrderByTimestampDesc(userId);
    }

    public Iterable<Transaction> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepo.findByStatus(status);
    }

    public Iterable<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepo.findByType(type);
    }

    public Iterable<Transaction> getTransactionsByAmountRange(BigDecimal min, BigDecimal max) {
        return transactionRepo.findByAmountBetween(min, max);
    }
}