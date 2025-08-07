package com.robertoljr.sops.repository;

import com.robertoljr.sops.constant.transaction.Status;
import com.robertoljr.sops.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationRepository, Long> {
    List<Notification> findByStatus(Status status);

    List<Notification> findByCreatedAtBetween(Instant start, Instant end);
}
