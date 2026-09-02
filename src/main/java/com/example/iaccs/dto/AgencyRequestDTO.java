package com.example.iaccs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AgencyRequestDTO {
    @NotBlank(message = "AgencyName is required")
    private String agencyName;
    @NotNull(message = "AgencyTypeId is required")
    private Integer agencyTypeId;
    @NotNull(message = "NtnId is required")
    private Integer ntnId;

    public AgencyRequestDTO(String agencyName, Integer agencyTypeId, Integer ntnId) {
        this.agencyName = agencyName;
        this.agencyTypeId = agencyTypeId;
        this.ntnId = ntnId;
    }

    //public AgencyRequestDTO() {
    //}

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getAgencyTypeId() {
        return agencyTypeId;
    }

    public void setAgencyTypeId(Integer agencyTypeId) {
        this.agencyTypeId = agencyTypeId;
    }

    public Integer getNtnId() {
        return ntnId;
    }

    public void setNtnId(Integer ntnId) {
        this.ntnId = ntnId;
    }
}
