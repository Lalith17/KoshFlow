package com.banking.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elasticsearch")
public class TransactionController {

    private final TransactionService transactionService;
    private final testing test;

    public TransactionController(TransactionService transactionService, testing test) {
        this.transactionService = transactionService;
        this.test = test;
    }

    @GetMapping("/userId")
    public Iterable<Transaction> getAllUserTransactions(@PathVariable Long userId) {
        return transactionService.getAllUserTransactions(userId);
    }

    @GetMapping("/")
    public Iterable<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping("/testing")
    public void createTransaction(@RequestBody Transaction transaction) {
        test.sendTransaction(transaction);
    }
}
