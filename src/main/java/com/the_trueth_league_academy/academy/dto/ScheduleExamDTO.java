package com.the_trueth_league_academy.academy.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleExamDTO {
    
    private int id;

    @NotBlank(message = "Exam ID cannot be blank.")
    @Size(max = 20, message = "Exam ID must not exceed 20 characters.")
    private String exId;

    @NotBlank(message = "Exam name cannot be blank.")
    @Size(max = 45, message = "Exam name must not exceed 45 characters.")
    private String exName;

    @NotNull(message = "Start date cannot be null.")
    @FutureOrPresent(message = "Start date must be in the present or future.")
    @JsonProperty("dFrom")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null.")
    @Future(message = "End date must be in the future.")
    @JsonProperty("dTo")
    private LocalDateTime endDate;

    @Positive(message = "Paper ID must be a positive integer.")
    @JsonProperty("paperId")
    private int papersId;

    @PositiveOrZero(message = "Status ID must be a positive integer or zero.")
    private int statusId;

    @NotEmpty(message = "Students list cannot be empty.")
    private List<@Valid UsersDTO> students;
}
