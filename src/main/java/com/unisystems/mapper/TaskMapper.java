package com.unisystems.mapper;

import com.unisystems.enums.TaskDifficultyEnum;
import com.unisystems.model.Task;
import com.unisystems.response.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

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

    private TaskDifficultyEnum getDifficulty(Task task) {
        int avgDifficulty = ((task.getEstimationA()+task.getEstimationB()+task.getEstimationC())/3);

        if (avgDifficulty < 2) return TaskDifficultyEnum.EASY;
        else if (avgDifficulty <= 4) return TaskDifficultyEnum.MEDIUM;
        else return TaskDifficultyEnum.HARD;
    }
}