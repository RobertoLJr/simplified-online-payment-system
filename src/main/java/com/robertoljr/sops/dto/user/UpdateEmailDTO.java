package com.robertoljr.sops.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateEmailDTO {

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "New email must not be blank")
    private String newEmail;

    @NotBlank(message = "Current password is required to confirm email change")
    private String currentPassword;

    public UpdateEmailDTO() {
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @Override
    public String toString() {
        return "UpdateEmailDTO{" +
                "newEmail='" + newEmail + '\'' +
                ", currentPassword='[PROTECTED]'" +
                '}';
    }
}
