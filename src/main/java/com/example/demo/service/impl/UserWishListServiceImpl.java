package com.example.demo.service.impl;

import com.example.demo.model.UserWishList;
import com.example.demo.repository.UserWishListRepository;
import com.example.demo.service.UserWishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserWishListServiceImpl implements UserWishListService {
    @Autowired
    UserWishListRepository userWishListRepository;

    @Override
    public UserWishList addNewUserWishList(UserWishList userWishList) {
        return userWishListRepository.save(userWishList);
    }

    @Override
    public List<UserWishList> getUserWishListByUserId(Long id) {
        return userWishListRepository.getAllByUserId(id);
    }
}
