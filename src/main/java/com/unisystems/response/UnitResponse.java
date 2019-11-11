package com.unisystems.response;

public class UnitResponse {
    private Long unitId;
    private String unitName;
    private String unitDescription;
    private String departmentName;

    public UnitResponse(Long unitId, String unitName, String unitDescription, String departmentName) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.unitDescription = unitDescription;
        this.departmentName = departmentName;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
