package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LessonDTO extends BaseDTO{
    private SectionDTO section;
    private String lessonTitle;
    private String urlVideo;
    private String description;
    private List<LessonQuestionDTO> lessonQuestions;
}
