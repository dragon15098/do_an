package com.example.demo.repository;

import com.example.demo.model.InstructorDetail;
import com.example.demo.model.LessonNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface InstructorDetailRepository extends JpaRepository<InstructorDetail, Long>, JpaSpecificationExecutor<InstructorDetail> {

    @Query(value = " SELECT  instructor.id as id, " +
            "instructor.aboutMe as aboutMe, " +
            "instructor.totalStudents as totalStudents, " +
            "instructor.reviewCount as reviewCount, " +
            "instructor.imageLink as imageLink, " +
            "instructor.facebookLink as facebookLink, " +
            "instructor.twitterLink as twitterLink, " +
            "instructor.numberCourses as numberCourses, " +
            "instructor.ratings as ratings, " +
            "instructor.achievement as achievement, " +
            "instructor.youtubeLink as youtubeLink " +
            " FROM InstructorDetail instructor" +
            " WHERE instructor.id = :id ")
    List<Tuple> getDetailById(@Param("id") Long id);
}
