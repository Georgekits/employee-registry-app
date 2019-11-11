package com.unisystems.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    @Column(name = "DEPARTMENT_DESCRIPTION")
    private String departmentDescription;
    @ManyToOne
    @JoinColumn(name="BUSINESS_UNIT_REF", referencedColumnName = "BUSINESS_UNIT_ID")
    private BusinessUnit businessUnitRef;

    public Department() {}

    public Department(String departmentName, String departmentDescription, BusinessUnit businessUnitRef) {
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.businessUnitRef = businessUnitRef;
    }

    public Department(String departmentName, String departmentDescription, BusinessUnit businessUnitRef, List<Unit> unitsList) {
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.businessUnitRef = businessUnitRef;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    public BusinessUnit getBusinessUnitRef() {
        return businessUnitRef;
    }

    public void setBusinessUnitRef(BusinessUnit businessUnitRef) {
        this.businessUnitRef = businessUnitRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return departmentId.equals(that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId);
    }
}