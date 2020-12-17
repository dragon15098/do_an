package com.example.demo.model.dto;

import com.example.demo.model.InstructorDetail;
import com.example.demo.model.Role;
import com.example.demo.model.StudentDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO extends BaseDTO {
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private InstructorDetailDTO instructorDetail;
    private StudentDetailDTO studentDetail;
    private List<RoleDTO> roles;
}
