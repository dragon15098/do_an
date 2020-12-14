package com.example.demo.repository;

import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.model.dto.CourseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
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
            "c.price as price, " +
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
            "c.categoryId as categoryId, " +
            "c.createTime as createTime, " +
            "c.status as status, " +
            "c.description as description, " +
            "c.price as price, " +
            "c.commentCount as commentCount" +
            " FROM Course c" +
            " WHERE c.id = :id ")
    List<Tuple> getCourseDetail(@Param("id") Long id);


    @Query(value = " SELECT  c.id as id, " +
            "c.title as title, " +
            "c.imageDescriptionLink as imageDescriptionLink, " +
            "c.rating as rating, " +
            "c.ratingCount as ratingCount, " +
            "c.instructorId as instructorId, " +
            "c.categoryId as categoryId, " +
            "c.createTime as createTime, " +
            "c.status as status, " +
            "c.description as description, " +
            "c.price as price, " +
            "c.commentCount as commentCount" +
            " FROM Course c" +
            " WHERE c.title LIKE :title ")
    List<Tuple> getCourseByTitle(@Param("title") String title);


    @Query(value = " SELECT  c.id as id, " +
            "c.imageDescriptionLink as imageDescriptionLink " +
            " FROM Course c JOIN Section s ON c.id = s.courseId " +
            " WHERE s.id = :sectionId ")
    List<Tuple> getCourseBySectionId(@Param("sectionId") Long sectionId);

    @Transactional
    @Modifying
    @Query(value = " UPDATE Course SET imageDescriptionLink = :link WHERE id = :id")
    void updateImageDescriptionLink(@Param("link") String link, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = " UPDATE Course SET status = :approved WHERE id = :id")
    void approveCourse(Long id, Course.CourseStatus approved);
}
