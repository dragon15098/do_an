package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWishListDTO extends BaseDTO{
    private UserDTO user;
    private CourseDTO course;
}
