package com.the_trueth_league_academy.academy.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperQuestionsDTO {
    private String paperName;
    private List<PaperHasQDTO> data;
    
}