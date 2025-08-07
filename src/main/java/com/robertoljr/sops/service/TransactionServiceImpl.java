package com.robertoljr.sops.service;

import com.robertoljr.sops.constant.transaction.Status;
import com.robertoljr.sops.constant.user.UserType;
import com.robertoljr.sops.dto.transaction.CreateTransactionDTO;
import com.robertoljr.sops.dto.transaction.ResponseTransactionDTO;
import com.robertoljr.sops.dto.transaction.UpdateStatusDTO;
import com.robertoljr.sops.dto.user.UserResponseDTO;
import com.robertoljr.sops.entity.Transaction;
import com.robertoljr.sops.exception.transaction.TransactionCreationException;
import com.robertoljr.sops.exception.transaction.TransactionNotAllowedException;
import com.robertoljr.sops.exception.transaction.TransactionNotFoundException;
import com.robertoljr.sops.exception.transaction.TransactionUpdateStatusException;
import com.robertoljr.sops.mapper.TransactionMapper;
import com.robertoljr.sops.repository.TransactionRepository;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserService userService;
    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, UserService userService, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public ResponseTransactionDTO createTransaction(CreateTransactionDTO dto) {
        logger.info("Creating transaction with sender id: {}", dto.getSenderId());
        logger.info("Creating transaction with recipient id: {}", dto.getRecipientId());

        if (!isTransactionValid(dto)) {
            logger.error("Invalid transaction.");
        }

        try {
            Transaction transaction = transactionMapper.toEntity(dto);

            if (isTransactionAuthorized(dto)) {
                logger.info("Transaction authorized.");
                transaction.setStatus(Status.SUCCEEDED);
            } else {
                logger.info("Transaction not authorized.");
                transaction.setStatus(Status.FAILED);
            }

            transaction = transactionRepository.save(transaction);
            return transactionMapper.toResponseDTO(transaction);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Data integrity violation exception:", ex);
            throw new TransactionCreationException("An unexpected error occurred while creating the transaction.");
        } catch (Exception ex) {
            logger.error("Exception:", ex);
            throw new TransactionCreationException("An unexpected error occurred while creating the transaction.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseTransactionDTO> findAllTransactions() {
        logger.info("Retrieving all transactions");

        return transactionRepository.findAll().stream()
                .map(transactionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseTransactionDTO findTransactionById(Long id) {
        logger.info("Retrieving transaction with id {}", id);

        Transaction transaction = getTransactionOrThrow(id);
        return transactionMapper.toResponseDTO(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseTransactionDTO> findTransactionsBySenderId(Long senderId) {
        logger.info("Retrieving transactions by sender id {}", senderId);

        return transactionRepository.findBySenderId(senderId).stream()
                .map(transactionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseTransactionDTO> findTransactionsByRecipientId(Long recipientId) {
        logger.info("Retrieving transactions by recipient id {}", recipientId);

        return transactionRepository.findByRecipientId(recipientId).stream()
                .map(transactionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseTransactionDTO> findTransactionsByStatus(Status status) {
        logger.info("Retrieving transactions by status {}", status);

        return transactionRepository.findByStatus(status).stream()
                .map(transactionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseTransactionDTO> findTransactionsByCreatedAtBetween(Instant start, Instant end) {
        logger.info("Retrieving transactions by createdAt between {} and {}", start, end);

        return transactionRepository.findByCreatedAtBetween(start, end).stream()
                .map(transactionMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public ResponseTransactionDTO updateStatus(Long id, UpdateStatusDTO dto) {
        logger.info("Updating status for transaction id {}", id);

        Status newStatus;
        try {
            newStatus = Status.valueOf(dto.getStatus().toUpperCase());
        } catch (IllegalArgumentException ex) {
            logger.error("Invalid status value: {}", dto.getStatus());
            throw new TransactionUpdateStatusException("Invalid status value: " + dto.getStatus());
        }

        Transaction dbTransaction = getTransactionOrThrow(id);
        dbTransaction.setStatus(newStatus);
        dbTransaction = transactionRepository.save(dbTransaction);
        logger.info("Updated status for transaction id {} to {}", id, newStatus);
        return transactionMapper.toResponseDTO(dbTransaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long id) {
        logger.info("Deleting transaction with id {}", id);

        Transaction dbTransaction = getTransactionOrThrow(id);

        transactionRepository.delete(dbTransaction);
        logger.info("Deleted transaction with id {}", id);
    }

    private Transaction getTransactionOrThrow(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> {
                    logger.error("Transaction not found for id: {}", transactionId);
                    return new TransactionNotFoundException("Transaction not found for id: " + transactionId);
                });
    }

    private boolean isTransactionValid(CreateTransactionDTO dto) {
        logger.info("Validating transaction with sender id: {}", dto.getSenderId());

        // Check if the sender and recipient ids are valid
        UserResponseDTO dbSenderUser = userService.findUserById(dto.getSenderId());
        UserResponseDTO dbRecipientUser = userService.findUserById(dto.getRecipientId());

        // Check if the sender is MERCHANT -- not allowed to make transfers
        if (dbSenderUser.userType().equals(UserType.MERCHANT)) {
            throw new TransactionNotAllowedException("Sender is a MERCHANT -- not allowed to make transfers.");
        }

        // Check if the sender and recipient ids are the same
        if (dbSenderUser.id().equals(dbRecipientUser.id())) {
            throw new TransactionCreationException("Sender and recipient ids are the same.");
        }

        // Check if the sender has enough balance to make the transfer
        if (dbSenderUser.balance().compareTo(dto.getAmount()) < 0) {
            throw new TransactionCreationException("Sender does not have enough balance to make the transfer.");
        }

        return true;
    }

    private boolean isTransactionAuthorized(CreateTransactionDTO dto) {
        logger.info("Authorizing transaction with sender id: {}", dto.getSenderId());

        try {
            ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

            return authorizationResponse.getStatusCode() == HttpStatus.OK && authorizationResponse.getBody().get("status").equals("success");
        } catch (HttpClientErrorException.Forbidden ex) {
            logger.error("Forbidden exception: {}", ex.getMessage());
            return false;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logger.error("Exception: {}", ex.getMessage());
            throw new TransactionCreationException("An unexpected error occurred while authorizing the transaction.");
        } catch (Exception ex) {
            logger.error("Exception: ", ex);
            throw new TransactionCreationException("An unexpected error occurred while authorizing the transaction.");
        }
    }
}
