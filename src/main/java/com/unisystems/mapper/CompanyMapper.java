package com.unisystems.mapper;

import com.unisystems.model.Company;
import com.unisystems.response.CompanyResponse;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public CompanyResponse mapCompanyResponseFromCompany(Company company) {
        CompanyResponse companyResponse = new CompanyResponse(
            company.getCompanyId(),
            company.getCompanyName(),
            company.getDescription(),
                getCompanyTitle(company)
        );
        return companyResponse;
    }

    private String getCompanyTitle(Company company) {
        return company.getCompanyName()+", "+company.getDescription();
    }
}
