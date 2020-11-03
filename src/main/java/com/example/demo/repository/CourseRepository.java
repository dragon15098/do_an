package com.example.demo.repository;

import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.model.dto.CourseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    @Query(value = " SELECT  c.id as id, " +
                    "c.title as title, " +
                    "c.imageDescriptionLink as imageDescriptionLink, " +
                    "c.rating as rating, " +
                    "c.ratingCount as ratingCount, " +
                    "c.instructorId as instructorId, " +
                    "c.createTime as createTime, " +
                    "c.status as status, " +
                    "c.description as description, " +
                    "c.commentCount as commentCount" +
                    " FROM Course c" +
                     " WHERE c.instructorId = :instructorId ")
    List<Tuple> getAllByInstructorId(@Param("instructorId") Long instructorId);


    @Query(value = " SELECT  c.id as id, " +
                            "c.title as title, " +
                            "c.imageDescriptionLink as imageDescriptionLink, " +
                            "c.rating as rating, " +
                            "c.ratingCount as ratingCount, " +
                            "c.instructorId as instructorId, " +
                            "c.createTime as createTime, " +
                            "c.status as status, " +
                            "c.description as description, " +
                            "c.commentCount as commentCount" +
                    " FROM Course c" +
                    " WHERE c.id = :id ")
    List<Tuple> getCourseDetail(@Param("id") Long id);



}
