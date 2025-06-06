package com.the_trueth_league_academy.academy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ques", nullable = false, length = 150)
    private String ques;

    @Column(name = "ans", nullable = false, length = 45)
    private String ans;

    @Column(name = "relation", nullable = true)
    private Integer relation;

    @ManyToOne
    @JoinColumn(name = "questype_id", nullable = false)
    private QuestType questType;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

}
