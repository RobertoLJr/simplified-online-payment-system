package com.robertoljr.sops.service;

import com.robertoljr.sops.dto.user.*;

public interface UserService {

    UserResponseDTO createUser(UserCreateDTO dto);

    UserResponseDTO findUserById(Long id);

    UserResponseDTO findUserByEmail(String email);

    UserResponseDTO findUserByDocumentNumber(String documentNumber);

    UserResponseDTO updateEmail(Long userId, UpdateEmailDTO dto);

    UserResponseDTO updatePhoneNumber(Long userId, UpdatePhoneNumberDTO dto);

    UserResponseDTO updatePassword(Long userId, UpdatePasswordDTO dto);

    void deleteUser(Long id);
}
