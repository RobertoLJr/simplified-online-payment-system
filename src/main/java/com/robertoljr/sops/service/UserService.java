package com.robertoljr.sops.service;

import com.robertoljr.sops.dto.user.*;

public interface UserService {

    UserResponseDTO createUser(UserCreateDTO dto);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO getUserByDocumentNumber(String documentNumber);

    void updateEmail(Long userId, UpdateEmailDTO dto);

    void updatePhoneNumber(Long userId, UpdatePhoneNumberDTO dto);

    void updatePassword(Long userId, UpdatePasswordDTO dto);

    void deleteUser(Long id);
}
