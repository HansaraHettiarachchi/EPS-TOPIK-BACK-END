package com.the_trueth_league_academy.academy.repo;

import com.the_trueth_league_academy.academy.models.UsersHasExams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserHasExamsRepo extends JpaRepository<UsersHasExams, Integer> {

    @Query("SELECT uhe FROM UsersHasExams uhe WHERE uhe.exams.startDate > :today")
    List<UsersHasExams> findAllWithStartDateAfter(@Param("today") LocalDateTime today);

    @Query("SELECT uhe FROM UsersHasExams uhe WHERE uhe.exams.startDate > :today AND uhe.users.id = :id")
    List<UsersHasExams> findAllWithStartDateAfterAndUsersId(
            @Param("today") LocalDateTime today,
            @Param("id") int id);

}