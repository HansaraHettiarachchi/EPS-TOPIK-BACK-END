package com.the_trueth_league_academy.academy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.the_trueth_league_academy.academy.models.Gender;

@Repository
public interface GenderRepo extends JpaRepository<Gender, Integer> {

}
