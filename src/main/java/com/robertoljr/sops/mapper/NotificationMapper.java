package com.robertoljr.sops.mapper;

import com.robertoljr.sops.dto.notification.NotificationCreateDTO;
import com.robertoljr.sops.dto.notification.NotificationResponseDTO;
import com.robertoljr.sops.entity.Notification;
import com.robertoljr.sops.entity.Transaction;
import com.robertoljr.sops.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    // NotificationCreateDTO -> Notification
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId", qualifiedByName = "fromUserId")
    @Mapping(target = "transaction", source = "transactionId", qualifiedByName = "fromTransactionId")
    Notification toEntity(NotificationCreateDTO dto);

    // Notification -> NotificationResponseDTO
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "transaction.id", target = "transactionId")
    NotificationResponseDTO toResponseDTO(Notification notification);

    @Named("fromUserId")
    default User fromUserId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("fromTransactionId")
    default Transaction fromTransactionId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
