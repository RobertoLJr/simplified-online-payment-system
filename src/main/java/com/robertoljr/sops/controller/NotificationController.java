package com.robertoljr.sops.controller;

import com.robertoljr.sops.constant.notification.Status;
import com.robertoljr.sops.controller.docs.NotificationControllerDocs;
import com.robertoljr.sops.controller.docs.NotificationControllerDocs;
import com.robertoljr.sops.dto.notification.NotificationCreateDTO;
import com.robertoljr.sops.dto.notification.NotificationResponseDTO;
import com.robertoljr.sops.dto.notification.NotificationUpdateStatusDTO;
import com.robertoljr.sops.service.NotificationService;
import com.robertoljr.sops.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController implements NotificationControllerDocs {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<NotificationResponseDTO> createNotification(@Valid @RequestBody NotificationCreateDTO notificationCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(notificationCreateDTO));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<NotificationResponseDTO>> findAllNotifications() {
        return ResponseEntity.ok(notificationService.findAllNotifications());
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<NotificationResponseDTO> findNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.findNotificationById(id));
    }

    @GetMapping(value = "/userId/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<NotificationResponseDTO>> findNotificationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.findNotificationByUserId(userId));
    }

    @GetMapping(value = "/transactionId/{transactionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<NotificationResponseDTO>> findNotificationByTransactionId(@PathVariable Long transactionId) {
        return ResponseEntity.ok(notificationService.findNotificationByTransactionId(transactionId));
    }

    @GetMapping(value = "/{status}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<NotificationResponseDTO>> findNotificationByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(notificationService.findNotificationByStatus(status));
    }

    @GetMapping(value = "/after/{start}/before/{end}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<NotificationResponseDTO>> findNotificationsByCreatedAtBetween(@PathVariable Instant start, @PathVariable Instant end) {
        return ResponseEntity.ok(notificationService.findNotificationByCreatedAtBetween(start, end));
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<NotificationResponseDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody NotificationUpdateStatusDTO status) {
        return ResponseEntity.ok(notificationService.updateStatus(id, status));
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
