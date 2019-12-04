package com.mapper;

import com.unisystems.mapper.UnitMapper;
import com.unisystems.model.BusinessUnit;
import com.unisystems.model.Company;
import com.unisystems.model.Department;
import com.unisystems.model.Unit;
import com.unisystems.response.UnitResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitMapperShould {
    private UnitMapper unitMapper;
    private Unit unit;
    private UnitResponse output;

    private BusinessUnit businessUnit;
    private Department department;
    private Company company;

    @Before
    public void setup() {
        unitMapper = new UnitMapper();
        company = new Company("UniSystems","That's the first company of this project and it listens to UniSystems");
        businessUnit =  new BusinessUnit("PublicSector",
                "That's the business unit that is well known as Public Sector",company);
        department = new Department("DM","Department of Magic",businessUnit);
        unit = new Unit(1L,"Meter","The basic unit of distance",department);
        //Output
        output = unitMapper.mapDepartmentResponseFromDepartment(unit);
    }

    @Test
    public void keepSameId() {
        Assert.assertEquals(unit.getUnitId(),output.getUnitId());
    }

    @Test
    public void keepSameName() {
        Assert.assertEquals(unit.getUnitName(),output.getUnitName());
    }

    @Test
    public void keepSameDescription() {
        Assert.assertEquals(unit.getUnitDescription(),output.getUnitDescription());
    }

    @Test
    public void keepSameDepartmentName() {
        Assert.assertEquals(unit.getDepartmentRef().getDepartmentName(),output.getDepartmentName());
    }
}