package com.robertoljr.sops.dto.notification;

import com.robertoljr.sops.constant.notification.Channel;
import com.robertoljr.sops.constant.notification.Status;

import java.time.Instant;

public record NotificationResponseDTO(
        Long id,
        Long userId,
        Long transactionId,
        Channel channel,
        String destination,
        String subject,
        String message,
        Status status,
        Instant createdAt,
        Instant sentAt
) {
}
