package com.example.demo.model.helper;

import com.example.demo.model.Lesson;
import com.example.demo.model.dto.LessonDTO;

public class LessonHelper {
    private final LessonDTO lessonDTO;

    public LessonHelper(LessonDTO lessonDTO) {
        this.lessonDTO = lessonDTO;
    }

    public Lesson lessonDTOToLesson() {
        Lesson lesson = new Lesson();
        lesson.setId(this.lessonDTO.getId());
        lesson.setLessonTitle(this.lessonDTO.getLessonTitle());
        lesson.setUrlVideo(this.lessonDTO.getUrlVideo());
        lesson.setSectionId(this.lessonDTO.getSection().getId());
        lesson.setDescription(this.lessonDTO.getDescription());
        return lesson;
    }

}
