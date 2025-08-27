package com.banking.elasticsearch;

import org.springframework.stereotype.Service;

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
}
