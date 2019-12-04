package com.controller;

import com.unisystems.controller.TaskController;
import com.unisystems.response.TaskByIdResponse;
import com.unisystems.response.TaskResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllEmployeeResponse;
import com.unisystems.response.getAllResponse.GetAllTaskResponse;
import com.unisystems.response.getAllResponse.GetTaskByIdResponse;
import com.unisystems.service.TaskService;
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

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TaskControllerShould {
    TaskController taskController;
    GenericResponse<GetAllTaskResponse> mockedResponse = new GenericResponse<>();
    GenericResponse<GetTaskByIdResponse> mockedResponseById = new GenericResponse<>();

    @Mock
    TaskService mockService;

    @Mock
    TaskResponse task1;

    @Mock
    TaskResponse task2;

    @Mock
    TaskByIdResponse taskById1;

    @Mock
    TaskByIdResponse taskById2;

    @Mock
    Error error1;

    @Mock
    Error error2;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        List<TaskResponse> mockedTasks = new ArrayList<>();
        mockedTasks.add(task1);
        mockedTasks.add(task2);
        mockedResponse.setData(new GetAllTaskResponse(mockedTasks));

        List<TaskByIdResponse> mockedTasksById = new ArrayList<>();
        mockedTasksById.add(taskById1);
        mockedTasksById.add(taskById2);
        mockedResponseById.setData(new GetTaskByIdResponse(mockedTasksById));
        taskController = new TaskController(mockService);
    }

    @Test
    public void returnAllTasks() {
        when(mockService.getAllTasks()).thenReturn(mockedResponse);
        ResponseEntity<GetAllTaskResponse> actual = taskController.getAll();

        assertThat(actual.getBody().getTaskResponses(), CoreMatchers.hasItems(task1, task2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void returnsErrorWhenServiceFails() {
        GenericResponse<GetAllTaskResponse> genericError = mockServiceError();
        when(mockService.getAllTasks()).thenReturn(genericError);
        ResponseEntity<GetAllEmployeeResponse> actual = taskController.getAll();
        assertThat(genericError.getErrors(), CoreMatchers.hasItems(error1, error2));
        Assert.assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    private GenericResponse<GetAllTaskResponse> mockServiceError() {
        GenericResponse<GetAllTaskResponse> errorResponse = new GenericResponse<>();
        List<Error> mockedErrors = new ArrayList<>();
        mockedErrors.add(error1);
        mockedErrors.add(error2);
        errorResponse.setErrors(mockedErrors);
        return errorResponse;
    }

    @Test
    public void returnAllTasksById() {
        when(mockService.findById(any())).thenReturn(mockedResponseById);
        ResponseEntity<GetTaskByIdResponse> actual = taskController.findById(any());

        assertThat(actual.getBody().getTaskResponses(), CoreMatchers.hasItems(taskById1, taskById2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void returnAllTasksByDifficulty() {
        when(mockService.findByDifficulty(any())).thenReturn(mockedResponseById);
        ResponseEntity<GetTaskByIdResponse> actual = taskController.findByDifficulty("test");

        assertThat(actual.getBody().getTaskResponses(), CoreMatchers.hasItems(taskById1, taskById2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void returnAllTasksByAssignedEmployees() {
        when(mockService.findByAssignedEmployees(any())).thenReturn(mockedResponseById);
        ResponseEntity<GetTaskByIdResponse> actual = taskController.findByAssignedEmployees("12");

        assertThat(actual.getBody().getTaskResponses(), CoreMatchers.hasItems(taskById1, taskById2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void returnAllTasksByAssignedEmployeesAndDifficulty() {
        when(mockService.findByAssignedEmployeesAndDifficulty(any(),any())).thenReturn(mockedResponseById);
        ResponseEntity<GetTaskByIdResponse> actual = taskController.findByAssignedEmployeesAndDifficulty("12","TEST");

        assertThat(actual.getBody().getTaskResponses(), CoreMatchers.hasItems(taskById1, taskById2));
        Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
    }
}