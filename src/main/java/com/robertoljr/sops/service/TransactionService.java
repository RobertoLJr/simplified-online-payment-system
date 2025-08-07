package com.robertoljr.sops.service;

import com.robertoljr.sops.constant.transaction.Status;
import com.robertoljr.sops.dto.transaction.CreateTransactionDTO;
import com.robertoljr.sops.dto.transaction.ResponseTransactionDTO;
import com.robertoljr.sops.dto.transaction.UpdateStatusDTO;

import java.time.Instant;
import java.util.List;

public interface TransactionService {

    ResponseTransactionDTO createTransaction(CreateTransactionDTO dto);

    List<ResponseTransactionDTO> findAllTransactions();

    ResponseTransactionDTO findTransactionById(Long id);

    List<ResponseTransactionDTO> findTransactionsBySenderId(Long senderId);

    List<ResponseTransactionDTO> findTransactionsByRecipientId(Long recipientId);

    List<ResponseTransactionDTO> findTransactionsByStatus(Status status);

    List<ResponseTransactionDTO> findTransactionsByCreatedAtBetween(Instant start, Instant end);

    ResponseTransactionDTO updateStatus(Long id, UpdateStatusDTO status);

    void deleteTransaction(Long id);
}
