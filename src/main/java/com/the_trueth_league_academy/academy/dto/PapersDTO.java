package com.the_trueth_league_academy.academy.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PapersDTO {

    private int id;

    @JsonProperty("paperName")
    private String name;

    private LocalDateTime cDAT;

}
