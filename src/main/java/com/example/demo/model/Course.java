package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "course")
public class Course extends BaseModel {
    private String title;
    private String imageDescriptionLink;
    private Float rating;
    private Long ratingCount;
    private Long instructorId;
    private Long categoryId;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private Date createTime;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    private String description;

    private Long commentCount;
    private Float price;

    public enum CourseStatus {
        WAIT, APPROVED
    }

}
