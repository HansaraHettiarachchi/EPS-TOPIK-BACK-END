package com.the_trueth_league_academy.academy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.the_trueth_league_academy.academy.models.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {
    List<Users> findByEmail(String email);

    List<Users> findByNic(String nic);

    List<Users> findByClassesId(int id);

    Users findByIndex(int i);

}
