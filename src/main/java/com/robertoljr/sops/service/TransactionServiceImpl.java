package com.robertoljr.sops.service;

import com.robertoljr.sops.constant.transaction.Status;
import com.robertoljr.sops.dto.transaction.CreateTransactionDTO;
import com.robertoljr.sops.dto.transaction.ResponseTransactionDTO;
import com.robertoljr.sops.entity.Transaction;
import com.robertoljr.sops.exception.transaction.TransactionCreationException;
import com.robertoljr.sops.exception.transaction.TransactionNotFoundException;
import com.robertoljr.sops.mapper.TransactionMapper;
import com.robertoljr.sops.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    @Transactional
    public ResponseTransactionDTO createTransaction(CreateTransactionDTO dto) {
        logger.info("Creating transaction with sender id: {}", dto.getSenderId());
        logger.info("Creating transaction with recipient id: {}", dto.getRecipientId());

        try {
            Transaction transaction = transactionMapper.toEntity(dto);
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
    public ResponseTransactionDTO updateStatus(Long id, Status status) {
        logger.info("Updating status for transaction id {}", id);

        Transaction transaction = getTransactionOrThrow(id);
        transaction.setStatus(status);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toResponseDTO(transaction);
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
}
