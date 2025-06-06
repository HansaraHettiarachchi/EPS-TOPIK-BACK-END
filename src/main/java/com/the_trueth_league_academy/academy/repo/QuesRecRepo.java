package com.the_trueth_league_academy.academy.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.the_trueth_league_academy.academy.models.QuesRecoding;
import com.the_trueth_league_academy.academy.models.Question;

@Repository
public interface QuesRecRepo extends JpaRepository<QuesRecoding, Integer> {
    Optional<QuesRecoding> findByQuestion(Question question);
}
