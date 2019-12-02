package com.controller;

import com.unisystems.controller.EmployeeController;
import com.unisystems.enums.EmployeeStatusEnum;
import com.unisystems.mapper.EmployeeMapper;
import com.unisystems.repository.EmployeeRepository;
import com.unisystems.response.EmployeeResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllEmployeeResponse;
import com.unisystems.service.EmployeeService;
import com.unisystems.strategy.employeeStrategy.SearchEmployeeStrategyFactory;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class EmployeeControllerShould {
    EmployeeController employeeController;
    EmployeeMapper employeeMapper;
    GenericResponse<GetAllEmployeeResponse> mockedResponse = new GenericResponse<>();

    @Mock
    EmployeeService mockService;

    @Mock
    EmployeeResponse employee1;

    @Mock
    EmployeeResponse employee2;

    @Mock
    Error error1;

    @Mock
    Error error2;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SearchEmployeeStrategyFactory searchEmployeeStrategyFactory;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        List<EmployeeResponse> mockedEmployees = new ArrayList<>();
        mockedEmployees.add(employee1);
        mockedEmployees.add(employee2);
        employeeMapper = new EmployeeMapper();
        mockedResponse.setData(new GetAllEmployeeResponse(mockedEmployees));
        employeeController = new EmployeeController(mockService);
    }

    @Test
    public void returnAllEmployees() {
        when(mockService.getAllEmployees()).thenReturn(mockedResponse);
        ResponseEntity<GetAllEmployeeResponse> actual = employeeController.getEmployees();
        assertThat(actual.getBody().getEmployeeResponseList(), CoreMatchers.hasItems(employee1, employee2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void returnsErrorWhenServiceFails() {
        GenericResponse<GetAllEmployeeResponse> genericError = mockServiceError();
        when(mockService.getAllEmployees()).thenReturn(genericError);
        ResponseEntity<GetAllEmployeeResponse> actual = employeeController.getEmployees();
        assertThat(genericError.getErrors(), CoreMatchers.hasItems(error1, error2));
        Assert.assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    private GenericResponse<GetAllEmployeeResponse> mockServiceError() {
        GenericResponse<GetAllEmployeeResponse> errorResponse = new GenericResponse<>();
        List<Error> mockedErrors = new ArrayList<>();
        mockedErrors.add(error1);
        mockedErrors.add(error2);
        errorResponse.setErrors(mockedErrors);
        return errorResponse;
    }

    @Test
    public void returnEmployeesWithCriteria() {
        String criteriaId="",searchCriteria="";
        when(mockService.getEmployeesWithCriteria(searchCriteria,criteriaId)).thenReturn(mockedResponse);
        ResponseEntity<GetAllEmployeeResponse> actual = employeeController.getEmployeeWithCriteria(searchCriteria, criteriaId);
        assertThat(actual.getBody().getEmployeeResponseList(), CoreMatchers.hasItems(employee1, employee2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void returnSpecificEmployee(){
        String criteriaId="4",searchCriteria="unit";
        GenericResponse<GetAllEmployeeResponse> resp = new GenericResponse<>();
        List<EmployeeResponse> empRespList = new ArrayList<>();
        EmployeeResponse expected = new EmployeeResponse(1L,2938,"Dimitris Papanikolaou","6983628263",
                "Years: 0, Months: 5, Days 3", EmployeeStatusEnum.INACTIVE,"Unisystems","ForthUnit",
                "HRConsultant");
        empRespList.add(expected);
        resp.setData(new GetAllEmployeeResponse(empRespList));
        when(mockService.getEmployeesWithCriteria(searchCriteria,criteriaId)).thenReturn(resp);
        ResponseEntity<GetAllEmployeeResponse> actual = employeeController.getEmployeeWithCriteria(searchCriteria, criteriaId);
        EmployeeResponse actualResponse = actual.getBody().getEmployeeResponseList().get(0);

        Assert.assertEquals(expected.getEmployeeId(),actualResponse.getEmployeeId());
        Assert.assertEquals(expected.getEmployeeUnitName(),actualResponse.getEmployeeUnitName());
    }
}