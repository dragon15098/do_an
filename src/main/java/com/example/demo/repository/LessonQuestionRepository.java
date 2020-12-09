package com.example.demo.repository;

import com.example.demo.model.Course;
import com.example.demo.model.LessonQuestion;
import com.example.demo.model.Section;
import com.example.demo.model.UserWishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;


@Repository
public interface LessonQuestionRepository extends JpaRepository<LessonQuestion, Long>, JpaSpecificationExecutor<LessonQuestion> {
    @Query(value = " SELECT  s.id as id, " +
                            "s.question as question, " +
                            "s.questionTitle as questionTitle, " +
                            "s.correctAnswerId as correctAnswerId " +
                    " FROM LessonQuestion s" +
                    " WHERE s.lessonId = :lessonId ")
    List<Tuple> findAllByLessonId(@Param("lessonId") Long lessonId);

    @Query(value = " SELECT  s.correctAnswerId as correctAnswerId " +
            " FROM LessonQuestion s" +
            " WHERE s.lessonId = :lessonId ")
    List<Tuple> findCorrectAnswerIdByLessonId(@Param("lessonId") Long lessonId);

}
