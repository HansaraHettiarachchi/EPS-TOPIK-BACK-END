package com.the_trueth_league_academy.academy.dto;

import com.the_trueth_league_academy.academy.models.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McqAnsDTO {
    private int id;
    private String ans;
    private Question question;
}
