package com.robertoljr.sops.service;

import com.robertoljr.sops.constant.notification.Status;
import com.robertoljr.sops.dto.notification.NotificationCreateDTO;
import com.robertoljr.sops.dto.notification.NotificationResponseDTO;
import com.robertoljr.sops.dto.notification.NotificationUpdateStatusDTO;
import com.robertoljr.sops.entity.Notification;
import com.robertoljr.sops.exception.notification.NotificationNotFoundException;
import com.robertoljr.sops.exception.transaction.TransactionNotFoundException;
import com.robertoljr.sops.exception.transaction.TransactionUpdateStatusException;
import com.robertoljr.sops.exception.user.UserNotFoundException;
import com.robertoljr.sops.mapper.NotificationMapper;
import com.robertoljr.sops.repository.NotificationRepository;
import com.robertoljr.sops.repository.TransactionRepository;
import com.robertoljr.sops.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            NotificationMapper notificationMapper,
            UserRepository userRepository,
            TransactionRepository transactionRepository,
            RestTemplate restTemplate) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public NotificationResponseDTO createNotification(NotificationCreateDTO dto) {
        logger.info("Creating notification for user id {}", dto.userId());

        // Validate user
        if (!isValidUser(dto.userId())) {
            throw new UserNotFoundException(String.format("User with id %d not found", dto.userId()));
        }

        // Validate transaction
        if (!isValidTransaction(dto.transactionId())) {
            throw new TransactionNotFoundException(String.format("Transaction with id %d not found", dto.transactionId()));
        }

        // Check if the notification service is available
        boolean isNotificationServiceAvailable = isNotificationServiceAvailable();

        Notification notification = notificationMapper.toEntity(dto);
        if (isNotificationServiceAvailable) {
            notification.setStatus(Status.SENT);
            notification.setSentAt(Instant.now());
        } else {
            notification.setStatus(Status.FAILED);
        }
        notificationRepository.save(notification);

        return notificationMapper.toResponseDTO(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> findAllNotifications() {
        logger.info("Retrieving all notifications");

        return notificationRepository.findAll().stream()
                .map(notificationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponseDTO findNotificationById(Long id) {
        logger.info("Retrieving notification with id {}", id);

        return notificationMapper.toResponseDTO(notificationRepository.findById(id).orElseThrow(
                () -> new NotificationNotFoundException("Notification not found for id: " + id)
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> findNotificationByUserId(Long userId) {
        logger.info("Retrieving notifications by user id {}", userId);

        return notificationRepository.findByUserId(userId).stream()
                .map(notificationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> findNotificationByTransactionId(Long transactionId) {
        logger.info("Retrieving notifications by transaction id {}", transactionId);

        return notificationRepository.findByTransactionId(transactionId).stream()
                .map(notificationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> findNotificationByStatus(Status status) {
        logger.info("Retrieving notifications by status {}", status);

        return notificationRepository.findByStatus(status).stream()
                .map(notificationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> findNotificationByCreatedAtBetween(Instant start, Instant end) {
        logger.info("Retrieving notifications by createdAt between {} and {}", start, end);

        return notificationRepository.findByCreatedAtBetween(start, end).stream()
                .map(notificationMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public NotificationResponseDTO updateStatus(Long id, NotificationUpdateStatusDTO dto) {
        logger.info("Updating status for notification id {}", id);

        Notification dbNotification = notificationRepository.findById(id).orElseThrow(
                () -> new NotificationNotFoundException("Notification not found for id: " + id)
        );

        dbNotification.setStatus(dto.newStatus());
        dbNotification = notificationRepository.save(dbNotification);
        logger.info("Updated status for notification id {} to {}", id, dto.newStatus());
        return notificationMapper.toResponseDTO(dbNotification);
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        logger.info("Deleting notification with id {}", id);

        Notification dbNotification = notificationRepository.findById(id).orElseThrow(
                () -> new NotificationNotFoundException("Notification not found for id: " + id)
        );

        notificationRepository.delete(dbNotification);
        logger.info("Deleted notification with id {}", id);
    }

    private boolean isValidUser(Long userId) {
        return userRepository.existsById(userId);
    }

    private boolean isValidTransaction(Long transactionId) {
        return transactionRepository.existsById(transactionId);
    }

    private boolean isNotificationServiceAvailable() {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity("https://util.devi.tools/api/v1/notify", Map.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception ex) {
            logger.error("Exception: {}", ex.getMessage());
            return false;
        }
    }
}
