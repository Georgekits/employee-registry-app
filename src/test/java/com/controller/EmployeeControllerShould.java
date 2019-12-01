package com.controller;

import com.unisystems.controller.EmployeeController;
import com.unisystems.response.EmployeeResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllEmployeeResponse;
import com.unisystems.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class EmployeeControllerShould {
    EmployeeController employeeController;
    GenericResponse<GetAllEmployeeResponse> mockedResponse = new GenericResponse<>();

    @Mock
    EmployeeService employeeService;

    @Mock
    EmployeeResponse employee1;

    @Mock
    EmployeeResponse employee2;

    @Mock
    Error error1;

    @Mock
    Error error2;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        List<EmployeeResponse> mockedEmployees = new ArrayList<>();
        mockedEmployees.add(employee1);
        mockedEmployees.add(employee2);

        mockedResponse.setData(new GetAllEmployeeResponse(mockedEmployees));
        when(employeeService.getAllEmployees()).thenReturn(mockedResponse);
        employeeController = new EmployeeController(employeeService);
    }

    @Test
    public void returnAllEmployees() {
        ResponseEntity<GetAllEmployeeResponse> actual = employeeController.getEmployees();
        Assert.assertThat(actual.getBody().getEmployeeResponseList(), CoreMatchers.hasItems(employee1, employee2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void returnsErrorWhenServiceFails() {
        GenericResponse<GetAllEmployeeResponse> genericError = mockServiceError();
        when(employeeService.getAllEmployees()).thenReturn(genericError);
        ResponseEntity<GetAllEmployeeResponse> actual = employeeController.getEmployees();
        Assert.assertThat(genericError.getErrors(), CoreMatchers.hasItems(error1, error2));
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
        when(employeeService.getEmployeesWithCriteria(searchCriteria,criteriaId)).thenReturn(mockedResponse);
        ResponseEntity<GetAllEmployeeResponse> actual = employeeController.getEmployeeWithCriteria(criteriaId, searchCriteria);
        Assert.assertThat(actual.getBody().getEmployeeResponseList(), CoreMatchers.hasItems(employee1, employee2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }
}