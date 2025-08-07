package com.robertoljr.sops.mapper;

import com.robertoljr.sops.dto.user.UserCreateDTO;
import com.robertoljr.sops.dto.user.UserResponseDTO;
import com.robertoljr.sops.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // UserCreateDTO -> User
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", defaultValue = "0.00")
    User toEntity(UserCreateDTO dto);

    // User -> UserCreateDTO
    UserResponseDTO toResponseDTO(User user);
}
