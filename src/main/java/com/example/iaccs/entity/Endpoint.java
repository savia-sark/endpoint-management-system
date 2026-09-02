package com.example.iaccs.entity;

import com.example.iaccs.entity.enums.EndpointType;
import com.example.iaccs.entity.enums.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "endpoints")
public class Endpoint {

    @Id
    @Column(name = "endpoint_id")
    private String endpointId;

    @Enumerated(EnumType.STRING)
    @Column(name = "endpoint_type", nullable = false)
    private EndpointType endpointType;

//    @Column(name = "airbase",nullable = false)
//    private String airbase;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name="remarks")
    private String remarks;

    @Column(name = "system_owner")
    private String systemOwner;

    @Column(name="im_raised")
    private String imRaised;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="agency_id")
    private Agency agency;

    public Endpoint() {
    }

    public String getEndpointId() {
        return endpointId;
    }

    public EndpointType getEndpointType() {
        return endpointType;
    }

//    public String getAirbase() {
//        return airbase;
//    }

    public Status getStatus() {
        return status;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getSystemOwner() {
        return systemOwner;
    }

    public String getImRaised() {
        return imRaised;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public void setEndpointType(EndpointType endpointType) {
        this.endpointType = endpointType;
    }

//    public void setAirbase(String airbase) {
//        this.airbase = airbase;
//    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setSystemOwner(String systemOwner) {
        this.systemOwner = systemOwner;
    }

    public void setImRaised(String imRaised) {
        this.imRaised = imRaised;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "endpointId='" + endpointId + '\'' +
                ", endpointType='" + endpointType + '\'' +
                ", status='" + status + '\'' +
                ", remarks='" + remarks + '\'' +
                ", systemOwner='" + systemOwner + '\'' +
                ", imRaised='" + imRaised + '\'' +
                '}';
    }
}

