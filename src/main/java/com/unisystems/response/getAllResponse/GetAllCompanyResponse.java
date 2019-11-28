package com.unisystems.response.getAllResponse;

import com.unisystems.response.CompanyResponse;

import java.util.List;

public class GetAllCompanyResponse {
    private List<CompanyResponse> companies;

    public GetAllCompanyResponse(List<CompanyResponse> companies) {
        this.companies = companies;
    }

    public List<CompanyResponse> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyResponse> companies) {
        this.companies = companies;
    }
}
