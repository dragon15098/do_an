package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.service.UserRoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<UserDTO> getUserDetail2(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getDetail(id), HttpStatus.OK);
    }


    @GetMapping("/get_detail")
    public ResponseEntity<UserDTO> getCurrentUserDetail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (!auth.getPrincipal().equals("anonymousUser")) {
                Long userId = Long.parseLong(auth.getPrincipal().toString());
                UserDTO user = userService.getDetail(userId);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new UserDTO(), HttpStatus.OK);
    }

    @GetMapping("/instructor")
    public ResponseEntity<List<UserDTO>> getAllIntruder() {
        return new ResponseEntity<>(userService.getAllIntruder(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> insertOrUpdate(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.insertOrUpdate(userDTO), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserDetail(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getDetail(userId), HttpStatus.OK);
    }

    @PostMapping("/update_profile")
    public ResponseEntity<UserDTO> uploadProfile(@RequestPart("user") UserDTO userDTO, @RequestPart MultipartFile file) {
        return new ResponseEntity<>(userService.updateProfile(file, userDTO), HttpStatus.OK);
    }


    @PostMapping("/update_profile_without_image")
    public ResponseEntity<UserDTO> uploadProfile(@RequestPart("user") UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateProfile(userDTO), HttpStatus.OK);
    }
}
