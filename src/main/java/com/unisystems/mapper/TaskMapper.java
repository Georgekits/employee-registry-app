package com.unisystems.mapper;

import com.unisystems.enums.TaskDifficultyEnum;
import com.unisystems.enums.TaskStatusEnum;
import com.unisystems.model.Employee;
import com.unisystems.model.Task;
import com.unisystems.repository.EmployeeRepository;
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

import java.util.*;
import java.util.regex.Pattern;

@Component
public class TaskMapper {

    @Autowired
    private Utils utils;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SearchDifficultyStrategyFactory searchDifficultyStrategyFactory;

    public TaskMapper(Utils utils, TaskRepository taskRepository) {
        this.utils = utils;
        this.taskRepository = taskRepository;
    }

    private String regexUpdates = "^[a-zA-Z0-9,]*$";
    private String regexEmployees = "^[0-9,]*$";

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

    public TaskDifficultyEnum getDifficulty(Task task) {
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
                                                           String estimationB, String estimationC, String status, String updates,
                                                           String employees) {
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
        GenericResponse<GetTaskByIdResponse> taskValidation = validateTask(title,desc,estimationA,estimationB,estimationC,status, updates,employees);

        if(taskValidation.getErrors() == null){
            taskToBeUpdated.setDesc(desc);
            taskToBeUpdated.setEstimationA(Integer.parseInt(estimationA));
            taskToBeUpdated.setEstimationB(Integer.parseInt(estimationB));
            taskToBeUpdated.setEstimationC(Integer.parseInt(estimationC));
            taskToBeUpdated.setTitle(title);
            taskToBeUpdated.setStatus(
                    TaskStatusEnum.valueOf(status.toUpperCase())
            );

            if(!updates.equals("null")){
                List<String> updatesList = Arrays.asList(updates.split(","));
                taskToBeUpdated.getUpdates().addAll(updatesList);
            }
            if(!employees.equals("null")){
                List<String> employeesIdList = Arrays.asList(employees.split(","));
                List<Employee> assignEmployeesList = new ArrayList<>();

                for (String employeeId: employeesIdList) {
                    boolean employeeExists = false;
                    if(taskToBeUpdated.getEmployeesList() != null){
                        for(Employee emp: taskToBeUpdated.getEmployeesList()){
                            if(emp.getEmployeeId() == Long.parseLong(employeeId)){
                                employeeExists = true;
                                break;
                            }
                        }
                    }
                    if(!employeeExists) assignEmployeesList.add(employeeRepository.findById(Long.parseLong(employeeId)).get());
                }
                taskToBeUpdated.getEmployeesList().addAll(assignEmployeesList);
            }
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
        String taskUnitName = new String();


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
                columnName.equals("status") || columnName.equals("updates") || columnName.equals("employees"))) {

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
                break;
            case "desc":
                tasktoBePatched.setDesc(data);
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
                break;
            case "status":
                if (!(data.equalsIgnoreCase(String.valueOf(TaskStatusEnum.NEW)) || data.equalsIgnoreCase(String.valueOf(TaskStatusEnum.STARTED)) ||
                        data.equalsIgnoreCase(String.valueOf(TaskStatusEnum.DONE)))){
                    Error error = new Error(3,
                            "Status does not exist",
                            "TRY NEW,STARTED,DONE");
                    errors.add(error);
                    genericResponse.setErrors(errors);
                } else {
                    tasktoBePatched.setStatus(TaskStatusEnum.valueOf(data.toUpperCase()));
                }
                break;
            case "updates":
                boolean matches = Pattern.matches(regexUpdates, data);

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
                break;
            case "employees":
                boolean matchesEmp = Pattern.matches(regexEmployees, data);

                if (!matchesEmp) {
                    Error error = new Error(4,
                            "Wrong input in updates",
                            "Try to delimit your list only with comma");
                    errors.add(error);
                    genericResponse.setErrors(errors);
                    return genericResponse;
                } else {
                    List<String> assignEmployeesIdList = Arrays.asList(data.split(","));
                    Task taskToBePatch = taskRepository.findById(Long.parseLong(id)).get();
                    if (taskToBePatch.getEmployeesList().size() == 0) {
                        boolean flag = true;
                        HashMap<String, Integer> employeeMap = new HashMap<>();
                        String employeeUnit = new String();
                        for (String employeeId : assignEmployeesIdList) {
                            boolean employeeExists = employeeRepository.findById(Long.parseLong(employeeId)).isPresent();
                            if (!employeeExists) {
                                Error error = new Error(5,
                                        "Employee with id " + employeeId + " does not exist",
                                        "Assign an existing Employee");
                                errors.add(error);
                                genericResponse.setErrors(errors);
                                return genericResponse;
                            }
                            boolean idExists = String.valueOf(employeeMap.get(employeeId)) != null;
                            if (idExists) {
                                employeeMap.put(employeeId, employeeMap.get(employeeId) == null ? 1 : employeeMap.get(employeeId) + 1);
                            }
                            if (flag) {
                                taskUnitName = employeeRepository.findById(Long.parseLong(employeeId)).get().getEmployeeUnitRef().getUnitName();
                                flag = false;

                            } else {
                                employeeUnit = employeeRepository.findById(Long.parseLong(employeeId)).get().getEmployeeUnitRef().getUnitName();
                                if (!(employeeUnit.equalsIgnoreCase(taskUnitName))) {
                                    Error error = new Error(6,
                                            "Employees are not in the same Unit",
                                            "Assign employees from the same Unit only");
                                    errors.add(error);
                                    genericResponse.setErrors(errors);
                                    return genericResponse;
                                }
                            }
                        }
                        Iterator it = employeeMap.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();
                            if (Integer.parseInt(String.valueOf(pair.getValue())) > 1) {
                                Error error = new Error(6,
                                        "Duplicate employeeId",
                                        "Employee with ID: " + pair.getKey() + " has been assigned twice, " +
                                                "please remove duplicates");
                                errors.add(error);
                                genericResponse.setErrors(errors);
                                return genericResponse;
                            }
                            it.remove();
                        }

                        if (genericResponse.getErrors() == null) {
                            if (!data.equals("null")) {
                                List<String> employeesIdList = Arrays.asList(data.split(","));
                                List<Employee> assignEmployeesList = new ArrayList<>();

                                for (String employeeId : employeesIdList) {
                                    assignEmployeesList.add(employeeRepository.findById(Long.parseLong(employeeId)).get());
                                }
                                tasktoBePatched.getEmployeesList().addAll(assignEmployeesList);
                            }
                        }
                    } else {
                        taskToBePatch.getEmployeesList().forEach(employee -> {
                            for (String s : assignEmployeesIdList) {
                                if (employee.getEmployeeId().toString().equals(s)) {
                                    Error error = new Error(6,
                                            "Duplicate employeeId",
                                            "Employee with ID: " + s + " has been assigned twice, " +
                                                    "please remove duplicates");
                                    errors.add(error);
                                    genericResponse.setErrors(errors);
                                    break;
                                }
                            }
                        });
                        String taskUnit = taskToBePatch.getEmployeesList().get(0).getEmployeeUnitRef().getUnitName();
                        for ( String s : assignEmployeesIdList){

                            Employee EmployeeToBeUpdated = new Employee();
                            try {
                                EmployeeToBeUpdated = employeeRepository.findById(Long.parseLong(s)).get();
                            } catch(NoSuchElementException e){
                                EmployeeToBeUpdated =null;
                            }
                            if(EmployeeToBeUpdated == null){
                                Error error = new Error(3,
                                        "Employee N/A",
                                        "The requested Employee Id does not map with any of the available Employees");
                                errors.add(error);
                                genericResponse.setErrors(errors);
                                return genericResponse;
                            }

                            if (!(taskUnit.equals(employeeRepository.findById(Long.parseLong(s)).get().getEmployeeUnitRef().getUnitName()))){
                                Error error = new Error(6,
                                        "Employees are not in the same Unit",
                                        "Assign employees from the same Unit only");
                                errors.add(error);
                                genericResponse.setErrors(errors);

                            }
                        }
                        if (genericResponse.getErrors() != null)
                        return genericResponse;
                    }
                }
                break;
        }

        if (genericResponse.getErrors() == null) taskRepository.save(tasktoBePatched);
        List<TaskByIdResponse> taskResponse = new ArrayList<TaskByIdResponse>();
        taskResponse.addAll(mapAllTasksById(tasks, id));
        genericResponse.setData(new GetTaskByIdResponse(taskResponse));
        return genericResponse;
    }

    public GenericResponse<GetTaskByIdResponse> validateTask(String title, String desc, String estimationA, String estimationB, String estimationC, String status, String updates, String employees) {
        GenericResponse<GetTaskByIdResponse> genericResponse = new GenericResponse<>();
        List<Error> errors = new ArrayList<>();
        List<String> assignEmployeesList = Arrays.asList(employees.split(","));
        String taskUnitName = new String();


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
        if(!updates.equals("null")) {
            boolean matchesUpdates = Pattern.matches(regexUpdates, updates);
            if (!matchesUpdates) {
                Error error = new Error(4,
                        "Wrong input in updates",
                        "Try to delimit your list only with comma");
                errors.add(error);
                genericResponse.setErrors(errors);
            }
        }

        if(!employees.equals("null")){
            boolean matchesEmployees = Pattern.matches(regexEmployees, employees);
            if (!matchesEmployees) {
                Error error = new Error(4,
                        "Wrong input in Employees",
                        "Assign an Employee with ID and try to delimit your list only with comma");
                errors.add(error);
                genericResponse.setErrors(errors);
            }else {
                boolean flag = true;
                HashMap<String, Integer> employeeMap = new HashMap<>();
                String employeeUnit = new String();
                for (String employeeId: assignEmployeesList) {
                    boolean employeeExists = employeeRepository.findById(Long.parseLong(employeeId)).isPresent();
                    if (!employeeExists) {
                        Error error = new Error(5,
                                "Employee with id " + employeeId + " does not exist",
                                "Assign an existing Employee");
                        errors.add(error);
                        genericResponse.setErrors(errors);
                        return genericResponse;
                    }
                    boolean idExists = String.valueOf(employeeMap.get(employeeId)) != null;
                    if(idExists){
                        employeeMap.put(employeeId,employeeMap.get(employeeId) == null ? 1 : employeeMap.get(employeeId) + 1);
                    }
                    if (flag) {
                        taskUnitName = employeeRepository.findById(Long.parseLong(employeeId)).get().getEmployeeUnitRef().getUnitName();
                        flag = false;

                    } else {
                        employeeUnit = employeeRepository.findById(Long.parseLong(employeeId)).get().getEmployeeUnitRef().getUnitName();
                        if (!(employeeUnit.equalsIgnoreCase(taskUnitName))) {
                            Error error = new Error(6,
                                    "Employees are not in the same Unit",
                                    "Assign employees from the same Unit only");
                            errors.add(error);
                            genericResponse.setErrors(errors);
                            return genericResponse;
                        }
                    }
                }
                Iterator it = employeeMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if(Integer.parseInt(String.valueOf(pair.getValue())) > 1){
                        Error error = new Error(6,
                                "Duplicate employeeId",
                                "Employee with ID: "+pair.getKey()+" has been assigned twice, " +
                                        "please remove duplicates");
                        errors.add(error);
                        genericResponse.setErrors(errors);
                    }
                    it.remove();
                }
            }
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