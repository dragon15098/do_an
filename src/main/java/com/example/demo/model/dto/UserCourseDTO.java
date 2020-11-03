package com.example.demo.model.dto;

import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
import com.example.demo.model.UserCourse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@Setter
public class UserCourseDTO extends BaseDTO {
    private UserDTO user;
    private CourseDTO course;
    private UserCourse.UserCourseStatus status;
    private LessonDTO currentLesson;
    private QuizDTO currentQuiz;
    private int process;
    private UserCourse.PaymentStatus paymentStatus;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private Date paymentDate;
}
