package com.unisystems.service;

import com.unisystems.mapper.CompanyMapper;
import com.unisystems.model.Company;
import com.unisystems.repository.CompanyRepository;
import com.unisystems.response.CompanyResponse;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllCompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyMapper companyMapper;

    public CompanyService(CompanyMapper companyMapper, CompanyRepository companyRepository) {
        this.companyMapper = companyMapper;
        this.companyRepository = companyRepository;
    }

    public GenericResponse<GetAllCompanyResponse> getAllCompanies() {
        List<Company> retrievedCompanies = (List<Company>) companyRepository.findAll();
        List<CompanyResponse> companies = new ArrayList<CompanyResponse>();
        GenericResponse<GetAllCompanyResponse> genericResponse = new GenericResponse<>();

        retrievedCompanies.forEach((company) -> {
            companies.add(companyMapper.mapCompanyResponseFromCompany(company));
        });
        genericResponse.setData(new GetAllCompanyResponse(companies));
        return genericResponse;
    }
}
