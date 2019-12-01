package com.mapper;

import com.unisystems.enums.EmployeeStatusEnum;
import com.unisystems.enums.TaskStatusEnum;
import com.unisystems.mapper.TaskMapper;
import com.unisystems.model.Department;
import com.unisystems.model.Employee;
import com.unisystems.model.Task;
import com.unisystems.model.Unit;
import com.unisystems.response.TaskByIdResponse;
import com.unisystems.response.TaskResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class TaskMapperShould {

    private TaskMapper mapper;
    private Task taskInput;
    private Employee employeeInput;
    private Unit unitInput;
    private TaskResponse taskResponseOutput;
    private TaskByIdResponse taskByIdResponseOutput;

    @Mock
    Department humanResources;

    @Before
    public void setup(){
        mapper = new TaskMapper();
        taskInput = new Task("New Portal","This is a task for making a new portal",5,9,8, TaskStatusEnum.NEW);
        taskInput.setId(100L);
        employeeInput =  new Employee(1187,"Kitsos","George",
                "Voutza35","6972659243","2019-03-01",
                null, EmployeeStatusEnum.ACTIVE,"UniSystems","JSE");
        employeeInput.setEmployeeId(101L);
        unitInput = new Unit("FirstUnit", "That's the department that is well known as FU",humanResources);
        unitInput.setUnitId(102L);
        employeeInput.setEmployeeUnitRef(unitInput);
        taskInput.getEmployeesList().add(employeeInput);
        taskInput.getUpdates().add("New update");
        taskResponseOutput = mapper.mapTaskResponseFromTask(taskInput);
        taskByIdResponseOutput = mapper.mapTaskByIdResponseFromTask(taskInput);
    }

    @Test
    public void keepSameId(){
        Assert.assertEquals(100, taskResponseOutput.getTaskId());
        Assert.assertEquals(100,taskByIdResponseOutput.getTaskId());
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
        Assert.assertEquals(mapper.getDifficulty(taskInput), taskResponseOutput.getDifficulty());
        Assert.assertEquals(mapper.getDifficulty(taskInput), taskByIdResponseOutput.getDifficulty());
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



}
