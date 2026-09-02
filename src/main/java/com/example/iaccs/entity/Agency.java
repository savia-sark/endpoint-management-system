package com.example.iaccs.entity;

import jakarta.persistence.*;

@Entity
@Table(name="agencies")
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="agency_id")
    private Integer agencyId;

    @Column(name="agency_name" ,nullable = false)
    private String agencyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_type", nullable = false)
    private AgencyType agencyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ntn_id")
    private Ntn ntn;

    public Agency() {
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public AgencyType getAgencyType() {
        return agencyType;
    }

    public Ntn getNtn() {
        return ntn;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void setAgencyType(AgencyType agencyType) {
        this.agencyType = agencyType;
    }

    public void setNtn(Ntn ntn) {
        this.ntn = ntn;
    }

    @Override
    public String toString() {
        return "Agency{" +
                "agencyId=" + agencyId +
                ", agencyName='" + agencyName + '\'' +
                '}';
    }
}
