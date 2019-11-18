package com.unisystems.mapper;

import com.unisystems.enums.TaskDifficultyEnum;
import com.unisystems.model.Task;
import com.unisystems.repository.TaskRepository;
import com.unisystems.response.TaskByIdResponse;
import com.unisystems.response.TaskResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetTaskByIdResponse;
import com.unisystems.strategy.taskStrategy.SearchDifficultyStrategy;
import com.unisystems.strategy.taskStrategy.SearchDifficultyStrategyFactory;
import com.unisystems.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {

    @Autowired
    private Utils utils;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SearchDifficultyStrategyFactory searchDifficultyStrategyFactory;

    public TaskResponse mapTaskResponseFromTask(Task task) {
        TaskResponse taskResponse = new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDesc(),
                getDifficulty(task),
                task.getStatus()
        );
        return taskResponse;
    }

    public TaskByIdResponse mapTaskByIdResponseFromTask(Task task) {
        TaskByIdResponse taskByIdResponse = new TaskByIdResponse(
                task.getId(),
                task.getTitle(),
                task.getDesc(),
                getDifficulty(task),
                task.getStatus(),
                task.getEmployeeInfo(task),
                task.getUpdates()
        );
        return taskByIdResponse;
    }

    private TaskDifficultyEnum getDifficulty(Task task) {
        int avgDifficulty = ((task.getEstimationA()+task.getEstimationB()+task.getEstimationC())/3);

        if (avgDifficulty < 2) return TaskDifficultyEnum.EASY;
        else if (avgDifficulty <= 4) return TaskDifficultyEnum.MEDIUM;
        else return TaskDifficultyEnum.HARD;
    }

    public GenericResponse<GetTaskByIdResponse> getGenericResponseById(String taskId, List<Task> retrievedTasks) {
        List<Error> errors = new ArrayList<Error>();
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();

        //Critical errors, filters
        if(!utils.isNumeric(taskId)) {
            Error error = new Error(1023,"ID NUMERIC ONLY","The taskId should be numeric");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        boolean taskExists = taskRepository.findById(Long.parseLong(taskId)).isPresent();
        List<TaskByIdResponse> taskResponse = new ArrayList<TaskByIdResponse>();

        taskResponse.addAll(mapAllTasksById(retrievedTasks, taskId));

        if(!taskExists){
            Error error = new Error(105,"No data fetched","This task with id: "+taskId
                    +" does not exist.");
            errors.add(error);
        } else if(taskResponse.size() == 0){
            Error error = new Error(100,"No tasks","There are no tasks with that id.");
            errors.add(error);
        }

        //Construct response
        if(errors.size() != 0) {
            genericResponse.setErrors(errors);
        } else {
            genericResponse.setData(new GetTaskByIdResponse(taskResponse));
        }

        return genericResponse;
    }

    public List<TaskByIdResponse> mapAllTasksById(List<Task> tasks, String taskId) {
        List<TaskByIdResponse> taskResponses = new ArrayList<TaskByIdResponse>();
        for(Task t: tasks){
            if(t.getId() == Long.parseLong(taskId)) taskResponses.add(mapTaskByIdResponseFromTask(t));
        }
        return taskResponses;
    }

    public GenericResponse<GetTaskByIdResponse> getTasksWithDifficulty(String difficulty, List<TaskByIdResponse> retrievedTasks) {
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();
        List<Error> errors = new ArrayList<>();
        List<TaskByIdResponse> taskResponses = new ArrayList<>();
        //Strategy & Factory Design Pattern
        SearchDifficultyStrategy strategy = searchDifficultyStrategyFactory.makeStrategyForDifficulty(difficulty);
        if (strategy == null) {
            Error error = new Error(100,
                    "SearchCriteria incorrect",
                    "It should be one of the following: [easy,medium,hard]");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //If strategy is OK
        taskResponses = strategy.executeTask(difficulty, retrievedTasks);
        if(taskResponses.size() == 0) {
            Error error = new Error(1009,
                    "Difficulty N/A",
                    "No such task with that difficulty " +
                            "or this difficulty level does not contain any tasks");
            errors.add(error);
            genericResponse.setErrors(errors);
        }
        //Construct response
        if(errors.size() != 0) {
            genericResponse.setErrors(errors);
        } else {
            genericResponse.setData(new GetTaskByIdResponse(taskResponses));
        }
        return genericResponse;
    }

    public GenericResponse<GetTaskByIdResponse> findByAssignedEmployees(String assignedEmployees, List<Task> retrievedTasks) {
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();
        List<Error> errors = new ArrayList<>();
        List<TaskByIdResponse> taskResponses = new ArrayList<>();
        if(!utils.isNumeric(assignedEmployees)){
            Error error = new Error(25,"Wrong assignedEmployees parameter",
                    "The provided assignedEmployees parameter should be numeric");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }

        for(Task task: retrievedTasks){
            if (task.getEmployeesList().size() == Integer.parseInt(assignedEmployees)) {
                taskResponses.add(mapTaskByIdResponseFromTask(task));
            }
        }
        if(taskResponses.isEmpty()){
            Error error = new Error(24,"No tasks",
                    "No tasks has been assigned to "+assignedEmployees+" employees");
            errors.add(error);
            genericResponse.setErrors(errors);
        }
        //Construct response
        if(errors.size() != 0) {
            genericResponse.setErrors(errors);
        } else {
            genericResponse.setData(new GetTaskByIdResponse(taskResponses));
        }
        return genericResponse;
    }

    public GenericResponse<GetTaskByIdResponse> findByAssignedEmployeesAndDifficulty(String assignedEmployees, String difficulty, List<TaskByIdResponse> retrievedTasks) {
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();
        List<Error> errors = new ArrayList<>();
        List<TaskByIdResponse> taskResponses = new ArrayList<>();
        if(!utils.isNumeric(assignedEmployees)){
            Error error = new Error(25,"Wrong assignedEmployees parameter",
                    "The provided assignedEmployees parameter should be numeric");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //Strategy & Factory Design Pattern
        SearchDifficultyStrategy strategy = searchDifficultyStrategyFactory.makeStrategyForDifficulty(difficulty);
        if (strategy == null) {
            Error error = new Error(100,
                    "SearchCriteria incorrect",
                    "It should be one of the following: [easy,medium,hard]");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //If strategy is OK
        taskResponses = strategy.executeTask(difficulty, retrievedTasks);
        List<TaskByIdResponse> modifiedTasks = new ArrayList<>();
        for(TaskByIdResponse task: taskResponses){
            if(task.getAssignedEmployees().size() == Integer.parseInt(assignedEmployees)) modifiedTasks.add(task);
        }
        if(modifiedTasks.size() == 0) {
            Error error = new Error(1009,
                    "Difficulty N/A",
                    "No such task with that difficulty has been assigned to " +assignedEmployees+" employees "+
                            "or this difficulty level does not contain any tasks");
            errors.add(error);
            genericResponse.setErrors(errors);
        }

        //Construct response
        if(errors.size() != 0) {
            genericResponse.setErrors(errors);
        } else {

            genericResponse.setData(new GetTaskByIdResponse(modifiedTasks));
        }
        return genericResponse;
    }
}