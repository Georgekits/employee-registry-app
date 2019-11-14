package com.unisystems.UniStructure;

import com.unisystems.mapper.UnitMapper;
import com.unisystems.model.BusinessUnit;
import com.unisystems.model.Company;
import com.unisystems.model.Department;
import com.unisystems.model.Unit;
import com.unisystems.response.UnitResponse;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.Assert;


public class UnitMapperShould {

    private UnitMapper unitMapper;

    private Unit unit;

    private Company companyInput;

    private UnitResponse output;

    @Mock
    private BusinessUnit businessUnit;
    @Mock
    private Department department;
    @Mock
    private Company company;


    @Before
    public void setup() {
        unitMapper=new UnitMapper();
        departmentInput = new Department("DM","Department of Magic",businessUnit);
        unitInput=new Unit("Meter","The basic unit of distance",department);
        output=unitMapper.mapDepartmentResponseFromDepartment(unitInput);

    }

    @Test
    public void keepSameId() {Assert.assertEquals(unitInput.getUnitId(),output.getUnitId());}

    @Test
    public void keepSameName() {Assert.assertEquals(unitInput.getUnitName(),output.getUnitName());}

    @Test
    public void keepSameDescription() {Assert.assertEquals(unitInput.getUnitDescription(),output.getUnitDescription());}

    @Test
    public void keepSameDepartment() {Assert.assertEquals(unitInput.getDepartmentRef(),output.getDepartmentName());}
}
