package com.the_trueth_league_academy.academy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.the_trueth_league_academy.academy.models.PapersHasQuestions;

@Repository
public interface PapersHasQuesRepo extends JpaRepository<PapersHasQuestions, Integer> {
    List<PapersHasQuestions> findAllByPapersId(int pId);
}
