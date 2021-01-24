package com.example.demo.model.helper;

import com.example.demo.model.Course;
import com.example.demo.model.dto.CourseDTO;

public class CourseHelper {
    private final CourseDTO courseDTO;

    public CourseHelper(CourseDTO courseDTO) {
        this.courseDTO = courseDTO;
    }

    public Course courseDTOToCourse() {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setTitle(courseDTO.getTitle());
        course.setImageDescriptionLink(courseDTO.getImageDescriptionLink());
        if(courseDTO.getInstructor()!=null){
            course.setInstructorId(courseDTO.getInstructor().getId());
        }
        course.setCategoryId(courseDTO.getCategory().getId());
        course.setDescription(courseDTO.getDescription());
        return course;
    }

}
