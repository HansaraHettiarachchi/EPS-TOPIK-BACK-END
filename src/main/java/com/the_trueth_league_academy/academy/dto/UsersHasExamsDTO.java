package com.the_trueth_league_academy.academy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersHasExamsDTO {
    private int id;
    private int examsId;

    @JsonProperty("id")
    private int usersId;

}
