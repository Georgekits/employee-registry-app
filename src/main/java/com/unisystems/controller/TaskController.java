package com.unisystems.controller;

import com.unisystems.model.Task;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllTaskResponse;
import com.unisystems.response.getAllResponse.GetTaskByIdResponse;
import com.unisystems.service.TaskService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task/")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("getAll")
    public ResponseEntity getAll(){
        GenericResponse<GetAllTaskResponse> finalResponse = taskService.getAllTasks();
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    @GetMapping("findById/{taskId}")
    public ResponseEntity findById(@PathVariable String taskId){
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.findById(taskId);
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    //List with tasks that have this difficulty!!
    @GetMapping("findByDifficulty/{difficulty}")
    public ResponseEntity findByDifficulty(@PathVariable String difficulty){
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.findByDifficulty(difficulty.toUpperCase());
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    //List with tasks that have been used by a specific workforce(assignedEmployees)
    @GetMapping("findByAssignedEmployees/{assignedEmployees}")
    public ResponseEntity findByAssignedEmployees(@PathVariable String assignedEmployees){
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.findByAssignedEmployees(assignedEmployees);
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    //List with tasks that have been used by a specific workforce(assignedEmployees) AND difficulty
    @GetMapping("findByAssignedEmployeesAndDifficulty/{difficulty}/{assignedEmployees}")
    public ResponseEntity findByAssignedEmployeesAndDifficulty(@PathVariable String assignedEmployees,
                                                               @PathVariable String difficulty){
        GenericResponse<GetTaskByIdResponse> finalResponse = taskService.findByAssignedEmployeesAndDifficulty(assignedEmployees, difficulty.toUpperCase());
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    @PostMapping("postTask")
    public ResponseEntity setTask(@RequestHeader String title, @RequestHeader String desc, @RequestHeader String estimationA,
                                  @RequestHeader String estimationB, @RequestHeader String estimationC, @RequestHeader String status){
        GenericResponse<GetAllTaskResponse> finalResponse = taskService.addTask(title,desc,estimationA,estimationB,estimationC,status);
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK
                );

    }
}