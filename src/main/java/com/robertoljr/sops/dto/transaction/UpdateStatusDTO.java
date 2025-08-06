package com.robertoljr.sops.dto.transaction;

import jakarta.validation.constraints.NotNull;

public class UpdateStatusDTO {

    @NotNull
    private String status;

    public UpdateStatusDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateStatusDTO{" +
                "status='" + status + '\'' +
                '}';
    }
}