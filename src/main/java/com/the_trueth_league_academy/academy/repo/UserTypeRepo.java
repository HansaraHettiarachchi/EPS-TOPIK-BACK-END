package com.the_trueth_league_academy.academy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.the_trueth_league_academy.academy.models.UserType;

@Repository
public interface UserTypeRepo extends JpaRepository<UserType, Integer> {

}
