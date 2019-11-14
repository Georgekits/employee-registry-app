package com.unisystems.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class    Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID")
    private Long companyId;
    @Column(name = "COMPANY_NAME")
    private String companyName;
    @Column(name = "DESCRIPTION")
    private String description;

    public Company() {}

    public Company(String companyName, String description) {
        this.companyName = companyName;
        this.description = description;
    }

    public Company(String companyName, String description, List<BusinessUnit> businessUnitsList) {
        this.companyName = companyName;
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return companyId.equals(company.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId);
    }
}