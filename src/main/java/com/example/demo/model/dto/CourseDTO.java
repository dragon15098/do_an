package com.example.demo.model.dto;

import com.example.demo.model.Category;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long ratingCount;
    private UserDTO instructor;
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private Date createTime;
    private String description;
    private Long commentCount;
    private Long courseSell;
    private CategoryDTO category;
    private Course.CourseStatus status;
    private List<SectionDTO> sections;
    private Float price;
}
