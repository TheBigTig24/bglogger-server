package com.example.bglogger.dto;

import com.example.bglogger.annotations.ValueOfEnum;
import com.example.bglogger.enumerations.ScoreCategory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDTO {
    
    @NotNull(message = "Game ID is required")
    private Integer gameId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    private String body;

    @NotNull(message = "Score category is required")
    @ValueOfEnum(enumClass = ScoreCategory.class, message = "Score category must be LOW, MEDIUM, or HIGH")
    private String scoreCategory;

    @Min(value = 1, message = "List position must be greater than 0")
    private int listPosition;
}
