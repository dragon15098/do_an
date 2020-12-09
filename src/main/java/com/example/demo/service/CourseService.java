package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.model.dto.LessonDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAll();

    List<CourseDTO> getCourseCreateByUserId(Long instructorId);

    CourseDTO getCourseById(Long id);

    CourseDTO insertOrUpdate(CourseDTO courseDTO);
}
