package com.example.iaccs.dto;

public class AgencyTypeResponseDTO {

    private Integer agencyTypeId;
    private String agencyTypeName;

    public AgencyTypeResponseDTO() {
    }

    public AgencyTypeResponseDTO(Integer agencyTypeId, String agencyTypeName) {
        this.agencyTypeId = agencyTypeId;
        this.agencyTypeName = agencyTypeName;
    }

    public Integer getAgencyTypeId() {
        return agencyTypeId;
    }

    public void setAgencyTypeId(Integer agencyTypeId) {
        this.agencyTypeId = agencyTypeId;
    }

    public String getAgencyTypeName() {
        return agencyTypeName;
    }

    public void setAgencyTypeName(String agencyTypeName) {
        this.agencyTypeName = agencyTypeName;
    }
}
