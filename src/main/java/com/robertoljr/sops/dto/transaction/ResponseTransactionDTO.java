package com.robertoljr.sops.dto.transaction;

import com.robertoljr.sops.constant.transaction.Status;

import java.math.BigDecimal;
import java.time.Instant;

public record ResponseTransactionDTO(
        Long id,
        Long senderId,
        Long recipientId,
        BigDecimal amount,
        Status status,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}