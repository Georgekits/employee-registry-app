package com.controller;

import com.unisystems.controller.DepartmentController;
import com.unisystems.mapper.DepartmentMapper;
import com.unisystems.repository.DepartmentRepository;
import com.unisystems.response.DepartmentResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllDepartmentResponse;
import com.unisystems.service.DepartmentService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

public class DepartmentControllerShould {

    DepartmentController departmentController;
    DepartmentMapper departmentMapper;
    GenericResponse<GetAllDepartmentResponse> mockedResponse=new GenericResponse<>();

    @Mock
    DepartmentService mockService;
    @Mock
    DepartmentResponse department1;
    @Mock
    DepartmentResponse department2;
    @Mock
    Error error1;
    @Mock
    Error error2;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        List<DepartmentResponse> mockedDepartments=new ArrayList<>();
        mockedDepartments.add(department1);
        mockedDepartments.add(department2);
        departmentMapper=new DepartmentMapper();
        mockedResponse.setData(new GetAllDepartmentResponse(mockedDepartments));
        departmentController=new DepartmentController(mockService);
    }

    @Test
    public void returnAllDepartments(){
        when(mockService.getAllDepartments()).thenReturn(mockedResponse);
        ResponseEntity<GetAllDepartmentResponse> actual=departmentController.getDepartments();
        assertThat(actual.getBody().getDepartmentResponseList(), CoreMatchers.hasItems(department1,department2));
        Assert.assertEquals(HttpStatus.OK,actual.getStatusCode());
    }

    @Test
    public void returnErrorWhenServiceFails(){
        GenericResponse<GetAllDepartmentResponse> genericError=mockServiceError();
        when(mockService.getAllDepartments()).thenReturn(genericError);
        ResponseEntity<GetAllDepartmentResponse> actual= departmentController.getDepartments();
        assertThat(genericError.getErrors(),CoreMatchers.hasItems(error1,error2));
        Assert.assertEquals(HttpStatus.BAD_REQUEST,actual.getStatusCode());

    }

    private GenericResponse<GetAllDepartmentResponse> mockServiceError(){
        GenericResponse<GetAllDepartmentResponse> errorResponse=new GenericResponse<>();
        List<Error> mockedErrors=new ArrayList<>();
        mockedErrors.add(error1);
        mockedErrors.add(error2);
        errorResponse.setErrors(mockedErrors);
        return errorResponse;
    }
}
