package com.example.demo.repository;

import com.example.demo.model.UserWishList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface UserWishListRepository extends JpaRepository<UserWishList, Long>, JpaSpecificationExecutor<UserWishList> {

    @Query(value = " SELECT c.id             as id, " +
            "       c.title                  as title, " +
            "       c.description            as description, " +
            "       c.image_description_link as imageDescriptionLink, " +
            "       c.price                  as price, " +
            "       COUNT(uc.course_comment) as courseComment, " +
            "       AVG(uc.course_rating)    as rating, " +
            "       COUNT(uc.course_rating)  as courseRating " +
            " FROM course c " +
            "         JOIN user_course uc ON uc.course_id = c.id " +
            "         JOIN user_wish_list uwl on c.id = uwl.course_id " +
            " WHERE uc.payment_status = 'COMPLETE' " +
            "  AND c.status = 'APPROVED' AND uwl.user_id = :userId " +
            " GROUP BY(c.id) ", nativeQuery = true)
    List<Tuple> getUserWishListCourse(@Param("userId") Long userId);
}
