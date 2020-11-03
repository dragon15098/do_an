package com.example.demo.model.dto;

import com.example.demo.model.InstructorDetail;
import com.example.demo.model.StudentDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends BaseDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private InstructorDetailDTO instructorDetail;
    private StudentDetailDTO studentDetail;
}
