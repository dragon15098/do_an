package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorDetailDTO extends BaseDTO {
    private String aboutMe;
    private int totalStudents;
    private int reviewCount;
    private String facebookLink;
    private String twitterLink;
    private int numberCourses;
    private float ratings;
    private String achievement;
    private String youtubeLink;
}
