package com.example.demo.repository;

import com.example.demo.model.UserCart;
import com.example.demo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, Long>, JpaSpecificationExecutor<UserCart> {
    List<UserCart> findAllByUserId(Long id);
}
