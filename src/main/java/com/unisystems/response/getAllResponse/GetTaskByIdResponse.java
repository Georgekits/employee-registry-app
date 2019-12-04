package com.unisystems.response.getAllResponse;

import com.unisystems.response.TaskByIdResponse;

import java.util.List;

public class GetTaskByIdResponse {
    private List<TaskByIdResponse> taskResponses;

    public GetTaskByIdResponse(List<TaskByIdResponse> taskResponses) {
        this.taskResponses = taskResponses;
    }

    public List<TaskByIdResponse> getTaskResponses() {
        return taskResponses;
    }

    public void setTaskResponses(List<TaskByIdResponse> taskResponses) {
        this.taskResponses = taskResponses;
    }
}
