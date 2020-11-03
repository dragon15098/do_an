package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonAnswerDTO extends BaseDTO {
    private String explanation;
    private String content;
}
