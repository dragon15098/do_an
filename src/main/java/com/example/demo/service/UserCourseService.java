package com.example.demo.service;

import com.example.demo.model.UserCourse;
import com.example.demo.model.dto.UserCourseDTO;

import java.util.List;

public interface UserCourseService {
    UserCourse insert(UserCourse userCourse);

    List<UserCourseDTO> getAllByUser();

    UserCourseDTO getUserCourseByUserCourseId(Long userCourseId);

    boolean goToNextLesson(UserCourseDTO userCourse);
}
