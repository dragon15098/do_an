package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserCourse;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.service.UserRoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @PostMapping("/register")
    public ResponseEntity<User> insert(@RequestBody User user) {
        user = userService.createUser(user);
        userRoleService.createUserRole(user, new Role(2L));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> hack() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/getDetail/{id}")
    public ResponseEntity<UserDTO> getUserDetail(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getDetail(id), HttpStatus.OK);
    }


    @GetMapping("/get_detail")
    public ResponseEntity<UserDTO> getCurrentUserDetail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long userId = Long.parseLong(auth.getPrincipal().toString());
            UserDTO user = userService.getDetail(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ignored) {
        }
        return new ResponseEntity<>(new UserDTO(), HttpStatus.OK);
    }

    @GetMapping("/instructor")
    public ResponseEntity<List<UserDTO>> getAllIntruder() {
        return new ResponseEntity<>(userService.getAllIntruder(), HttpStatus.OK);
    }
}
