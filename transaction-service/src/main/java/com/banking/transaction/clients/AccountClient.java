package com.banking.transaction.clients;


import com.banking.transaction.dto.AccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {
    @GetMapping("/accounts/check/{accountNumber}")
    AccountRequest checkAccountExistsAndBalance(@PathVariable("accountNumber") String accountNumber);
}
