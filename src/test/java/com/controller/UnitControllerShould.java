package com.controller;

import com.unisystems.controller.UnitController;
import com.unisystems.mapper.UnitMapper;
import com.unisystems.model.Department;
import com.unisystems.model.Unit;
import com.unisystems.repository.UnitRepository;
import com.unisystems.response.UnitResponse;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllUnitResponse;
import com.unisystems.service.UnitService;
import org.apache.coyote.Response;
import org.hamcrest.CoreMatchers;
import org.hibernate.validator.constraints.ModCheck;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.unisystems.response.generic.Error;
import sun.net.www.content.text.Generic;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitControllerShould {

    UnitController unitController;
    GenericResponse<GetAllUnitResponse> mockedResponse = new GenericResponse<>();

    @Mock
    UnitService mockedService;
    @Mock
    UnitResponse unit1;
    @Mock
    UnitResponse unit2;

    @Mock
    Error error;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ArrayList<UnitResponse> mockedUnits = new ArrayList<>();

        mockedUnits.add(unit1);
        mockedUnits.add(unit2);
        mockedResponse.setData(new GetAllUnitResponse(mockedUnits));
        unitController = new UnitController(mockedService);


    }

    @Test
    public void returnAllUnits() {
        when(mockedService.getAllUnits()).thenReturn(mockedResponse);
        ResponseEntity<GetAllUnitResponse> actual = unitController.getAllUnits();
        Assert.assertThat(actual.getBody().getUnitResponseList(), CoreMatchers.hasItems(unit1, unit2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void returnErrorWhenFailureAtService(){
        GenericResponse<GetAllUnitResponse> response = mockServiceError();

        when(mockedService.getAllUnits()).thenReturn(response);
        ResponseEntity<GetAllUnitResponse> actual = unitController.getAllUnits();
        Assert.assertThat(response.getErrors(), CoreMatchers.hasItem(error));
    }



    public GenericResponse<GetAllUnitResponse> mockServiceError(){
        GenericResponse<GetAllUnitResponse> errorResponse = new GenericResponse<>();
        List<Error> mockedErrors = new ArrayList<>();
        mockedErrors.add(error);
        errorResponse.setErrors(mockedErrors);
        return errorResponse;
    }







}
