package com.robertoljr.sops.dto.user;

import com.robertoljr.sops.constant.user.DocumentType;
import com.robertoljr.sops.constant.user.UserType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UserCreateDTO {

    @NotBlank
    private String legalName;

    @NotNull
    private DocumentType documentType;

    @NotBlank
    private String documentNumber;

    @Email
    @NotBlank
    private String email;

    private String phoneNumber;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotNull
    private UserType userType;

    @DecimalMin(value = "0.00")
    private BigDecimal balance;

    public UserCreateDTO() {
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "legalName='" + legalName + '\'' +
                ", documentType=" + documentType +
                ", documentNumber='" + documentNumber + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", balance=" + balance +
                '}';
    }
}
