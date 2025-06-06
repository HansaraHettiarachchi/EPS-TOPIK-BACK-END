package com.the_trueth_league_academy.academy.dto;

import java.sql.Time;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPDTO {
    private int id;
    private String ques;
    private String ans;
    private int questTypeId;
    private int statusId;
    private int qDifficultyId;
    private List<Ques> quesImage;
    private List<Ans> answers;
    private Ques quesRecoding;
    private Time time;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Ques {
        private String location;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Ans {
        private String ans;
    }

}
