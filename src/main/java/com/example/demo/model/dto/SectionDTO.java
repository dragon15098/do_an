package com.example.demo.model.dto;

import com.example.demo.model.Course;
import com.example.demo.model.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SectionDTO extends BaseDTO {
    private String sectionTitle;
    private int length;
    private QuizDTO quiz;
    private CourseDTO course;
    private List<LessonDTO> lessons;
}
