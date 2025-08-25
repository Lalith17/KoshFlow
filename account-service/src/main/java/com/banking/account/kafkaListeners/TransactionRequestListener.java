package com.banking.account.kafkaListeners;

import com.banking.account.AccountService;
import com.banking.account.dto.TransactionInitiated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionRequestListener {

    private final AccountService accountService;

    public TransactionRequestListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @KafkaListener(topics = "transaction.initiated", groupId = "account-service-group")
    public void processTransactionRequest(TransactionInitiated transactionInitiated) {
        log.info("Received transaction request: {}", transactionInitiated.transactionId());
        accountService.processTransaction(transactionInitiated);
    }
}
