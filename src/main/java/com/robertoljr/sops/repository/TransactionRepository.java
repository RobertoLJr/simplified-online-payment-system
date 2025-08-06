package com.robertoljr.sops.repository;

import com.robertoljr.sops.constant.transaction.Status;
import com.robertoljr.sops.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderId(Long senderId);

    List<Transaction> findByRecipientId(Long recipientId);

    List<Transaction> findByStatus(Status status);

    List<Transaction> findByCreatedAtBetween(Instant start, Instant end);
}
