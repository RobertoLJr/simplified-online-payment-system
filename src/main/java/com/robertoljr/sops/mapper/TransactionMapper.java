package com.robertoljr.sops.mapper;

import com.robertoljr.sops.dto.transaction.CreateTransactionDTO;
import com.robertoljr.sops.dto.transaction.ResponseTransactionDTO;
import com.robertoljr.sops.entity.Transaction;
import com.robertoljr.sops.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    // CreateTransactionDTO -> Transaction
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sender", source = "senderId", qualifiedByName = "fromId")
    @Mapping(target = "recipient", source = "recipientId", qualifiedByName = "fromId")
    Transaction toEntity(CreateTransactionDTO dto);

    // Transaction -> ResponseTransactionDTO
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "recipient.id", target = "recipientId")
    ResponseTransactionDTO toResponseDTO(Transaction transaction);

    @Named("fromId")
    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
