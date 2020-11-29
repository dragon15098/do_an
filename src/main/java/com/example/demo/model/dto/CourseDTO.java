package com.example.demo.model.dto;

import com.example.demo.model.Category;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private CategoryDTO category;
    private Course.CourseStatus status;
    private List<SectionDTO> sections;
}
