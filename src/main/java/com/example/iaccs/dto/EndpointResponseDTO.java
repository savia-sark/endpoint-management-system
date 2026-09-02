package com.example.iaccs.dto;

import com.example.iaccs.entity.enums.EndpointType;
import com.example.iaccs.entity.enums.Status;
import jakarta.persistence.criteria.CriteriaBuilder;

public class EndpointResponseDTO {
    private String endpointId;
    private EndpointType endpointType;
    private Status status;
    private String remarks;
    private String systemOwner;
    private String imRaised;
    private Integer agencyId;
    private String agencyName;

    public EndpointResponseDTO(String endpointId, EndpointType endpointType, Status status, String remarks, String systemOwner, String imRaised, Integer agencyId,String agencyName) {
        this.endpointId = endpointId;
        this.endpointType = endpointType;
        this.status = status;
        this.remarks = remarks;
        this.systemOwner = systemOwner;
        this.imRaised = imRaised;
        this.agencyId = agencyId;
        this.agencyName=agencyName;
    }

    public EndpointResponseDTO() {
    }

    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public EndpointType getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(EndpointType endpointType) {
        this.endpointType = endpointType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSystemOwner() {
        return systemOwner;
    }

    public void setSystemOwner(String systemOwner) {
        this.systemOwner = systemOwner;
    }

    public String getImRaised() {
        return imRaised;
    }

    public void setImRaised(String imRaised) {
        this.imRaised = imRaised;
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
}


