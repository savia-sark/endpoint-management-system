package com.example.iaccs.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "agency_types")
public class AgencyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_type_id")
    private Integer agencyTypeId;

    @Column(name = "name", nullable = false, unique = true)
    private String typeName;

    // Constructors
    public AgencyType() {
    }

    // Getters
    public Integer getAgencyTypeId() {
        return agencyTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    // Setters
    public void setAgencyTypeId(Integer agencyTypeId) {
        this.agencyTypeId = agencyTypeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "AgencyType{" +
                "agencyTypeId=" + agencyTypeId +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
