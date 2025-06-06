package com.the_trueth_league_academy.academy.dto;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperHasQDTO {

    @JsonProperty("qdifficultyId")
    private int qDifficultyId;
    private QuestionDTO question;
    private int papersId;
    private Time time;
}