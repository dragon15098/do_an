package com.example.demo.repository;

import com.example.demo.model.UserCart;
import com.example.demo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, Long>, JpaSpecificationExecutor<UserCart> {
    List<UserCart> findAllByUserId(Long id);


    @Query(value = " SELECT ucart.id         as userCartId,     " +
            "       c.id                     as courseId, " +
            "       c.title                  as title, " +
            "       c.description            as description, " +
            "       c.image_description_link as imageDescriptionLink, " +
            "       c.price                  as price, " +
            "       COUNT(uc.course_comment) as courseComment, " +
            "       AVG(uc.course_rating)    as rating, " +
            "       COUNT(uc.course_rating)  as courseRating " +
            " FROM course c " +
            "         JOIN user_cart ucart ON ucart.course_id = c.id " +
            "         JOIN user_course uc on c.id = uc.course_id " +
            " WHERE ucart.user_id = :userId " +
            "  AND c.status = 'APPROVED' " +
            " GROUP BY(c.id) ",
            nativeQuery = true)
    List<Tuple> getUserCartByUserId(@Param("userId") Long userId);
}
