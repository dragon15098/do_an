package com.example.demo.service;

import com.example.demo.model.UserWishList;

import java.util.List;

public interface UserWishListService {
    UserWishList addNewUserWishList(UserWishList  userWishList);

    List<UserWishList> getUserWishListByUserId(Long id);
}
