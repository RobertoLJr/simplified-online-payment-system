package com.robertoljr.sops.dto.notification;

import com.robertoljr.sops.constant.notification.Channel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationCreateDTO(
        @NotNull
        Long userId,

        @NotNull
        Long transactionId,

        @NotNull
        Channel channel,

        @NotNull
        String destination,

        @NotNull
        String subject,

        @NotBlank
        String message
) {
}
