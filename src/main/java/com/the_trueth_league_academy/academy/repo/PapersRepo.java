package com.the_trueth_league_academy.academy.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.the_trueth_league_academy.academy.models.Papers;

@Repository
public interface PapersRepo extends JpaRepository<Papers, Integer> {

    Optional<Papers> findByName(String name);

    @Query(value = "SELECT * FROM `papers` WHERE `name` LIKE ?%  ;", nativeQuery = true)
    List<Papers> findByNameLike(@Param("name") String text);
}
