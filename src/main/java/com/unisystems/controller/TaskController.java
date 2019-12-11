package com.unisystems.controller;

import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllTaskResponse;
import com.unisystems.response.getAllResponse.GetTaskByIdResponse;
import com.unisystems.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task/")
public class TaskController {

    @Autowired
    TaskService taskService;

    public TaskController(TaskService mockService) {
        this.taskService = mockService;
    }

    @GetMapping("getAll")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity getAll() {
        GenericResponse<GetAllTaskResponse> finalResponse = taskService.getAllTasks();
        if (finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    @GetMapping("findById/{taskId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity findById(@PathVariable String taskId) {
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.findById(taskId);
        if (finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    //List with tasks that have this difficulty!!
    @GetMapping("findByDifficulty/{difficulty}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity findByDifficulty(@PathVariable String difficulty) {
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.findByDifficulty(difficulty.toUpperCase());
        if (finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    //List with tasks that have been used by a specific workforce(assignedEmployees)
    @GetMapping("findByAssignedEmployees/{assignedEmployees}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity findByAssignedEmployees(@PathVariable String assignedEmployees) {
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.findByAssignedEmployees(assignedEmployees);
        if (finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    //List with tasks that have been used by a specific workforce(assignedEmployees) AND difficulty
    @GetMapping("findByAssignedEmployeesAndDifficulty/{difficulty}/{assignedEmployees}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity findByAssignedEmployeesAndDifficulty(@PathVariable String assignedEmployees,
                                                               @PathVariable String difficulty) {
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.findByAssignedEmployeesAndDifficulty(assignedEmployees, difficulty.toUpperCase());
        if (finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    @PostMapping("postTask")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity setTask(@RequestHeader String title, @RequestHeader String desc, @RequestHeader String estimationA,
                                  @RequestHeader String estimationB, @RequestHeader String estimationC, @RequestHeader String status,
                                  @RequestHeader String updates ,@RequestHeader String employees) {
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.addTask(title, desc, estimationA, estimationB, estimationC, status, updates, employees);
        if (finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.CREATED
        );
    }

    @PutMapping("putTask/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity putTask(@PathVariable String taskId, @RequestHeader String title, @RequestHeader String desc, @RequestHeader String estimationA,
                                  @RequestHeader String estimationB, @RequestHeader String estimationC, @RequestHeader String status,
                                  @RequestHeader String updates, @RequestHeader String employees) {
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.updateTask(taskId, title, desc, estimationA, estimationB,
                estimationC, status, updates,employees);

        if (finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    @PatchMapping("patchTask/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity patchTask(@PathVariable String id, @RequestHeader String columnName, @RequestHeader String data) {
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.patchTask(id, columnName, data);
        if (finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK
        );
    }

    @DeleteMapping("deleteAllTasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteAllTasks() {
        GenericResponse<String> finalResponse = taskService.deleteAllTasks();
        if(finalResponse.getErrors() == null) {
            return new ResponseEntity("Every task has been deleted",
                    null,
                    HttpStatus.ACCEPTED
            );
        }
        return new ResponseEntity(finalResponse.getErrors(),
                null,
                HttpStatus.NOT_ACCEPTABLE
        );

    }

    @DeleteMapping("/deleteTask/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteTaskById(@PathVariable String taskId) {
        GenericResponse<String> finalResponse = taskService.deleteTaskById(taskId);
        if(finalResponse.getErrors() == null) {
            return new ResponseEntity("Task with id: "+taskId+" has been deleted!",
                    null,
                    HttpStatus.ACCEPTED
            );
        }
        return new ResponseEntity(finalResponse.getErrors(),
                null,
                HttpStatus.NOT_ACCEPTABLE
        );
    }
}