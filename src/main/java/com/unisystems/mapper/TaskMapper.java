package com.unisystems.mapper;

import com.unisystems.enums.TaskDifficultyEnum;
import com.unisystems.enums.TaskStatusEnum;
import com.unisystems.model.Task;
import com.unisystems.repository.TaskRepository;
import com.unisystems.response.TaskByIdResponse;
import com.unisystems.response.TaskResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllTaskResponse;
import com.unisystems.response.getAllResponse.GetTaskByIdResponse;
import com.unisystems.strategy.taskStrategy.SearchDifficultyStrategy;
import com.unisystems.strategy.taskStrategy.SearchDifficultyStrategyFactory;
import com.unisystems.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Component
public class TaskMapper {

    @Autowired
    private Utils utils;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SearchDifficultyStrategyFactory searchDifficultyStrategyFactory;

    private String regex = "^[a-zA-Z0-9,]*$";

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
        int avgDifficulty = ((task.getEstimationA() + task.getEstimationB() + task.getEstimationC()) / 3);

        if (avgDifficulty < 2) return TaskDifficultyEnum.EASY;
        else if (avgDifficulty <= 4) return TaskDifficultyEnum.MEDIUM;
        else return TaskDifficultyEnum.HARD;
    }

    public GenericResponse<GetTaskByIdResponse> getGenericResponseById(String taskId, List<Task> retrievedTasks) {
        List<Error> errors = new ArrayList<Error>();
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();

        //Critical errors, filters
        if (!utils.isNumeric(taskId)) {
            Error error = new Error(1023, "ID NUMERIC ONLY", "The taskId should be numeric");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        boolean taskExists = taskRepository.findById(Long.parseLong(taskId)).isPresent();
        List<TaskByIdResponse> taskResponse = new ArrayList<TaskByIdResponse>();

        taskResponse.addAll(mapAllTasksById(retrievedTasks, taskId));

        if (!taskExists) {
            Error error = new Error(105, "No data fetched", "This task with id: " + taskId
                    + " does not exist.");
            errors.add(error);
        } else if (taskResponse.size() == 0) {
            Error error = new Error(100, "No tasks", "There are no tasks with that id.");
            errors.add(error);
        }

        //Construct response
        if (errors.size() != 0) {
            genericResponse.setErrors(errors);
        } else {
            genericResponse.setData(new GetTaskByIdResponse(taskResponse));
        }

        return genericResponse;
    }

    public List<TaskByIdResponse> mapAllTasksById(List<Task> tasks, String taskId) {
        List<TaskByIdResponse> taskResponses = new ArrayList<TaskByIdResponse>();
        for (Task t : tasks) {
            if (t.getId() == Long.parseLong(taskId)) taskResponses.add(mapTaskByIdResponseFromTask(t));
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
        if (taskResponses.size() == 0) {
            Error error = new Error(1009,
                    "Difficulty N/A",
                    "No such task with that difficulty " +
                            "or this difficulty level does not contain any tasks");
            errors.add(error);
            genericResponse.setErrors(errors);
        }
        //Construct response
        if (errors.size() != 0) {
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
        if (!utils.isNumeric(assignedEmployees)) {
            Error error = new Error(25, "Wrong assignedEmployees parameter",
                    "The provided assignedEmployees parameter should be numeric");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }

        for (Task task : retrievedTasks) {
            if (task.getEmployeesList().size() == Integer.parseInt(assignedEmployees)) {
                taskResponses.add(mapTaskByIdResponseFromTask(task));
            }
        }
        if (taskResponses.isEmpty()) {
            Error error = new Error(24, "No tasks",
                    "No tasks has been assigned to " + assignedEmployees + " employees");
            errors.add(error);
            genericResponse.setErrors(errors);
        }
        //Construct response
        if (errors.size() != 0) {
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
        if (!utils.isNumeric(assignedEmployees)) {
            Error error = new Error(25, "Wrong assignedEmployees parameter",
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
        for (TaskByIdResponse task : taskResponses) {
            if (task.getAssignedEmployees().size() == Integer.parseInt(assignedEmployees)) modifiedTasks.add(task);
        }
        if (modifiedTasks.size() == 0) {
            Error error = new Error(1009,
                    "Difficulty N/A",
                    "No such task with that difficulty has been assigned to " + assignedEmployees + " employees " +
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

    public GenericResponse<GetTaskByIdResponse> updateTask(String taskId, String title, String desc, String estimationA,
                                                          String estimationB, String estimationC, String status, String updates) {
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();
        List<Error> errors = new ArrayList<>();
        if(!utils.isNumeric(taskId)){
            Error error = new Error(2,
                    "Incorrect taskId",
                    "The given taskId should always be numeric");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //taskId is OK
        Task taskToBeUpdated = new Task();
        try {
            taskToBeUpdated = taskRepository.findById(Long.parseLong(taskId)).get();
        } catch(NoSuchElementException e){
            taskToBeUpdated =null;
        }
        if(taskToBeUpdated == null){
            Error error = new Error(3,
                    "Task N/A",
                    "The requested taskId does not map with any of the available tasks");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //taskId is OK and exists, then check input
        GenericResponse<GetTaskByIdResponse> taskValidation = validateTask(title,desc,estimationA,estimationB,estimationC,status, updates);
        System.out.println("taskValidation: "+taskValidation);
        if(taskValidation.getErrors() == null){
            taskToBeUpdated.setDesc(desc);
            taskToBeUpdated.setEstimationA(Integer.parseInt(estimationA));
            taskToBeUpdated.setEstimationB(Integer.parseInt(estimationB));
            taskToBeUpdated.setEstimationC(Integer.parseInt(estimationC));
            taskToBeUpdated.setTitle(title);
            taskToBeUpdated.setStatus(
                    TaskStatusEnum.valueOf(status.toUpperCase())
            );
            List<String> updatesList = Arrays.asList(updates.split(","));
            taskToBeUpdated.getUpdates().addAll(updatesList);
            taskRepository.save(taskToBeUpdated);
            List<TaskByIdResponse> newTask = new ArrayList<>();
            newTask.add(mapTaskByIdResponseFromTask(taskToBeUpdated));
            genericResponse.setData(new GetTaskByIdResponse(newTask));
        } else {
            genericResponse.setErrors(taskValidation.getErrors());
        }
        return genericResponse;
    }
    public GenericResponse<GetTaskByIdResponse> taskExists(String id, String columnName, String data, List<Task> tasks) {
        List<Error> errors = new ArrayList<Error>();
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();

        if (!utils.isNumeric(id)) {
            Error error = new Error(1023, "ID NUMERIC ONLY", "The taskId should be numeric");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //taskId is OK
        Task tasktoBePatched = new Task();
        try {
            tasktoBePatched = taskRepository.findById(Long.parseLong(id)).get();
        } catch(NoSuchElementException e){
            tasktoBePatched =null;
        }

        if (tasktoBePatched == null) {
            Error error = new Error(1023, "Task with id " + id + " does not exist", "Please give an existing id");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //task exists and is OK
        if(!(columnName.equals("title")|| columnName.equals("desc") || columnName.equals("estimationA") ||
                columnName.equals("estimationB")|| columnName.equals("estimationC") || columnName.equals("estimationA") ||
                columnName.equals("status") || columnName.equals("updates"))) {

            Error error = new Error(166,
                    "This attribute is not a task property",
                    "Please give a valid attribute");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //columnName is OK

        switch (columnName) {
            case "title":
                tasktoBePatched.setTitle(data);
                taskRepository.save(tasktoBePatched);
                break;
            case "desc":
                tasktoBePatched.setDesc(data);
                taskRepository.save(tasktoBePatched);
                break;
            case "estimationA":
                if (!utils.isNumeric(data)) {
                    Error error = new Error(2,
                            "The estimationA is not Numeric",
                            "Estimation must be always a number");
                    errors.add(error);
                    genericResponse.setErrors(errors);

                    return genericResponse;
                }
                tasktoBePatched.setEstimationA(Integer.parseInt(data));
                taskRepository.save(tasktoBePatched);
                break;
            case "estimationB":

                if (!utils.isNumeric(data)) {
                    Error error = new Error(2,
                            "The estimationB is not Numeric",
                            "Estimation must be always a number");
                    errors.add(error);
                    genericResponse.setErrors(errors);

                    return genericResponse;
                }
                tasktoBePatched.setEstimationB(Integer.parseInt(data));
                taskRepository.save(tasktoBePatched);
                break;
            case "estimationC":

                if (!utils.isNumeric(data)) {
                    Error error = new Error(2,
                            "The estimationC is not Numeric",
                            "Estimation must be always a number");
                    errors.add(error);
                    genericResponse.setErrors(errors);

                    return genericResponse;
                }
                tasktoBePatched.setEstimationC(Integer.parseInt(data));
                taskRepository.save(tasktoBePatched);
                break;
            case "status":
                tasktoBePatched.setStatus(TaskStatusEnum.valueOf(data.toUpperCase()));
                taskRepository.save(tasktoBePatched);
                break;
            case "updates":
                boolean matches = Pattern.matches(regex, data);

                if (!matches) {
                    Error error = new Error(4,
                            "Wrong input in updates",
                            "Try to delimit your list only with comma");
                    errors.add(error);
                    genericResponse.setErrors(errors);
                    return genericResponse;
                }
                List<String> updatesList = Arrays.asList(data.split(","));
                tasktoBePatched.getUpdates().addAll(updatesList);
                taskRepository.save(tasktoBePatched);
                break;
        }
        List<TaskByIdResponse> taskResponse = new ArrayList<TaskByIdResponse>();
        taskResponse.addAll(mapAllTasksById(tasks, id));
        genericResponse.setData(new GetTaskByIdResponse(taskResponse));
        return genericResponse;
    }

    public GenericResponse<GetTaskByIdResponse> validateTask(String title, String desc, String estimationA, String estimationB, String estimationC, String status, String updates) {
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();
        List<Error> errors = new ArrayList<>();

        if (!utils.isNumeric(estimationA) || !utils.isNumeric(estimationB) || !utils.isNumeric(estimationC)) {
            Error error = new Error(2,
                    "Some of the estimation is not Numeric",
                    "Estimation must be always a number");
            errors.add(error);
            genericResponse.setErrors(errors);
        }
        if (!(status.equalsIgnoreCase(String.valueOf(TaskStatusEnum.NEW)) || status.equalsIgnoreCase(String.valueOf(TaskStatusEnum.STARTED)) || status.equalsIgnoreCase(String.valueOf(TaskStatusEnum.DONE)))){
            Error error = new Error(3,
                    "Status does not exist",
                    "TRY NEW,STARTED,DONE");
            errors.add(error);
            genericResponse.setErrors(errors);
        }

        boolean matches = Pattern.matches(regex, updates);
        if (!matches) {
            Error error = new Error(4,
                    "Wrong input in updates",
                    "Try to delimit your list only with comma");
            errors.add(error);
            genericResponse.setErrors(errors);
        }
        return genericResponse;
    }

    public GenericResponse<String> deleteTasks(List<Task> retrievedTasks) {
        GenericResponse<String> response = new GenericResponse<>();
        List<Error> errors = new ArrayList<>();
        if(retrievedTasks.size() == 0) {
            Error error = new Error(345,"No tasks.","No tasks have been found");
            errors.add(error);
            response.setErrors(errors);
        } else {
            taskRepository.deleteAll();
        }
        return response;
    }

    public GenericResponse<String> deleteById(String taskId) {
        GenericResponse<String> genericResponse = new GenericResponse<>();
        List<Error> errors = new ArrayList<>();
        if(!utils.isNumeric(taskId)){
            Error error = new Error(234, "Wrong taskId",
                    "The provided taskId parameter should be numeric");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //taskId is OK
        long formattedTaskId = Long.parseLong(taskId);
        boolean taskExists = taskRepository.findById(formattedTaskId).isPresent();
        if(!taskExists){
            Error error = new Error(234, "Task N/A",
                    "The provided taskId does not match with any task");
            errors.add(error);
            genericResponse.setErrors(errors);
        } else {
            taskRepository.deleteById(formattedTaskId);
        }
        return genericResponse;
    }
}