package com.the_trueth_league_academy.academy.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDTO {
    private int id;
    private String question;
    private String ans;
    private Integer relation;
    private int questionType;
    private List<String> answers;
    private List<MultipartFile> images;
    private MultipartFile audio;

}