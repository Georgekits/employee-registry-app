package com.service;


import com.unisystems.mapper.CompanyMapper;
import com.unisystems.model.Company;
import com.unisystems.repository.CompanyRepository;
import com.unisystems.response.CompanyResponse;
import com.unisystems.service.CompanyService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@SpringBootTest
public class CompanyServiceShould {

    private CompanyService companyService;

    CompanyResponse companyResponseFromMapper;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    private List<Company> mockedCompanies = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(companyRepository.findAll()).thenReturn(mockedCompanies);
        companyResponseFromMapper = new CompanyResponse(1L,"UniSystems","this is uni","company");
        when(companyMapper.mapCompanyResponseFromCompany(any())).thenReturn(companyResponseFromMapper);
        companyService = new CompanyService(companyMapper,companyRepository);
    }

    @Test
    public void retrievedCompaniesFromRepository() {
        companyService.getAllCompanies();
        Mockito.verify(companyRepository).findAll();
    }

    @Test
    public void usesCompanyMapper() {
        companyService.getAllCompanies();
        Mockito.verify(companyMapper,times(2)).mapCompanyResponseFromCompany(any());
    }


    @Test
    public void returnsListOfCompaniesResponse() {
        List<CompanyResponse> output = (List<CompanyResponse>) companyService.getAllCompanies().getData();
        Assert.assertEquals(1,output.size());
        List<CompanyResponse> expectedList = new ArrayList<>();
        expectedList.add(companyResponseFromMapper);
        Assert.assertThat(output, CoreMatchers.hasItems(companyResponseFromMapper));
    }
}