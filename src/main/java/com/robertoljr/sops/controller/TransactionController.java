package com.robertoljr.sops.controller;

import com.robertoljr.sops.constant.transaction.Status;
import com.robertoljr.sops.controller.docs.TransactionControllerDocs;
import com.robertoljr.sops.dto.transaction.CreateTransactionDTO;
import com.robertoljr.sops.dto.transaction.ResponseTransactionDTO;
import com.robertoljr.sops.dto.transaction.UpdateStatusDTO;
import com.robertoljr.sops.dto.user.*;
import com.robertoljr.sops.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController implements TransactionControllerDocs {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<ResponseTransactionDTO> createTransaction(@Valid @RequestBody CreateTransactionDTO transactionCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transactionCreateDTO));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<ResponseTransactionDTO>> findAllTransactions() {
        return ResponseEntity.ok(transactionService.findAllTransactions());
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<ResponseTransactionDTO> findTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findTransactionById(id));
    }

    @GetMapping(value = "/senderId/{senderId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<ResponseTransactionDTO>> findTransactionBySenderId(@PathVariable Long senderId) {
        return ResponseEntity.ok(transactionService.findTransactionsBySenderId(senderId));
    }

    @GetMapping(value = "/recipientId/{recipientId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<ResponseTransactionDTO>> findTransactionByRecipientId(@PathVariable Long recipientId) {
        return ResponseEntity.ok(transactionService.findTransactionsByRecipientId(recipientId));
    }

    @PutMapping(value = "/{status}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<ResponseTransactionDTO>> findTransactionsByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(transactionService.findTransactionsByStatus(status));
    }

    @PutMapping(value = "/after/{start}/before/{end}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<ResponseTransactionDTO>> findTransactionsByCreatedAtBetween(@PathVariable Instant start, @PathVariable Instant end) {
        return ResponseEntity.ok(transactionService.findTransactionsByCreatedAtBetween(start, end));
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<ResponseTransactionDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusDTO status) {
        return ResponseEntity.ok(transactionService.updateStatus(id, status));
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
