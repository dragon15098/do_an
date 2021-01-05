package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCartDTO extends BaseDTO{
    private UserDTO user;
    private CourseDTO course;
}
