package com.example.demo.repository;

import com.example.demo.model.StudentDetail;
import com.example.demo.model.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface StudentDetailRepository extends JpaRepository<StudentDetail, Long>, JpaSpecificationExecutor<StudentDetail> {
    @Query(value = " SELECT  student.id as id, " +
                            "student.school as school, " +
                            "student.grade as grade " +
                    " FROM StudentDetail student" +
                    " WHERE student.id = :id ")
    List<Tuple> getDetailById(@Param("id") Long id);
}
