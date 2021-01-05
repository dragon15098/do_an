package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    @Query(value = " SELECT  user.id as id, " +
                            "user.username as username, " +
                            "user.firstName as firstName, " +
                            "user.lastName as lastName, " +
                            "user.email as email, " +
                            "user.phoneNumber as phoneNumber, " +
                            "user.imageUrl as imageUrl, " +
                            "user.instructorDetailId as instructorDetailId, " +
                            "user.studentDetailId as studentDetailId " +
                    " FROM User user" +
                    " WHERE user.id = :userId ")
    List<Tuple> getUserDetail(@Param("userId") Long userId);

    @Query(value = " SELECT  user.id as id, " +
                        "user.username as username, " +
                        "user.firstName as firstName, " +
                        "user.lastName as lastName, " +
                        "user.email as email, " +
                        "user.imageUrl as imageUrl, " +
                        "user.phoneNumber as phoneNumber, " +
                        "user.instructorDetailId as instructorDetailId, " +
                        "user.studentDetailId as studentDetailId " +
                    " FROM User user" +
                    " WHERE user.instructorDetailId is not null")
    List<Tuple> getAllIntruder();

}
