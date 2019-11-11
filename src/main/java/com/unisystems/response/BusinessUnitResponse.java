package com.unisystems.response;

public class BusinessUnitResponse {
    private Long businessUnitId;
    private String businessUnitName;
    private String businessUnitDescription;
    private String companyName;

    public BusinessUnitResponse(Long businessUnitId, String businessUnitName, String businessUnitDescription, String companyName) {
        this.businessUnitId = businessUnitId;
        this.businessUnitName = businessUnitName;
        this.businessUnitDescription = businessUnitDescription;
        this.companyName = companyName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
