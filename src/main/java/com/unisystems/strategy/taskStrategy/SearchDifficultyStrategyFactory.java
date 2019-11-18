package com.unisystems.strategy.taskStrategy;

import org.springframework.stereotype.Component;

@Component
public class SearchDifficultyStrategyFactory {
    public SearchDifficultyStrategy makeStrategyForDifficulty(String difficulty){
        switch (difficulty){
            case "EASY":
                return new SearchEasyStrategy();
            case "MEDIUM":
                return new SearchMediumStrategy();
            case "HARD":
                return new SearchHardStrategy();
            default:
                return null;
        }
    }
}