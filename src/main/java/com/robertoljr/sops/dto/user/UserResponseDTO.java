package com.robertoljr.sops.dto.user;

import com.robertoljr.sops.constant.user.DocumentType;
import com.robertoljr.sops.constant.user.UserType;

import java.math.BigDecimal;
import java.time.Instant;

public record UserResponseDTO(
        Long id,
        String legalName,
        DocumentType documentType,
        String documentNumber,
        String email,
        String phoneNumber,
        BigDecimal balance,
        UserType userType,
        Instant createdAt,
        Instant updatedAt
) {}
