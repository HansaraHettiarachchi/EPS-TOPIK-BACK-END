package com.the_trueth_league_academy.academy.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDataByIdDTO {

    private UsersDTO usersDTO;
    private List<UHE> exData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UHE {

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

    }
}
