package com.banking.account;

import com.banking.account.dto.TransactionCompletion;
import com.banking.account.dto.TransactionInitiated;
import com.banking.account.enums.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class AccountService {
    private final AccountRepo accountRepo;
    private final KafkaTemplate<String, TransactionCompletion> kafkaTemplate;

    public AccountService(AccountRepo accountRepo, KafkaTemplate<String, TransactionCompletion> kafkaTemplate) {
        this.kafkaTemplate=kafkaTemplate;
        this.accountRepo = accountRepo;
    }

    public Account createAccount(Account account) {
        return accountRepo.save(account);
    }

    public Optional<Account> getAccount(String accountNumber) {
        return accountRepo.findById(accountNumber);
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public Iterable<Account> getAccountByUserId(Long UserId) {
        return accountRepo.findByUserId(UserId);
    }

    public void processTransaction(TransactionInitiated transactionInitiated) {
        TransactionType type = transactionInitiated.type();
        try {
            switch (type) {
                case TRANSFER: {
                    transfer(transactionInitiated);
                    break;
                }
                case WITHDRAWAL: {
                    withdraw(transactionInitiated);
                    break;
                }
                case DEPOSIT: {
                    deposit(transactionInitiated);
                    break;
                }
            }
        } catch (RuntimeException e) {
            publishOutcome(transactionInitiated, false, e.getMessage());
        }
    }

    private void transfer(TransactionInitiated transactionInitiated){
        Account sender = accountRepo.findByIdForUpdate(transactionInitiated.fromId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Account receiver = accountRepo.findByIdForUpdate(transactionInitiated.toId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        if (sender.getBalance().compareTo(transactionInitiated.amount()) < 0) {
            publishOutcome(transactionInitiated, false, "Insufficient balance");
            return;
        }
        sender.setBalance(sender.getBalance().subtract(transactionInitiated.amount()));
        receiver.setBalance(receiver.getBalance().add(transactionInitiated.amount()));
        accountRepo.save(sender);
        accountRepo.save(receiver);
        publishOutcome(transactionInitiated, true, "Transfer completed successfully");
    }

    private void withdraw(TransactionInitiated transactionInitiated){
        Account sender = accountRepo.findByIdForUpdate(transactionInitiated.fromId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (sender.getBalance().compareTo(transactionInitiated.amount()) < 0) {
            publishOutcome(transactionInitiated, false, "Insufficient balance");
            return;
        }
        sender.setBalance(sender.getBalance().subtract(transactionInitiated.amount()));
        accountRepo.save(sender);
        publishOutcome(transactionInitiated, true, "Withdrawal completed successfully");
    }

    private void deposit(TransactionInitiated transactionInitiated){
        Account receiver = accountRepo.findByIdForUpdate(transactionInitiated.toId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        receiver.setBalance(receiver.getBalance().add(transactionInitiated.amount()));
        accountRepo.save(receiver);
        publishOutcome(transactionInitiated, true, "Deposit completed successfully");
    }

    private void publishOutcome(TransactionInitiated transactionInitiated, boolean success, String reason) {
        TransactionCompletion transactionCompletion = new TransactionCompletion(transactionInitiated.transactionId(),
                transactionInitiated.type(),
                success,
                reason);
        kafkaTemplate.send("transaction.completed", transactionCompletion);
    }
}
