package com.robertoljr.sops.exception;

import java.time.Instant;

public record ExceptionResponse(Instant timestamp, String message, String details) {
}
