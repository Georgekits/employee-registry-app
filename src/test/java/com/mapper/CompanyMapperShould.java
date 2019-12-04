package com.unisystems.UniStructure;



import com.unisystems.mapper.CompanyMapper;
import com.unisystems.model.Company;
import com.unisystems.response.CompanyResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CompanyMapperShould {

    private CompanyMapper companyMapper;
    private Company companyInput;
    private CompanyResponse output;


    @Before

    public void setup() {
        companyMapper = new CompanyMapper();
        companyInput = new Company("UniSystems",
                                "That's the first company of this project and it listens to UniSystems ");
        companyInput.setCompanyId(Long.valueOf(100));
        output = companyMapper.mapCompanyResponseFromCompany(companyInput);
    }


    @Test
    public void keepSameId() {
        Assert.assertEquals(companyInput.getCompanyId(), output.getCompanyId());
    }

    @Test
    public void keepSameCompanyName() {
        Assert.assertEquals(companyInput.getCompanyName(), output.getCompanyName());
    }


    @Test
    public void keepSameDescription() {
        Assert.assertEquals(companyInput.getDescription(), output.getDescription());
    }
}
