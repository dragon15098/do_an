package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_course")
public class UserCourse extends BaseModel {

    private Long userId;
    private Long courseId;
    @Enumerated(EnumType.STRING)
    private UserCourseStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private Date paymentDate;
    private Long currentLessonId;
    private Long currentQuizId;
    private int process;
    public enum UserCourseStatus {
        INCOMPLETE, COMPLETE
    }

    public enum PaymentStatus {
        INCOMPLETE, COMPLETE
    }
}
