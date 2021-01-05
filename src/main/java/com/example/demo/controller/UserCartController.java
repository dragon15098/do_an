package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserCart;
import com.example.demo.model.dto.UserCartDTO;
import com.example.demo.service.UserCartService;
import com.example.demo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/user_cart")
public class UserCartController {
    @Autowired
    UserCartService userCartService;

    @PostMapping("/create")
    public ResponseEntity<UserCart> insert(@RequestBody UserCart userCart) {
        userCart = userCartService.createUserCart(userCart);
        return new ResponseEntity<>(userCart, HttpStatus.OK);
    }

    @GetMapping("/get_user_cart")
    public ResponseEntity<List<UserCartDTO>> getUserCart() {
        return new ResponseEntity<>(userCartService.getUserCart(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<UserCartDTO> deleteUserCart(@PathVariable Long id) {
        return new ResponseEntity<>(userCartService.deleteUserCart(id), HttpStatus.OK);
    }

}
