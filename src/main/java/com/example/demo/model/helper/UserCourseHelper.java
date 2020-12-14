package com.example.demo.model.helper;

import com.example.demo.model.User;
import com.example.demo.model.UserCourse;
import com.example.demo.model.dto.UserCourseDTO;

public class UserCourseHelper {
    private final UserCourseDTO userCourseDTO;

    public UserCourseHelper(UserCourseDTO userCourseDTO) {
        this.userCourseDTO = userCourseDTO;
    }

    public UserCourse userCourseDTOToUserCourse() {
        UserCourse userCourse = new UserCourse();
        userCourse.setId(this.userCourseDTO.getId());
        if (this.userCourseDTO.getCurrentLesson() != null) {
            userCourse.setCurrentLessonId(this.userCourseDTO.getCurrentLesson().getId());
        }
        userCourse.setProcess(this.userCourseDTO.getProcess());
        userCourse.setCourseId(this.userCourseDTO.getCourse().getId());
        userCourse.setPaymentDate(this.userCourseDTO.getPaymentDate());
        userCourse.setPaymentStatus(this.userCourseDTO.getPaymentStatus());
        if (this.userCourseDTO.getUser() != null) {
            userCourse.setUserId(this.userCourseDTO.getUser().getId());
        }
        if (this.userCourseDTO.getCurrentQuiz() != null) {
            userCourse.setCurrentQuizId(this.userCourseDTO.getCurrentQuiz().getId());
        }
        if (this.userCourseDTO.getCurrentLesson() != null) {
            userCourse.setCurrentLessonId(this.userCourseDTO.getCurrentLesson().getId());
        }
        return userCourse;
    }
}
