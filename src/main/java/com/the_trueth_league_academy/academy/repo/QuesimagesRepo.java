package com.the_trueth_league_academy.academy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.the_trueth_league_academy.academy.models.QuesImage;
import com.the_trueth_league_academy.academy.models.Question;

@Repository
public interface QuesimagesRepo extends JpaRepository<QuesImage, Integer> {
    List<QuesImage> findByQuestion(Question question);
}
