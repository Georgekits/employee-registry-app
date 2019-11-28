package com.unisystems.response.getAllResponse;

import com.unisystems.response.TaskResponse;

import java.util.List;

public class GetAllTaskResponse {
    private List<TaskResponse> taskResponses;

    public GetAllTaskResponse(List<TaskResponse> taskResponses) {
        this.taskResponses = taskResponses;
    }

    public List<TaskResponse> getTaskResponses() {
        return taskResponses;
    }

    public void setTaskResponses(List<TaskResponse> taskResponses) {
        this.taskResponses = taskResponses;
    }
}
