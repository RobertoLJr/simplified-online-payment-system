package com.robertoljr.sops.dto.user;

public class UpdatePhoneNumberDTO {

    private String phoneNumber;

    public UpdatePhoneNumberDTO() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "UpdatePhoneNumberDTO{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
