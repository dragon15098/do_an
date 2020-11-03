package com.example.demo.repository;

import com.example.demo.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long>, JpaSpecificationExecutor<Section> {
    @Query(value = " SELECT  s.id as id, " +
            "s.sectionTitle as sectionTitle, " +
            "s.quizId as quizId, " +
            "s.courseId as courseId, " +
            "q.quizTitle as quizTitle " +
            " FROM Section s " +
            " LEFT JOIN Quiz q ON q.id = s.quizId " +
            " WHERE s.courseId = :courseId ")
    List<Tuple> getAllSectionByCourseId(Long courseId);
}
