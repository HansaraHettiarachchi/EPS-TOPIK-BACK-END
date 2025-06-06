package com.the_trueth_league_academy.academy.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.the_trueth_league_academy.academy.models.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT * FROM questions WHERE ques = ?1 AND questype_id = ?2;", nativeQuery = true)
    Question findByQues(String ques, int id);

    Optional<Question> findById(int id);

    @Query("SELECT q FROM Question q WHERE q.ques LIKE :ques% AND q.questType.id = :questTypeId")
    List<Question> findByQuesLike(@Param("ques") String ques, @Param("questTypeId") int questTypeId);

    @Query("SELECT q FROM Question q WHERE str(q.id) LIKE :ques% AND q.questType.id = :questTypeId")
    List<Question> findByIdLike(@Param("ques") String ques, @Param("questTypeId") int questTypeId);

    @Query("SELECT q FROM Question q WHERE q.ques LIKE :ques% ")
    List<Question> findByQuesLike(@Param("ques") String ques);

    @Query("SELECT q FROM Question q WHERE str(q.id) LIKE :ques%")
    List<Question> findByIdLike(@Param("ques") String quesquestTypeId);

}
