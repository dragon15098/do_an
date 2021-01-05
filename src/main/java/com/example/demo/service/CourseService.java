package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.dto.CategoryDTO;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAll();

    List<CourseDTO> getCourseCreateByUserId(Long instructorId);

    CourseDTO getCourseById(Long id);

    CourseDTO insertOrUpdate(CourseDTO courseDTO);

    List<CourseDTO> findCourseByTitle(String title);

    Page<CourseDTO> getCourseHottest(Integer pageNumber, Integer pageSize);

    CourseDTO approveCourse(CourseDTO courseDTO);

    Page<CourseDTO> getCourseNewest(Integer pageNumber, Integer pageSize);

    Page<CourseDTO> getSomeRandomCourse(Integer pageNumber, Integer pageSize);

    List<CourseDTO> getCourseByFilter(List<Long> categoryId, String name);

    Page<CourseDTO> searchCourse(String categoryIds, String title, String status, String fromDate,
                                 String toDate, String instructorName, Integer pageSize, Integer pageNumber);

}
