package com.the_trueth_league_academy.academy.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {
    private int id;
    private String ques;
    private QuestTypeDTO questType;
    private String ans;
    private Integer relation;
    private List<String> answers;
    private List<String> imageUrls;
    private String audioUrl;

}