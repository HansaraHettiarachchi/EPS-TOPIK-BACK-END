package com.the_trueth_league_academy.academy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private int id;
    private String ques;
    private String ans;
    private Integer relation;
    private int questTypeId;
    private int statusId;
}
