package com.the_trueth_league_academy.academy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.the_trueth_league_academy.academy.models.McqAns;
import com.the_trueth_league_academy.academy.models.Question;

@Repository
public interface MCQAnsRepo extends JpaRepository<McqAns, Integer> {
 List<McqAns> findByQuestion(Question question);
}
