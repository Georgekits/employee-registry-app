package com.unisystems.strategy.taskStrategy;

import com.unisystems.response.TaskByIdResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SearchDifficultyStrategy {
    List<TaskByIdResponse> executeTask (String difficulty, List<TaskByIdResponse> tasks);
}