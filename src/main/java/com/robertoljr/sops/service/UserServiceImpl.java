package com.robertoljr.sops.service;

import com.robertoljr.sops.dto.user.*;
import com.robertoljr.sops.entity.User;
import com.robertoljr.sops.exception.UserCreationException;
import com.robertoljr.sops.exception.UserNotFoundException;
import com.robertoljr.sops.exception.UserUpdateException;
import com.robertoljr.sops.mapper.UserMapper;
import com.robertoljr.sops.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        try {
            User user = userMapper.toEntity(dto);
            user = userRepository.save(user);
            return userMapper.toResponseDTO(user);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Data integrity violation exception:", ex);
            throw new UserCreationException("Email or document number already in use.");
        } catch (Exception ex) {
            logger.error("Exception:", ex);
            throw new UserCreationException("An unexpected error occurred while creating the user.");
        }
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO findUserById(Long id) {
        User user = getUserOrThrow(id);
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO findUserByEmail(String email) {
        Optional<User> dbUser = userRepository.findByEmail(email);
        return dbUser.map(userMapper::toResponseDTO)
                .orElseThrow(() -> {
                    logger.error("User not found for email: {}", email);
                    return new UserNotFoundException("User not found for email: " + email);
                });
    }

    @Override
    public UserResponseDTO findUserByDocumentNumber(String documentNumber) {
        Optional<User> dbUser = userRepository.findByDocumentNumber(documentNumber);
        return dbUser.map(userMapper::toResponseDTO)
                .orElseThrow(() -> {
                    logger.error("User not found for document number: {}", documentNumber);
                    return new UserNotFoundException("User not found for document number: " + documentNumber);
                });
    }

    @Override
    @Transactional
    public UserResponseDTO updateEmail(Long userId, UpdateEmailDTO dto) {
        User dbUser = getUserOrThrow(userId);

        // Check if the current password is correct
        assertPasswordMatches(dbUser.getPassword(), dto.getCurrentPassword());

        String newEmail = dto.getNewEmail().toLowerCase();

        // Check if the new email is the same as the current one
        if (dbUser.getEmail().equals(newEmail)) {
            throw new UserUpdateException("New email is the same as the current one.");
        }

        // Check if the new email is already in use
        if (userRepository.findByEmail(newEmail).isPresent()) {
            throw new UserUpdateException("Email already in use.");
        }

        dbUser.setEmail(dto.getNewEmail());
        logger.info("Updated email for user id {} to {}", userId, dto.getNewEmail().toLowerCase());
        userRepository.save(dbUser);

        return userMapper.toResponseDTO(dbUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updatePhoneNumber(Long userId, UpdatePhoneNumberDTO dto) {
        User dbUser = getUserOrThrow(userId);

        // Check if the current phone number is the same as the new one
        if (dto.getPhoneNumber().equals(dbUser.getPhoneNumber())) {
            throw new UserUpdateException("New phone number is the same as the current one.");
        }

        dbUser.setPhoneNumber(dto.getPhoneNumber());

        logger.info("Updated phoneNumber for user id {} to {}", userId, dto.getPhoneNumber());
        userRepository.save(dbUser);

        return userMapper.toResponseDTO(dbUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updatePassword(Long userId, UpdatePasswordDTO dto) {
        User dbUser = getUserOrThrow(userId);
        assertPasswordMatches(dbUser.getPassword(), dto.getCurrentPassword());
        dbUser.setPassword(dto.getNewPassword());
        userRepository.save(dbUser);

        logger.info("Updated password for user id {}", userId);
        return userMapper.toResponseDTO(dbUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User dbUser = getUserOrThrow(id);

        logger.info("Deleted user with id {}", id);
        userRepository.delete(dbUser);
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found for id: {}", userId);
                    return new UserNotFoundException("User not found for id: " + userId);
                });
    }

    private void assertPasswordMatches(String actual, String expected) {
        if (!actual.equals(expected)) {
            logger.error("Current password is incorrect.");
            throw new UserUpdateException("Current password is incorrect.");
        }
    }
}
