package com.example.demo.repository;

import com.example.demo.model.InstructorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface InstructorDetailRepository extends JpaRepository<InstructorDetail, Long>, JpaSpecificationExecutor<InstructorDetail> {

    @Query(value = " SELECT i.id                 as id, " +
            "       i.about_me                   as aboutMe, " +
            "       i.achievement                as achievement, " +
            "       i.facebook_link              as facebookLink, " +
            "       i.twitter_link               as twitterLink, " +
            "       i.youtube_link               as youtubeLink, " +
            "       COUNT(uc.instructor_comment) as reviewCount, " +
            "       AVG (uc.instructor_rating)   as instructorRating, " +
            "       COUNT(c.id)                  as numberCourses, " +
            "       tmp.totalStudents            as totalStudents " +
            "FROM (SELECT COUNT(*) as totalStudents FROM user_course uc where uc.payment_status = 'COMPLETE') AS tmp " +
            "         JOIN user_course uc " +
            "         JOIN course c on uc.course_id = c.id " +
            "         JOIN users u on c.instructor_id = u.id " +
            "         JOIN instructor_detail i on u.instructor_detail_id = i.id " +
            "WHERE uc.course_comment IS NOT NULL " +
            "  AND i.id = ?1", nativeQuery = true)
    List<Tuple> getDetailById(@Param("id") Long id);

    @Query(value = " SELECT DISTINCT (i.id)            as id, " +
            "       i.about_me      as aboutMe, " +
            "       i.achievement   as achievement, " +
            "       i.facebook_link as facebookLink, " +
            "       i.twitter_link  as twitterLink, " +
            "       i.youtube_link  as youtubeLink " +
            "FROM instructor_detail i " +
            "         JOIN users u on i.id = u.instructor_detail_id " +
            "         LEFT JOIN course c on c.instructor_id = u.id " +
            "WHERE i.id = :id", nativeQuery = true)
    List<Tuple> getDetail(@Param("id") Long id);


    @Query(value = "SELECT * " +
            "FROM (SELECT COUNT(DISTINCT uc.user_id) as totalStudents, " +
            "            COUNT(DISTINCT c.id) as numberCourses " +
            "      FROM instructor_detail id " +
            "               JOIN users u on id.id = u.instructor_detail_id " +
            "               JOIN course c ON c.instructor_id = u.id " +
            "               JOIN user_course uc on c.id = uc.course_id " +
            "      WHERE id.id = :id " +
            "        AND uc.payment_status = 'COMPLETE') a " +
            "         JOIN (SELECT COUNT(DISTINCT uc.user_id) as reviewCount, " +
            "                      IF(AVG(instructor_rating) IS NULL, 5, AVG(instructor_rating)) as instructorRating " +
            "               FROM instructor_detail id " +
            "                        JOIN users u on id.id = u.instructor_detail_id " +
            "                        JOIN course c ON c.instructor_id = u.id " +
            "                        JOIN user_course uc on c.id = uc.course_id " +
            "               WHERE id.id = :id " +
            "                 AND uc.course_comment IS NOT NULL " +
            "                 AND uc.payment_status = 'COMPLETE') b", nativeQuery = true)
    List<Tuple> getCourseDetail(@Param("id") Long id);


}
