package com.robertoljr.sops.dto.notification;

import com.robertoljr.sops.constant.notification.Channel;
import com.robertoljr.sops.constant.notification.Status;

public record NotificationCreateDTO(
    Long userId,
    Long transactionId,
    Channel channel,
    String destination,
    String subject,
    String message,
    Status status
) {}
