package com.unisystems.response;

import com.unisystems.enums.TaskDifficultyEnum;
import com.unisystems.enums.TaskStatusEnum;

import java.util.List;

public class TaskByIdResponse {
    private long taskId;
    private String taskTitle;
    private String taskDesc;
    private TaskDifficultyEnum difficulty;
    private TaskStatusEnum taskStatus;
    private List<String> assignedEmployees;
    private List<String> updates;

    public TaskByIdResponse(long taskId, String taskTitle, String taskDesc,
                TaskDifficultyEnum difficulty, TaskStatusEnum taskStatus, List<String> assignedEmployees, List<String> updates) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDesc = taskDesc;
        this.difficulty = difficulty;
        this.taskStatus = taskStatus;
        this.assignedEmployees = assignedEmployees;
        this.updates = updates;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public TaskDifficultyEnum getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(TaskDifficultyEnum difficulty) {
        this.difficulty = difficulty;
    }

    public TaskStatusEnum getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatusEnum taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<String> getAssignedEmployees() {
        return assignedEmployees;
    }

    public void setAssignedEmployees(List<String> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    public List<String> getUpdates() {
        return updates;
    }

    public void setUpdates(List<String> updates) {
        this.updates = updates;
    }
}
