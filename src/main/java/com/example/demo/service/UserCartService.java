package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.UserCart;

import java.util.List;

public interface UserCartService {
    UserCart createUserCart(UserCart userCart);

    List<UserCart> findByUserId(Long id);
}
