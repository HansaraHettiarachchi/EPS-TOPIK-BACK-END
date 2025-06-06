package com.the_trueth_league_academy.academy.models;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "papers_has_questions")
public class PapersHasQuestions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "papers_id", nullable = false)
    private Papers papers;

    @ManyToOne
    @JoinColumn(name = "questions_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "Qdifficulty_id", nullable = false)
    private QDifficulty qDifficulty;

    @Column(name = "time", nullable = true)
    private Time time;

}
