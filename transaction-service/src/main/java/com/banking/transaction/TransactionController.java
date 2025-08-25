package com.banking.transaction;

import com.banking.transaction.dto.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransactionRequest transactionRequest){
        if(transactionRequest.idempotencyKey()==null || transactionRequest.idempotencyKey().isEmpty()){
            return ResponseEntity.badRequest().body("Idempotency Key is required");
        }
        if(transactionRequest.amount().compareTo(BigDecimal.valueOf(0))<=0) return ResponseEntity.badRequest().body("Amount should be greater than zero");
        return ResponseEntity.ok(transactionService.initiateTransaction(transactionRequest));
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Transaction>> getAllTransactions(){
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}
