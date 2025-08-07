package com.robertoljr.sops.repository;

import com.robertoljr.sops.constant.notification.Status;
import com.robertoljr.sops.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long recipientId);

    List<Notification> findByTransactionId(Long transactionId);

    List<Notification> findByStatus(Status status);

    List<Notification> findByCreatedAtBetween(Instant start, Instant end);
}
