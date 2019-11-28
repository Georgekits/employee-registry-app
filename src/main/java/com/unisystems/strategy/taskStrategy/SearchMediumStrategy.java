package com.unisystems.strategy.taskStrategy;

import com.unisystems.response.TaskByIdResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchMediumStrategy implements SearchDifficultyStrategy {
    @Override
    public List<TaskByIdResponse> executeTask(String difficulty, List<TaskByIdResponse> tasks) {
        List<TaskByIdResponse> taskResponses = new ArrayList<>();
        for(TaskByIdResponse task: tasks){
            if (String.valueOf(task.getDifficulty()).equals(difficulty)) {
                taskResponses.add(task);
            }
        }
        return taskResponses;
    }
}