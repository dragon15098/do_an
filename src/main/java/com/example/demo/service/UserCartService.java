package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.UserCart;
import com.example.demo.model.dto.UserCartDTO;

import java.util.List;

public interface UserCartService {
    UserCart createUserCart(UserCart userCart);

    List<UserCartDTO> getUserCart();

    UserCartDTO deleteUserCart(Long id);
}
