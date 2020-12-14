package com.example.demo.repository;

import com.example.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Query(value = " SELECT  c.id as id, " +
            "c.name as name " +
            " FROM Category c ")
    List<Tuple> getAllCategory();

    @Query(value = " SELECT  c.id as id, " +
            "c.name as name " +
            " FROM Category c " +
            " WHERE c.id = :id")
    List<Tuple> getCategoryById(@Param("id") Long id);
}
