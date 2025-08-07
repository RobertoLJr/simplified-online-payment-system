package com.robertoljr.sops.controller.docs;

import com.robertoljr.sops.constant.notification.Status;
import com.robertoljr.sops.dto.notification.NotificationCreateDTO;
import com.robertoljr.sops.dto.notification.NotificationResponseDTO;
import com.robertoljr.sops.dto.notification.NotificationUpdateStatusDTO;
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

public interface NotificationControllerDocs {
    @Operation(
            summary = "Create a new notification.",
            description = "Creates a new Notification by passing in a JSON, XML or YML representation of said notification.",
            tags = {"Notification"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<NotificationResponseDTO> createNotification(@Valid @RequestBody NotificationCreateDTO notificationCreateDTO);

    @Operation(
            summary = "Find all notifications.",
            description = "Finds all notifications registered in the system.",
            tags = {"Notification"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = NotificationResponseDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<NotificationResponseDTO>> findAllNotifications();

    @Operation(
            summary = "Find a notification by ID.",
            description = "Finds a particular notification by its ID.",
            tags = {"Notification"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<NotificationResponseDTO> findNotificationById(@PathVariable Long id);

    @Operation(
            summary = "Find notification by user ID.",
            description = "Finds all notifications sent to the User with the passed in ID.",
            tags = {"Notification"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<NotificationResponseDTO>> findNotificationsByUserId(@PathVariable Long userId);

    @Operation(
            summary = "Find notifications by transaction ID.",
            description = "Finds all notifications related to the transaction with the passed in ID.",
            tags = {"Notification"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<NotificationResponseDTO>> findNotificationByTransactionId(@PathVariable Long transactionId);

    @Operation(
            summary = "Find notifications with a particular status.",
            description = "Finds all notifications with a particular Status.",
            tags = {"Notification"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<NotificationResponseDTO>> findNotificationByStatus(@PathVariable Status status);

    @Operation(
            summary = "Find notification between Instants",
            description = "Finds all notifications after a particular Instant and before a particular Instant.",
            tags = {"Notification"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<NotificationResponseDTO>> findNotificationsByCreatedAtBetween(@PathVariable Instant start, @PathVariable Instant end);

    @Operation(
            summary = "Update a notification's Status based on its ID.",
            description = "Updates a notification's Status by passing in an ID and a JSON, XML or YML representation of said notification's new Status.",
            tags = {"Notification"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<NotificationResponseDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody NotificationUpdateStatusDTO status);

    @Operation(summary = "Delete a Notification.",
            description = "Deletes a specific Notification by its ID.",
            tags = {"Notification"},
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
    ResponseEntity<Void> deleteNotification(@PathVariable Long id);
}
