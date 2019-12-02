package com.mapper;

import com.unisystems.enums.EmployeeStatusEnum;
import com.unisystems.enums.TaskStatusEnum;
import com.unisystems.mapper.TaskMapper;
import com.unisystems.model.Department;
import com.unisystems.model.Employee;
import com.unisystems.model.Task;
import com.unisystems.model.Unit;
import com.unisystems.repository.TaskRepository;
import com.unisystems.response.TaskByIdResponse;
import com.unisystems.response.TaskResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetTaskByIdResponse;
import com.unisystems.utils.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class TaskMapperShould {
    GenericResponse<GetTaskByIdResponse> mockedResponse = new GenericResponse<>();
    @InjectMocks
    private TaskMapper taskMapper;
    @Mock
    private TaskMapper mockedTaskMapper;
    @Mock
    private Task taskInput;
    @Mock
    private Task taskInput2;
    private Employee employeeInput;
    private Unit unitInput;
    private TaskResponse taskResponseOutput;
    private TaskByIdResponse taskByIdResponseOutput;
    private GenericResponse<GetTaskByIdResponse> genericOutput;
    private List<Task> mockedTasks = new ArrayList<>();

    @Mock
    Department humanResources;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    Utils utils;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
         List<Task> mockedTasks = new ArrayList<>();
        taskMapper = new TaskMapper(utils,taskRepository);
        taskInput = new Task("New Portal","This is a task for making a new portal",5,9,8, TaskStatusEnum.NEW);
        taskInput.setId(1L);
        employeeInput =  new Employee(1187,"Kitsos","George",
                "Voutza35","6972659243","2019-03-01",
                null, EmployeeStatusEnum.ACTIVE,"UniSystems","JSE");
        employeeInput.setEmployeeId(101L);
        unitInput = new Unit("FirstUnit", "That's the department that is well known as FU",humanResources);
        unitInput.setUnitId(102L);
        employeeInput.setEmployeeUnitRef(unitInput);
        taskInput.getEmployeesList().add(employeeInput);
        taskInput.getUpdates().add("New update");
        taskResponseOutput = taskMapper.mapTaskResponseFromTask(taskInput);
        taskByIdResponseOutput = taskMapper.mapTaskByIdResponseFromTask(taskInput);
        mockedTasks.add(taskInput);
        mockedTasks.add(taskInput2);
        GenericResponse<GetTaskByIdResponse> mockedResponse = new GenericResponse(mockedTasks);
        when(taskRepository.findAll()).thenReturn(mockedTasks);
    }

    @Test
    public void keepSameId(){
        Assert.assertEquals(1, taskResponseOutput.getTaskId());
        Assert.assertEquals(1,taskByIdResponseOutput.getTaskId());
    }

    @Test
    public void keepSameTitle(){
        Assert.assertEquals(taskInput.getTitle(), taskResponseOutput.getTaskTitle());
        Assert.assertEquals(taskInput.getTitle(), taskByIdResponseOutput.getTaskTitle());
    }


    @Test
    public void keepSameDesk(){
        Assert.assertEquals(taskInput.getDesc(), taskResponseOutput.getTaskDesc());
        Assert.assertEquals(taskInput.getDesc(), taskByIdResponseOutput.getTaskDesc());
    }

    @Test
    public void keepSameDifficulty(){
        Assert.assertEquals(taskMapper.getDifficulty(taskInput), taskResponseOutput.getDifficulty());
        Assert.assertEquals(taskMapper.getDifficulty(taskInput), taskByIdResponseOutput.getDifficulty());
    }

    @Test
    public void keepSameStatus(){
        Assert.assertEquals(taskInput.getStatus(), taskResponseOutput.getTaskStatus());
        Assert.assertEquals(taskInput.getStatus(), taskByIdResponseOutput.getTaskStatus());
    }

    @Test
    public void KeepSameEmployeeInfo(){
        Assert.assertEquals(taskInput.getEmployeeInfo(taskInput),taskByIdResponseOutput.getAssignedEmployees());
    }

    @Test
    public  void KeepSameUpdates(){
        Assert.assertEquals(taskInput.getUpdates(),taskByIdResponseOutput.getUpdates());
    }

    @Test
    public void ReturnTaskById(){
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        List<Error> errors = new ArrayList<Error>();
        List<TaskByIdResponse> taskResponse = new ArrayList<TaskByIdResponse>();
        GenericResponse<GetTaskByIdResponse> responseData = new GenericResponse<>();
        GenericResponse<GetTaskByIdResponse> responseError = new GenericResponse<>();
        Task task = new Task("New Portal","This is a task for making a new portal",5,9,8, TaskStatusEnum.NEW);
        task.setId(1L);
        taskResponse.addAll(taskMapper.mapAllTasksById(tasks,"1"));
        responseData.setData( new GetTaskByIdResponse(taskResponse));
        Error numericError = new Error(1023, "ID NUMERIC ONLY", "The taskId should be numeric");
        errors.add(numericError);
        responseError.setErrors(errors);
        when(mockedTaskMapper.getGenericResponseById("1",tasks)).thenReturn(responseData);
        when(mockedTaskMapper.getGenericResponseById("k",tasks)).thenReturn(responseError);

        GenericResponse<GetTaskByIdResponse> actualDto = mockedTaskMapper.getGenericResponseById("1",tasks);
        GenericResponse<GetTaskByIdResponse> actualNumericError = mockedTaskMapper.getGenericResponseById("k",tasks);
        assertNotNull(actualDto);
        Assert.assertEquals(task.getTitle(),actualDto.getData().getTaskResponses().get(0).getTaskTitle());
        Assert.assertEquals("ID NUMERIC ONLY",actualNumericError.getErrors().get(0).getErrorMessage());
   }
}