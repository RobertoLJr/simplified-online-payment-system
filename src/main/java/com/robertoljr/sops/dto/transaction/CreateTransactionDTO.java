package com.robertoljr.sops.dto.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateTransactionDTO {

    @NotNull
    private Long senderId;

    @NotNull
    private Long recipientId;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0.0")
    private BigDecimal amount;

    @Size(max = 255)
    private String description;

    public CreateTransactionDTO() {
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CreateTransactionDTO{" +
                "senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}