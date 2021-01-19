package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    @Query(value = " SELECT  c.id as id, " +
            "c.title as title, " +
            "c.imageDescriptionLink as imageDescriptionLink, " +
            "c.instructorId as instructorId, " +
            "c.createTime as createTime, " +
            "c.status as status, " +
            "c.description as description, " +
            "c.price as price " +
            " FROM Course c" +
            " WHERE c.instructorId = :instructorId ")
    List<Tuple> getAllByInstructorId(@Param("instructorId") Long instructorId);


    @Query(value = " SELECT c.id             as id, " +
            "       c.title                  as title, " +
            "       c.description            as description, " +
            "       c.image_description_link as imageDescriptionLink, " +
            "       c.price                  as price, " +
            "       c.create_time            as createTime, " +
            "       c.category_id            as categoryId, " +
            "       c.instructor_id          as instructorId, " +
            "       c.status                 as status " +
            "FROM course c " +
            "WHERE c.id = :id ", nativeQuery = true)
    List<Tuple> getCourseDetail(@Param("id") Long id);


    @Query(value = " SELECT  c.id as id, " +
            "c.title as title, " +
            "c.imageDescriptionLink as imageDescriptionLink, " +
            "c.instructorId as instructorId, " +
            "c.categoryId as categoryId, " +
            "c.createTime as createTime, " +
            "c.status as status, " +
            "c.description as description, " +
            "c.price as price " +
            " FROM Course c" +
            " WHERE c.title LIKE :title ")
    List<Tuple> getCourseByTitle(@Param("title") String title);


    @Query(value = " SELECT c.id                                                  as id, " +
            "       c.title                                                       as title, " +
            "       c.description                                                 as description, " +
            "       c.image_description_link                                      as imageDescriptionLink, " +
            "       c.price                                                       as price, " +
            "       c.create_time                                                 as createTime,  " +
            "       COUNT(uc.id)                                                  as numberSells, " +
            "       COUNT(uc.course_comment)                                      as courseComment, " +
            "       IF(AVG(course_rating) IS NULL, 5, AVG(course_rating))         as rating, " +
            "       COUNT(uc.course_rating)                                       as courseRating " +
            "FROM course c " +
            "         LEFT JOIN user_course uc ON uc.course_id = c.id " +
            "WHERE c.status = 'APPROVED' " +
            "GROUP BY(c.id) ",
            countQuery = " SELECT COUNT(*)  " +
                    "FROM (SELECT c.id                                                    as id, " +
                    "       c.title                                                       as title, " +
                    "       c.description                                                 as description, " +
                    "       c.image_description_link                                      as imageDescriptionLink, " +
                    "       c.price                                                       as price, " +
                    "       c.create_time                                                 as createTime,  " +
                    "       COUNT(uc.id)                                                  as numberSells, " +
                    "       COUNT(uc.course_comment)                                      as courseComment, " +
                    "       IF(AVG(course_rating) IS NULL, 5, AVG(course_rating))         as rating, " +
                    "       COUNT(uc.course_rating)                                       as courseRating " +
                    "FROM course c " +
                    "         LEFT JOIN user_course uc ON uc.course_id = c.id " +
                    "WHERE c.status = 'APPROVED' " +
                    "GROUP BY(c.id)) ",
            nativeQuery = true)
    List<Tuple> getCourseFilter(Pageable pageable);

    @Query(value = " SELECT c.id  as id,  " +
            "       c.title       as title,  " +
            "       c.description as description,  " +
            "       c.image_description_link as imageDescriptionLink,  " +
            "       c.price       as price,  " +
            "       c.create_time as createTime,  " +
            "       c.status      as status " +
            " FROM course c LEFT JOIN user_course  uc  " +
            "              ON uc.course_id = c.id  " +
            "WHERE  c.status='APPROVED' " +
            " AND c.category_id IN :categoryId AND c.title LIKE :courseTitle ",
            nativeQuery = true)
    List<Tuple> getCourseByFilter(@Param("categoryId") List<Long> categoryId,
                                  @Param("courseTitle") String courseTitle);


    @Query(value = " SELECT c.id             as id, " +
            "       c.title                  as title, " +
            "       c.description            as description, " +
            "       c.image_description_link as imageDescriptionLink, " +
            "       c.price                  as price, " +
            "       c.create_time            as createTime, " +
            "       u.first_name             as firstName, " +
            "       u.last_name              as lastName, " +
            "       c.status                 as status " +
            " FROM course c " +
            "         JOIN users u ON c.instructor_id = u.id " +
            "         JOIN instructor_detail i on u.instructor_detail_id = i.id " +
            " WHERE c.status LIKE :status " +
            "  AND c.category_id IN :categoryId " +
            "  AND c.title LIKE :courseTitle " +
            "  AND CONCAT(u.first_name, u.last_name) LIKE :instructorName " +
            "  AND c.create_time >= :createFrom " +
            "  AND c.create_time <= :createTo " +
            " GROUP BY(c.id); ",
            nativeQuery = true)
    List<Tuple> search(@Param("categoryId") List<Long> categoryId,
                       @Param("courseTitle") String courseTitle,
                       @Param("instructorName") String instructorName,
                       @Param("createFrom") Date fromDate,
                       @Param("createTo") Date toDate,
                       @Param("status") String status);

    @Query(value = "  SELECT *  " +
            "FROM (SELECT COUNT(DISTINCT uc.id)                                 as reviewCount,  " +
            "             IF(AVG(course_rating) IS NULL, 5, AVG(course_rating)) as rating  " +
            "      FROM course c  " +
            "               JOIN user_course uc on c.id = uc.course_id  " +
            "      WHERE c.id = :id  " +
            "        AND uc.course_comment IS NOT NULL  " +
            "        AND uc.payment_status = 'COMPLETE') a  " +
            "         JOIN  " +
            "     (SELECT COUNT(DISTINCT uc.user_id) as totalSell  " +
            "      FROM course c  " +
            "               JOIN user_course uc on c.id = uc.course_id  " +
            "      WHERE c.id = :id  " +
            "        AND uc.payment_status = 'COMPLETE') b ",
            nativeQuery = true)
    List<Tuple> getCourseSellDetail(@Param("id") Long courseId);

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
    @Query(value = " UPDATE Course SET status = :approved, price = :price WHERE id = :id")
    void approveCourse(Long id, Float price, Course.CourseStatus approved);
}
