package com.robertoljr.sops.service;

import com.robertoljr.sops.constant.notification.Status;
import com.robertoljr.sops.dto.notification.NotificationCreateDTO;
import com.robertoljr.sops.dto.notification.NotificationResponseDTO;
import com.robertoljr.sops.dto.notification.NotificationUpdateStatusDTO;
import com.robertoljr.sops.dto.transaction.CreateTransactionDTO;
import com.robertoljr.sops.dto.transaction.ResponseTransactionDTO;
import com.robertoljr.sops.dto.transaction.UpdateStatusDTO;
import com.robertoljr.sops.entity.Notification;

import java.time.Instant;
import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationCreateDTO dto);

    List<NotificationResponseDTO> findAllNotifications();

    NotificationResponseDTO findNotificationById(Long id);

    List<NotificationResponseDTO> findNotificationByUserId(Long userId);

    List<NotificationResponseDTO> findNotificationByTransactionId(Long transactionId);

    List<NotificationResponseDTO> findNotificationByStatus(Status status);

    List<NotificationResponseDTO> findNotificationByCreatedAtBetween(Instant start, Instant end);

    NotificationResponseDTO updateStatus(Long id, NotificationUpdateStatusDTO newStatus);

    void deleteNotification(Long id);
}
