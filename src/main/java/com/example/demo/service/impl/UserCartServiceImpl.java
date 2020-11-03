package com.example.demo.service.impl;

import com.example.demo.model.UserCart;
import com.example.demo.repository.UserCartRepository;
import com.example.demo.service.UserCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCartServiceImpl implements UserCartService {
    private final UserCartRepository userCartRepository;

    @Override
    public UserCart createUserCart(UserCart userCart) {
        userCart = userCartRepository.save(userCart);
        return userCart;
    }

    @Override
    public List<UserCart> findByUserId(Long id) {
        return userCartRepository.findAllByUserId(id);
    }
}
