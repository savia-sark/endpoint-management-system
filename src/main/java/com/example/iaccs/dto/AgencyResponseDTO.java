package com.example.iaccs.dto;

public class AgencyResponseDTO {
    private Integer agencyId;
    private String agencyName;
    private Integer agencyTypeId;
    private String agencyTypeName;
    private Integer ntnId;
    private String ntnName;

    public AgencyResponseDTO(Integer agencyId, String agencyName, Integer agencyTypeId, String agencyTypeName, Integer ntnId, String ntnName) {
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.agencyTypeId = agencyTypeId;
        this.agencyTypeName = agencyTypeName;
        this.ntnId = ntnId;
        this.ntnName = ntnName;
    }

    public AgencyResponseDTO() {
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

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

    public String getAgencyTypeName() {
        return agencyTypeName;
    }

    public void setAgencyTypeName(String agencyTypeName) {
        this.agencyTypeName = agencyTypeName;
    }

    public Integer getNtnId() {
        return ntnId;
    }

    public void setNtnId(Integer ntnId) {
        this.ntnId = ntnId;
    }

    public String getNtnName() {
        return ntnName;
    }

    public void setNtnName(String ntnName) {
        this.ntnName = ntnName;
    }
}
