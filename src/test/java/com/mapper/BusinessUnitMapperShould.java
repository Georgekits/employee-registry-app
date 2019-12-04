package com.mapper;

        import com.unisystems.mapper.BusinessUnitMapper;
        import com.unisystems.model.BusinessUnit;
        import com.unisystems.model.Company;
        import com.unisystems.response.BusinessUnitResponse;
        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;


public class BusinessUnitMapperShould {

    private BusinessUnitMapper businessUnitMapper;
    private BusinessUnit businessUnitInput;
    private Company companyInput;
    private BusinessUnitResponse output;


    @Before
    public void setup() {
        businessUnitMapper = new BusinessUnitMapper();
        companyInput = new Company("UniSystems","That's the first company of this project and it listens to UniSystems");
        companyInput.setCompanyId(Long.valueOf(100));
        businessUnitInput = new BusinessUnit("PublicSector",
                "That's the business unit that is well known as Public Sector", companyInput);
        businessUnitInput.setBusinessUnitId(Long.valueOf(200));
        output = businessUnitMapper.mapBUResponseFromBU(businessUnitInput);
    }



    @Test
    public void keepSameId(){
        Assert.assertEquals(businessUnitInput.getBusinessUnitId(), output.getBusinessUnitId());
    }

    @Test
    public void keepSameUnitName(){
        Assert.assertEquals(businessUnitInput.getBusinessUnitName(), output.getBusinessUnitName());
    }

    @Test
    public void keepSameDescription(){
        Assert.assertEquals(businessUnitInput.getBusinessUnitDescription(), output.getBusinessUnitDescription());
    }

    @Test
    public void keepSameCompanyName(){
        Assert.assertEquals(companyInput.getCompanyName(), output.getCompanyName());
    }
}