package com.banking.account;

import com.banking.account.dto.AccountResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class AccountService {
    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account createAccount(Account account) {
        return accountRepo.save(account);
    }

    public Optional<Account> getAccount(String accountNumber) {
        return accountRepo.findById(accountNumber);
    }

    public void deleteAccount(String accountNumber) {
        accountRepo.deleteById(accountNumber);
    }

    public Account updateAccount(String accountNumber, Account updateRequest) {
        Optional<Account> optionalAccount = accountRepo.findById(accountNumber);
        if (optionalAccount.isEmpty()) {
            throw new RuntimeException("Account not found with number: " + accountNumber);
        }
        Account account = optionalAccount.get();
        account.setAccountType(updateRequest.getAccountType());
        account.setBalance(updateRequest.getBalance());
        return accountRepo.save(account);
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public Optional<Account> getAccountByUserId(Long UserId) {
        return Optional.ofNullable(accountRepo.findByUserId(UserId));
    }

    public AccountResponse checkAccountExistsAndBalance(String accountNumber) {
        Optional<Account> optionalAccount = accountRepo.findById(accountNumber);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            return new AccountResponse(true, account.getBalance());
        } else {
            return new AccountResponse(false, null);
        }
    }
}
