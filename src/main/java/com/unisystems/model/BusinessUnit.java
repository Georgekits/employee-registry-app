package com.unisystems.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class BusinessUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUSINESS_UNIT_ID")
    private Long businessUnitId;
    @Column(name = "BUSINESS_UNIT_NAME")
    private String businessUnitName;
    @Column(name = "BUSINESS_UNIT_DESCRIPTION")
    private String businessUnitDescription;
    @ManyToOne
    @JoinColumn(name="COMPANY_REF", referencedColumnName = "COMPANY_ID")
    private Company companyRef;

    public BusinessUnit() {}

    public BusinessUnit(String businessUnitName, String businessUnitDescription, Company companyRef) {
        this.businessUnitName = businessUnitName;
        this.businessUnitDescription = businessUnitDescription;
        this.companyRef = companyRef;
    }

    public Long getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(Long businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getBusinessUnitDescription() {
        return businessUnitDescription;
    }

    public void setBusinessUnitDescription(String businessUnitDescription) {
        this.businessUnitDescription = businessUnitDescription;
    }

    public Company getCompanyRef() {
        return companyRef;
    }

    public void setCompanyRef(Company companyRef) {
        this.companyRef = companyRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessUnit)) return false;
        BusinessUnit businessUnit = (BusinessUnit) o;
        return businessUnitId.equals(businessUnit.businessUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessUnitId);
    }
}