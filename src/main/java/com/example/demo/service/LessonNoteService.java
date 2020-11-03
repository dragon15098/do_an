package com.example.demo.service;

import com.example.demo.model.Lesson;
import com.example.demo.model.LessonNote;
import com.example.demo.model.dto.LessonNoteDTO;

import java.util.List;

public interface LessonNoteService {
    List<LessonNoteDTO> getNoteCurrentUser(Long lessonId);

    LessonNoteDTO createLessonNote(LessonNoteDTO lessonNote);

    void deleteLessonNote(LessonNoteDTO lessonNote);
}
