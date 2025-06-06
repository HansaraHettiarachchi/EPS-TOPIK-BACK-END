package com.the_trueth_league_academy.academy.dto;

import com.the_trueth_league_academy.academy.models.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuesRecodingDTO {
    private int id;
    private String location;
    private Question question;
}
