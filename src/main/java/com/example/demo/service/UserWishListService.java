package com.example.demo.service;

import com.example.demo.model.UserWishList;
import com.example.demo.model.dto.UserWishListDTO;

import java.util.List;

public interface UserWishListService {
    UserWishListDTO addNewUserWishList(UserWishListDTO userWishListDTO);

    List<UserWishListDTO> getUserWishListByUserId(Long id);
}
