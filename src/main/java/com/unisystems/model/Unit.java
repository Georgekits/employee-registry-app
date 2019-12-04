package com.unisystems.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_ID")
    private Long unitId;
    @Column(name = "UNIT_NAME")
    private String unitName;
    @Column(name = "UNIT_DESCRIPTION")
    private String unitDescription;
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_REF", referencedColumnName = "DEPARTMENT_ID")
    private Department departmentRef;

    public Unit() {}

    public Unit(String unitName, String unitDescription, Department departmentRef) {
        this.unitName = unitName;
        this.unitDescription = unitDescription;
        this.departmentRef = departmentRef;
    }

    public Unit(Long unitId, String unitName, String unitDescription, Department departmentRef) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.unitDescription = unitDescription;
        this.departmentRef = departmentRef;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public Department getDepartmentRef() {
        return departmentRef;
    }

    public void setDepartmentRef(Department departmentRef) {
        this.departmentRef = departmentRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        Unit unit = (Unit) o;
        return unitId.equals(unit.unitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitId);
    }
}