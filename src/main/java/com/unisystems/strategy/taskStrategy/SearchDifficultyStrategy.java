package com.unisystems.strategy.taskStrategy;

import com.unisystems.model.Task;
import com.unisystems.response.TaskByIdResponse;
import com.unisystems.response.TaskResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SearchDifficultyStrategy {
    List<TaskByIdResponse> executeTask (String difficulty, List<TaskByIdResponse> tasks);
}