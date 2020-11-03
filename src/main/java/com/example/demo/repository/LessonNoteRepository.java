package com.example.demo.repository;


import com.example.demo.model.LessonNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LessonNoteRepository extends JpaRepository<LessonNote, Long>, JpaSpecificationExecutor<LessonNote> {

    @Query(value = " SELECT  ln.id as id, " +
                            "ln.note as note " +
                    " FROM LessonNote ln " +
                    " WHERE ln.lessonId = :lessonId " +
                    " AND ln.userId = :userId")
    List<Tuple> getLessonNoteByUserIdAndLessonId(@Param("userId") Long userId, @Param("lessonId") Long lessonId);
}
