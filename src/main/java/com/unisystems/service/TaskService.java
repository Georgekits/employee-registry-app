package com.unisystems.service;

import com.unisystems.enums.TaskStatusEnum;
import com.unisystems.mapper.TaskMapper;
import com.unisystems.model.Task;
import com.unisystems.repository.TaskRepository;
import com.unisystems.response.TaskByIdResponse;
import com.unisystems.response.TaskResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllTaskResponse;
import com.unisystems.response.getAllResponse.GetTaskByIdResponse;
import com.unisystems.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    Utils utils;

    public GenericResponse<GetAllTaskResponse> getAllTasks() {
        Iterable<Task> retrievedTasks = taskRepository.findAll();
        List<TaskResponse> tasksList = new ArrayList<TaskResponse>();
        GenericResponse<GetAllTaskResponse> response = new GenericResponse<>();

        for (Task task : retrievedTasks){
            tasksList.add(taskMapper.mapTaskResponseFromTask(task));
        }
        response.setData(new GetAllTaskResponse(tasksList));
        return response;
    }

    public GenericResponse<GetTaskByIdResponse> findById(String taskId) {
        List<Task> retrievedTasks = (List<Task>) taskRepository.findAll();
        return taskMapper.getGenericResponseById(taskId, retrievedTasks);
    }

    public GenericResponse<GetTaskByIdResponse> findByDifficulty(String difficulty) {
        List<Task> retrievedTasks = (List<Task>) taskRepository.findAll();
        List<TaskByIdResponse> tasks = new ArrayList<>();
        for (Task task : retrievedTasks){
            tasks.add(taskMapper.mapTaskByIdResponseFromTask(task));
        }
        return taskMapper.getTasksWithDifficulty(difficulty, tasks);
    }

    public GenericResponse<GetTaskByIdResponse> findByAssignedEmployees(String assignedEmployees) {
        List<Task> retrievedTasks = (List<Task>) taskRepository.findAll();
        return taskMapper.findByAssignedEmployees(assignedEmployees, retrievedTasks);
    }

    public GenericResponse<GetTaskByIdResponse> findByAssignedEmployeesAndDifficulty(String assignedEmployees, String difficulty) {
        List<Task> retrievedTasks = (List<Task>) taskRepository.findAll();
        List<TaskByIdResponse> tasks = new ArrayList<>();
        for (Task task : retrievedTasks){
            tasks.add(taskMapper.mapTaskByIdResponseFromTask(task));
        }
        return taskMapper.findByAssignedEmployeesAndDifficulty(assignedEmployees, difficulty, tasks);
    }

    public GenericResponse<GetTaskByIdResponse> patchTask(String id, String columnName, String data) {
        List<Task> retrievedTasks = (List<Task>) taskRepository.findAll();
        return taskMapper.taskExists(id,columnName,data,retrievedTasks);
    }

    public GenericResponse<GetTaskByIdResponse> addTask(String title, String desc, String estimationA, String estimationB, String estimationC, String status, String updates) {
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();
        GenericResponse<GetTaskByIdResponse> validation =  taskMapper.validateTask(title,desc,estimationA,estimationB,estimationC,status, updates);
        if (validation.getErrors() == null){
            List<String> updatesList = Arrays.asList(updates.split(","));
            Task task = new Task(title,desc,Integer.parseInt(estimationA),Integer.parseInt(estimationB),
                    Integer.parseInt(estimationC), TaskStatusEnum.valueOf(status.toUpperCase()));
            task.getUpdates().addAll(updatesList);
            taskRepository.save(task);
            List<TaskByIdResponse> newTask = new ArrayList<>();
            newTask.add(taskMapper.mapTaskByIdResponseFromTask(task));
            genericResponse.setData(new GetTaskByIdResponse(newTask));
        } else {
            genericResponse.setErrors(validation.getErrors());
        }
        return genericResponse;
    }

    public GenericResponse<GetTaskByIdResponse> updateTask(String taskId, String title, String desc,
                                  String estimationA, String estimationB, String estimationC, String status, String updates) {
        return taskMapper.updateTask(taskId,title,desc,estimationA,estimationB,estimationC,status,updates);

    }

    public GenericResponse<String> deleteAllTasks() {
        List<Task> retrievedTasks = (List<Task>) taskRepository.findAll();
        return taskMapper.deleteTasks(retrievedTasks);
    }

    public GenericResponse<String> deleteTaskById(String taskId) {
        return taskMapper.deleteById(taskId);
    }
}