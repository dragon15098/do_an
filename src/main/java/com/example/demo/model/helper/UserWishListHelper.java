package com.example.demo.model.helper;

import com.example.demo.model.UserWishList;
import com.example.demo.model.dto.UserWishListDTO;

public class UserWishListHelper {
    private final UserWishListDTO userWishListDTO;

    public UserWishListHelper(UserWishListDTO userWishListDTO) {
        this.userWishListDTO = userWishListDTO;
    }

    public UserWishList userWishListDTOToUserWishList() {
        UserWishList userWishList = new UserWishList();
        if (userWishListDTO.getUser() != null) {
            userWishList.setUserId(userWishListDTO.getUser().getId());
        }
        if (userWishListDTO.getCourse() != null) {
            userWishList.setCourseId(userWishListDTO.getCourse().getId());
        }
        return userWishList;
    }
}
