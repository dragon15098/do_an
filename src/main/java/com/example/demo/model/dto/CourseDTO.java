package com.example.demo.model.dto;

import com.example.demo.model.Course;
import com.example.demo.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseDTO extends BaseDTO {
    private String title;
    private String imageDescriptionLink;
    private Float rating;
    private Integer ratingCount;

    private UserDTO instructor;

    private Date createTime;

    private String description;

    private Long commentCount;
    private Course.CourseStatus status;
}
