package com.example.demo.model.dto;

import com.example.demo.model.Lesson;
import com.example.demo.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonNoteDTO extends BaseDTO {
    private Lesson lesson;
    private User user;
    private String note;
}
