package com.example.demo.controller;

import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.LessonNoteDTO;
import com.example.demo.service.LessonNoteService;
import com.example.demo.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/lesson")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonDTO> getAllLessonNote(@PathVariable Long lessonId) {
        return new ResponseEntity<>(lessonService.getLessonDetailById(lessonId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<LessonDTO> insertOrUpdate(@RequestBody LessonDTO lessonDTO) {
        return new ResponseEntity<>(lessonService.insertOrUpdate(lessonDTO), HttpStatus.OK);
    }

}
