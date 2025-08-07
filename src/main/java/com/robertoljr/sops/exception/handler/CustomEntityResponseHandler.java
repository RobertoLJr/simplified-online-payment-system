package com.robertoljr.sops.exception.handler;

import com.robertoljr.sops.exception.*;
import com.robertoljr.sops.exception.transaction.TransactionCreationException;
import com.robertoljr.sops.exception.transaction.TransactionNotAllowedException;
import com.robertoljr.sops.exception.transaction.TransactionNotFoundException;
import com.robertoljr.sops.exception.transaction.TransactionUpdateStatusException;
import com.robertoljr.sops.exception.user.UserCreationException;
import com.robertoljr.sops.exception.user.UserDeletionException;
import com.robertoljr.sops.exception.user.UserNotFoundException;
import com.robertoljr.sops.exception.user.UserUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomEntityResponseHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleGlobalExceptions(Exception ex, WebRequest request) {
        logger.error("Exception:", ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                Instant.now(),
                "An unexpected error occurred. Please try again later.", // Generic message for security
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // User exceptions
    @ExceptionHandler(UserCreationException.class)
    public final ResponseEntity<ExceptionResponse> handleUserCreationExceptions(UserCreationException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleUserNotFoundExceptions(UserNotFoundException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserUpdateException.class)
    public final ResponseEntity<ExceptionResponse> handleUserUpdateExceptions(UserUpdateException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDeletionException.class)
    public final ResponseEntity<ExceptionResponse> handleUserDeletionExceptions(UserDeletionException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    // Transaction exceptions
    @ExceptionHandler(TransactionCreationException.class)
    public final ResponseEntity<ExceptionResponse> handleTransactionCreationExceptions(TransactionCreationException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionNotAllowedException.class)
    public final ResponseEntity<ExceptionResponse> handleTransactionNotAllowedExceptions(TransactionNotAllowedException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleTransactionNotFoundExceptions(TransactionNotFoundException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionUpdateStatusException.class)
    public final ResponseEntity<ExceptionResponse> handleTransactionUpdateStatusExceptions(TransactionUpdateStatusException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponse> buildResponse(Exception ex, WebRequest request, HttpStatus status) {
        logger.error("Exception:", ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                Instant.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, status);
    }
}
