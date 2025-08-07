package com.robertoljr.sops.dto.notification;

import com.robertoljr.sops.constant.notification.Status;

public record NotificationUpdateStatusDTO(
        Status newStatus
) {
}
