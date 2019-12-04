package com.mapper;

import com.unisystems.mapper.DepartmentMapper;
import com.unisystems.model.BusinessUnit;
import com.unisystems.model.Company;
import com.unisystems.model.Department;
import com.unisystems.response.DepartmentResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentMapperShould {

    private DepartmentMapper departmentMapper;
    private Department departmentInput;
    private Company companyInput;
    private BusinessUnit businessUnitInput;
    private DepartmentResponse output;

    @Before
    public void setUp() {
        departmentMapper = new DepartmentMapper();
        companyInput = new Company("UniSystems","That's the first company of this project and it listens to UniSystems");
        businessUnitInput = new BusinessUnit("PublicSector", "This is the first PublicSector", companyInput);
        businessUnitInput.setBusinessUnitId(50L);

        departmentInput = new Department("IT", "This is the IT department", businessUnitInput);
        departmentInput.setDepartmentId(100L);
        output = departmentMapper.mapDepartmentResponseFromDepartment(departmentInput);
    }

    @Test
    public void keepSameId(){
        Assert.assertEquals(departmentInput.getDepartmentId(),output.getDepartmentId());
    }
    @Test
    public void keepSameDepartmentName(){
        Assert.assertEquals(departmentInput.getDepartmentName(),output.getDepartmentName());
    }
    @Test
    public void keepSameDescription(){
        Assert.assertEquals(departmentInput.getDepartmentDescription(),output.getDepartmentDescription());
    }
    @Test
    public void keepSameBusinessUnitName(){
        Assert.assertEquals(businessUnitInput.getBusinessUnitName(), output.getBusinessUnitName());
    }
}