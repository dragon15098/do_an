package com.example.demo.controller;

import com.example.demo.model.UserWishList;
import com.example.demo.model.dto.UserWishListDTO;
import com.example.demo.service.UserWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/user_wish_list")
public class UserWishListController {
    @Autowired
    UserWishListService userWishListService;

    @PostMapping("/create")
    public ResponseEntity<UserWishListDTO> insert(@RequestBody UserWishListDTO userWishListDTO) {
        userWishListDTO = userWishListService.addNewUserWishList(userWishListDTO);
        return new ResponseEntity<>(userWishListDTO, HttpStatus.OK);
    }

    @GetMapping("/get_user_wish_list/{id}")
    public ResponseEntity<List<UserWishListDTO>> getUserWishList(@PathVariable Long id) {
        return new ResponseEntity<>(userWishListService.getUserWishListByUserId(id), HttpStatus.OK);
    }
}
