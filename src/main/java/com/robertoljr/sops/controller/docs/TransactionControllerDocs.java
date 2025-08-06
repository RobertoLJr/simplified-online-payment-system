package com.robertoljr.sops.controller.docs;

import com.robertoljr.sops.constant.transaction.Status;
import com.robertoljr.sops.dto.transaction.*;
import com.robertoljr.sops.dto.user.UpdateEmailDTO;
import com.robertoljr.sops.dto.user.UpdatePasswordDTO;
import com.robertoljr.sops.dto.user.UpdatePhoneNumberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;

public interface TransactionControllerDocs {
    @Operation(
            summary = "Create a new transaction.",
            description = "Creates a new Transaction by passing in a JSON, XML or YML representation of said transaction.",
            tags = {"Transaction"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ResponseTransactionDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<ResponseTransactionDTO> createTransaction(@Valid @RequestBody CreateTransactionDTO transactionCreateDTO);

    @Operation(
            summary = "Find all transactions.",
            description = "Finds all transactions registered in the system.",
            tags = {"Transaction"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = ResponseTransactionDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<ResponseTransactionDTO>> findAllTransactions();

    @Operation(
            summary = "Find a transaction by ID.",
            description = "Finds a particular transaction by its ID.",
            tags = {"Transaction"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseTransactionDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<ResponseTransactionDTO> findTransactionById(@PathVariable Long id);

    @Operation(
            summary = "Find transactions by sender ID.",
            description = "Finds all transactions made by the User with the passed in ID.",
            tags = {"Transaction"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseTransactionDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<ResponseTransactionDTO>> findTransactionBySenderId(@PathVariable Long senderId);

    @Operation(
            summary = "Find transactions by recipient ID.",
            description = "Finds all transactions made to the User with the passed in ID.",
            tags = {"Transaction"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseTransactionDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<ResponseTransactionDTO>> findTransactionByRecipientId(@PathVariable Long recipientId);

    @Operation(
            summary = "Find transactions with a particular status.",
            description = "Finds all transactions with a particular Status.",
            tags = {"Transaction"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseTransactionDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<ResponseTransactionDTO>> findTransactionsByStatus(@PathVariable Status status);

    @Operation(
            summary = "Find transactions between Instants",
            description = "Finds all transactions after a particular Instant and before a particular Instant.",
            tags = {"Transaction"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseTransactionDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<ResponseTransactionDTO>> findTransactionsByCreatedAtBetween(@PathVariable Instant start, @PathVariable Instant end);

    @Operation(
            summary = "Update a transaction's Status based on its ID.",
            description = "Updates a transaction's Status by passing in an ID and a JSON, XML or YML representation of said transaction's current Status.",
            tags = {"Transaction"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ResponseTransactionDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<ResponseTransactionDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusDTO status);

    @Operation(summary = "Delete a Transaction.",
            description = "Deletes a specific Transaction by its ID.",
            tags = {"Transaction"},
            responses = {
                    @ApiResponse(
                            description = "No Content",
                            responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<Void> deleteTransaction(@PathVariable Long id);
}
