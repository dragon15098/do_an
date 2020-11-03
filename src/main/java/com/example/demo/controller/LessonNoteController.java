package com.example.demo.controller;

import com.example.demo.model.LessonNote;
import com.example.demo.model.dto.LessonNoteDTO;
import com.example.demo.service.LessonNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/lesson_note")
public class LessonNoteController {
    @Autowired
    LessonNoteService lessonNoteService;


    @GetMapping("/get/{lessonId}")
    public ResponseEntity<List<LessonNoteDTO>> getAllLessonNote(@PathVariable Long lessonId) {
        return new ResponseEntity<>(lessonNoteService.getNoteCurrentUser(lessonId), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<LessonNoteDTO> createLessonNote(@RequestBody LessonNoteDTO lessonNoteDTO) {
        return new ResponseEntity<>(lessonNoteService.createLessonNote(lessonNoteDTO), HttpStatus.OK);
    }


    @PostMapping("/delete")
    public ResponseEntity<LessonNote> deleteLessonNote(@RequestBody LessonNoteDTO lessonNoteDTO) {
        try {
            lessonNoteService.deleteLessonNote(lessonNoteDTO);
        } catch (Exception e) {

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
