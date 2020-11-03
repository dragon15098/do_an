package com.example.demo.repository;

import com.example.demo.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long>, JpaSpecificationExecutor<QuizQuestion> {
    @Query(value = " SELECT  qq.id as id, " +
            " qq.question as question, " +
            " qq.questionTitle as questionTitle, " +
            " qq.correctAnswerId as correctAnswerId " +
            " FROM QuizQuestion q" +
            "q WHERE qq.quizId = :quizId ")
    List<Tuple> getAllQuizQuestionByQuizId(@Param("quizId") Long quizId);
}
