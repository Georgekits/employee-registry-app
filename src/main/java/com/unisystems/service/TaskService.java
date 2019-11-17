package com.unisystems.service;

import com.unisystems.mapper.TaskMapper;
import com.unisystems.model.Task;
import com.unisystems.repository.TaskRepository;
import com.unisystems.response.TaskResponse;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllTaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

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
}
