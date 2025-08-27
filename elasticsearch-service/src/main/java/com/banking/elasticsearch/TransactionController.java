package com.banking.elasticsearch;

import com.banking.elasticsearch.enums.TransactionStatus;
import com.banking.elasticsearch.enums.TransactionType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/elasticsearch")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/user/{userId}")
    public Iterable<Transaction> getAllUserTransactions(@PathVariable Long userId) {
        return transactionService.getAllUserTransactions(userId);
    }

    @GetMapping("/all")
    public Iterable<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/recent/{userId}")
    public Iterable<Transaction> getRecentTransactions(@PathVariable Long userId) {
        return transactionService.getRecentTransactions(userId);
    }

    @GetMapping("/status")
    public Iterable<Transaction> getTransactionsByStatus(@RequestParam TransactionStatus status) {
        return transactionService.getTransactionsByStatus(status);
    }

    @GetMapping("/type")
    public Iterable<Transaction> getTransactionsByType(@RequestParam TransactionType type) {
        return transactionService.getTransactionsByType(type);
    }

    @GetMapping("/amount-range")
    public Iterable<Transaction> getTransactionsByAmountRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return transactionService.getTransactionsByAmountRange(min, max);
    }
}