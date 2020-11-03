package com.example.demo.repository;

import com.example.demo.model.LessonAnswer;
import com.example.demo.model.LessonQuestion;
import com.example.demo.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LessonAnswerRepository  extends JpaRepository<LessonAnswer, Long>, JpaSpecificationExecutor<LessonAnswer> {
    @Query(value = " SELECT  ls.id as id, " +
                            "ls.explanation as explanation, " +
                            "ls.content as content, " +
                            "ls.lessonQuestionId as lessonQuestionId" +
                    " FROM LessonAnswer ls" +
                    " WHERE ls.lessonQuestionId = :lessonQuestionId ")
    List<Tuple> findAllByLessonQuestionId(@Param("lessonQuestionId") Long lessonQuestionId);

}
