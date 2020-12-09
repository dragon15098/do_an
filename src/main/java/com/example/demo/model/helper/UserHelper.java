package com.example.demo.model.helper;

import com.example.demo.model.User;
import com.example.demo.model.dto.InstructorDetailDTO;
import com.example.demo.model.dto.StudentDetailDTO;
import com.example.demo.model.dto.UserDTO;

public class UserHelper {
    private final UserDTO userDTO;

    public UserHelper(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public User userDTOToUser() {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
}
