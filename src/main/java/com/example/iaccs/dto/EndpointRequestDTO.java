package com.example.iaccs.dto;

import com.example.iaccs.entity.enums.EndpointType;
import com.example.iaccs.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EndpointRequestDTO {
    @NotBlank(message = "EndpointId is required")
    private String endpointId;
    @NotNull(message = "EndpointType is required")
    private EndpointType endpointType;
    @NotNull(message = "Status is required")
    private Status status;
    private String remarks;
    @NotBlank(message = "SystemOwner is required")
    private String systemOwner;
    @NotBlank(message = "IMRaised is required")
    private String imRaised;
    @NotNull(message = "AgencyId is required")
    private Integer agencyId;

    public EndpointRequestDTO(String endpointId, EndpointType endpointType, Status status, String remarks, String systemOwner, String imRaised, Integer agencyId) {
        this.endpointId = endpointId;
        this.endpointType = endpointType;
        this.status = status;
        this.remarks = remarks;
        this.systemOwner = systemOwner;
        this.imRaised = imRaised;
        this.agencyId = agencyId;
    }

    public EndpointRequestDTO() {
    }

    public EndpointRequestDTO(String endpointId, EndpointType endpointType, Status status, Integer agencyId) {
        this.endpointId = endpointId;
        this.endpointType = endpointType;
        this.status = status;
        this.agencyId = agencyId;
    }

    @Override
    public String toString() {
        return "EndpointRequestDTO{" +
                "endpointId='" + endpointId + '\'' +
                ", endpointType=" + endpointType +
                ", status=" + status +
                ", remarks='" + remarks + '\'' +
                ", systemOwner='" + systemOwner + '\'' +
                ", imRaised='" + imRaised + '\'' +
                ", agencyId=" + agencyId +
                '}';
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
}
