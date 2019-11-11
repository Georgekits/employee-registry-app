package com.unisystems.response;

public class CompanyResponse {
    private Long companyId;
    private String companyName;
    private String description;
    private String title;

    public CompanyResponse(Long companyId, String companyName, String description, String title) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.description = description;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
