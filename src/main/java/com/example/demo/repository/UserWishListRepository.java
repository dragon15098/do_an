package com.example.demo.repository;

import com.example.demo.model.UserWishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWishListRepository extends JpaRepository<UserWishList, Long>, JpaSpecificationExecutor<UserWishList> {
    List<UserWishList> getAllByUserId(Long id);
}
