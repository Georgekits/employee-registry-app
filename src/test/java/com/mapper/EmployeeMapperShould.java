package com.mapper;

import com.unisystems.enums.EmployeeStatusEnum;
import com.unisystems.mapper.EmployeeMapper;
import com.unisystems.model.*;
import com.unisystems.response.EmployeeResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

@SpringBootTest
public class EmployeeMapperShould {

    @Mock
    private EmployeeMapper employeeMapper;
    private Employee employeeInput;
    private Unit unitInput;
    private Department departmentInput;
    private BusinessUnit businessUnitInput;
    private Company companyInput;
    private EmployeeResponse output;
    private EmployeeResponse employeeResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        employeeMapper = new EmployeeMapper();
        employeeInput = new Employee(1187,
                "Kitsos","George","Voutza35",
                "6972659243","2019-03-01",null,
                EmployeeStatusEnum.ACTIVE,"UniSystems","JSE");
        //Construct the hierarchy
        companyInput = new Company("UniSystems",
                "That's the first company of this project and it listens to UniSystems");
        businessUnitInput = new BusinessUnit("PublicSector",
                "That's the business unit that is well known as Public Sector",companyInput);
        departmentInput = new Department("Marketing",
                "That's the department that is well known as Marketing Dept",businessUnitInput);
        unitInput = new Unit("ThirdUnit", "That's the department that is well known as TU",departmentInput);
        employeeInput.setEmployeeUnitRef(unitInput);
        //Fetch output from mapper
        output = employeeMapper.mapEmployeeResponseFromEmployee(employeeInput);
        employeeResponse = new EmployeeResponse(1L,8843,"Elias Kotsikonas",
                "6962538645","PRESENT",EmployeeStatusEnum.ACTIVE,"UniSystems","FirstUnit","SSE");
    }

    @Test
    public void keepSameId(){
        Assert.assertEquals(employeeInput.getEmployeeId(), output.getEmployeeId());
    }

    @Test
    public void keepSameRegNumber(){
        Assert.assertEquals(employeeInput.getRegistrationNumber(), output.getRegistrationNumber());
    }
    //getEmployeeFullName
    @Test
    public void returnFullName(){
        Assert.assertEquals(employeeMapper.getEmployeeFullName(employeeInput),
                output.getFullName());
    }

    @Test
    public void keepSamePhoneNumber(){
        Assert.assertEquals(employeeInput.getPhoneNumber(), output.getPhoneNumber());
    }
    //findWorkingPeriod
    @Test
    public void returnSameWorkingPeriod(){
        Assert.assertEquals(employeeMapper.findWorkingPeriod(
                employeeInput.getRecruitmentDate(), employeeInput.getReleaseDate()
        ), output.getWorkingPeriod());
    }

    @Test
    public void keepSameEmployeeStatus(){
        Assert.assertEquals(employeeInput.getEmployeeStatus(), output.getEmployeeStatus());
    }

    @Test
    public void keepSameContractType(){
        Assert.assertEquals(employeeInput.getContractType(), output.getContractType());
    }

    @Test
    public void keepSameUnitName(){
        Unit employeeUnit = employeeInput.getEmployeeUnitRef();
        if(employeeUnit != null) {
            Assert.assertEquals(employeeInput.getEmployeeUnitRef().getUnitName()
                    ,output.getEmployeeUnitName());
        } else {
            Assert.assertEquals("N/A", output.getEmployeeUnitName());
        }
    }

    @Test
    public void keepSamePosition(){
        Assert.assertEquals(employeeInput.getPosition(), output.getPosition());
    }
    //findUnitName
    @Test
    public void returnUnitName(){
        Unit employeeUnit = employeeInput.getEmployeeUnitRef();
        if(employeeUnit != null) {
            Assert.assertEquals(employeeInput.getEmployeeUnitRef().getUnitName(),
                    employeeMapper.findUnit(employeeInput));
        }
    }
    //mapAllEmployees
    @Test
    @Ignore
    public void returnMappedEmployees(){
        List<EmployeeResponse> responses = new ArrayList<>();
        responses.add(employeeResponse);
        Object[] respObj = responses.toArray();
        List<Employee> employees = new ArrayList<Employee>() {
            {
                add( new Employee(1L,8843,"Kotsikonas",
                        "Elias","Stamatiadou5","6962538645",
                        "2007-03-01",null,EmployeeStatusEnum.ACTIVE,
                        "UniSystems","SSE"));
            }
        };

        Object[] response = employeeMapper.mapAllEmployees(employees).toArray();
        assertArrayEquals(response, respObj);
    }

    @Test
    @Ignore
    public void callEmployeeRepository(){
        //check if repo is called in getResponseFromStrategy
    }

    @Test
    @Ignore
    public void callEmployeeStrategyFactory(){
        //check if searchEmployeeStrategyFactory is called in getResponseFromStrategy
    }

    @Test
    @Ignore
    public void returnErrorIfWrongInputIsGivenInGetResponseFromStrategy(){
        //check if error is given if wrong input is given
    }
}