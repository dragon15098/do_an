package com.example.demo.repository;

import com.example.demo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>, JpaSpecificationExecutor<Lesson> {
    @Query(value = " SELECT  l.id as id, " +
            "l.lessonTitle as lessonTitle, " +
            "l.urlVideo as urlVideo, " +
            "l.description as description, " +
            "l.sectionId as sectionId " +
            " FROM Lesson l" +
            " WHERE l.sectionId = :sectionId ")
    List<Tuple> findAllLessonBySectionId(@Param("sectionId") Long sectionId);

    @Query(value = " SELECT  l.id as id, " +
            "l.lessonTitle as lessonTitle, " +
            "l.urlVideo as urlVideo, " +
            "l.description as description, " +
            "l.sectionId as sectionId " +
            " FROM Lesson l" +
            " WHERE l.id = :lessonId ")
    List<Tuple> findLessonById(@Param("lessonId") Long lessonId);

    @Query(value= "SELECT MIN(id) as id  " +
            " FROM Lesson " +
            " WHERE sectionId = " +
            " (SELECT MIN(id) " +
            " FROM Section " +
            " WHERE courseId = :courseId)")
    List<Tuple> findFirstLessonByCourseId(@Param("courseId") Long courseId);
}
