package com.example.iaccs.dto;

import jakarta.validation.constraints.NotBlank;

public class AgencyTypeRequestDTO {
    @NotBlank(message = "AgencyTypeName is required")
    private String agencyTypeName;

    public AgencyTypeRequestDTO(String agencyTypeName) {
        this.agencyTypeName = agencyTypeName;
    }

    public AgencyTypeRequestDTO() {
    }

    public String getAgencyTypeName() {
        return agencyTypeName;
    }

    public void setAgencyTypeName(String agencyTypeName) {
        this.agencyTypeName = agencyTypeName;
    }
}
