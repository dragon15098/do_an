package com.example.demo.service.impl;

import com.example.demo.model.UserCourse;
import com.example.demo.model.dto.*;
import com.example.demo.model.helper.UserCourseHelper;
import com.example.demo.repository.UserCourseRepository;
import com.example.demo.service.CourseService;
import com.example.demo.service.LessonService;
import com.example.demo.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCourseServiceImpl implements UserCourseService {
    private final UserCourseRepository userCourseRepository;
    private final CourseService courseService;
    private final LessonService lessonService;
//    private final SectionService sectionService;

    @Override
    public UserCourseDTO insertOrUpdate(UserCourseDTO userCourseDTO) {
        UserCourseHelper userCourseHelper = new UserCourseHelper(userCourseDTO);
        UserCourse userCourse = userCourseHelper.userCourseDTOToUserCourse();
        userCourse.setStatus(UserCourse.UserCourseStatus.INCOMPLETE);
        userCourse.setPaymentStatus(UserCourse.PaymentStatus.COMPLETE);
        Calendar calendar = Calendar.getInstance();
        userCourse.setPaymentDate(calendar.getTime());
        userCourse.setCreateDate(calendar.getTime());
        userCourse.setProcess(0);
        userCourse.setCurrentLessonId(lessonService.getFistLessonIdByCourseId(userCourse.getCourseId()).getId());
        userCourse = userCourseRepository.save(userCourse);
        userCourseDTO.setId(userCourse.getId());
        return userCourseDTO;
    }

    @Override
    public List<UserCourseDTO> getAllByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long userId = Long.parseLong(auth.getPrincipal().toString());
            return userCourseRepository.getAllUserCourseByUserId(userId).stream().map(tuple -> {
                UserCourseDTO userCourse = tupleToUserCourse(tuple);
                userCourse.setCurrentLesson(lessonService.getLessonById((Long) tuple.get("currentLessonId")));
                return userCourse;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public UserCourseDTO ratingCourse(UserCourseDTO userCourseDTO) {
        UserCourse userCourse = userCourseRepository.getOne(userCourseDTO.getId());
        userCourse.setCourseComment(userCourseDTO.getCourseComment());
        userCourse.setInstructorComment(userCourseDTO.getInstructorComment());
        userCourse.setCourseRating(userCourseDTO.getCourseRating());
        userCourse.setInstructorRating(userCourseDTO.getInstructorRating());
        userCourseRepository.save(userCourse);
        return userCourseDTO;
    }


    private UserCourseDTO tupleToUserCourse(Tuple tuple) {
        UserCourseDTO userCourse = new UserCourseDTO();
        userCourse.setId((Long) tuple.get("id"));
        userCourse.setCourse(courseService.getCourseById((Long) tuple.get("courseId")));
        userCourse.setStatus((UserCourse.UserCourseStatus) tuple.get("status"));
        userCourse.setPaymentStatus((UserCourse.PaymentStatus) tuple.get("paymentStatus"));
        userCourse.setPaymentDate((Date) tuple.get("paymentDate"));
        userCourse.setProcess((Integer) tuple.get("process"));

        userCourse.setCurrentLesson(getLessonFromTuple(tuple));
        userCourse.setCurrentQuiz(getQuizFromTuple(tuple));

        Long quizId = (Long) tuple.get("currentQuizId");
        if (quizId != null) {
            QuizDTO quiz = new QuizDTO();
            quiz.setId(quizId);
            userCourse.setCurrentQuiz(quiz);
        }
        userCourse.setProcess((Integer) tuple.get("process"));
        return userCourse;
    }

    private LessonDTO getLessonFromTuple(Tuple tuple) {
        Long lessonId = (Long) tuple.get("currentLessonId");
        if (lessonId != null) {
            LessonDTO lesson = new LessonDTO();
            lesson.setId(lessonId);
            return lesson;
        }
        return null;
    }

    private QuizDTO getQuizFromTuple(Tuple tuple) {
        Long quizId = (Long) tuple.get("currentQuizId");
        if (quizId != null) {
            QuizDTO quiz = new QuizDTO();
            quiz.setId(quizId);
            return quiz;
        }
        return null;
    }

    @Override
    public UserCourseDTO getUserCourseByCourseId(Long courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            Long userId = Long.parseLong(auth.getPrincipal().toString());
            return userCourseRepository.getDetailByCourseIdAndUserId(courseId, userId).stream().map(tuple -> {
                UserCourseDTO userCourse = new UserCourseDTO();
                // create course
                CourseDTO course = new CourseDTO();
                course.setId((Long) tuple.get("courseId"));

                userCourse.setId((Long) tuple.get("id"));
                userCourse.setCourse(course);

                userCourse.setCurrentLesson(getLessonFromTuple(tuple));
                userCourse.setCurrentQuiz(getQuizFromTuple(tuple));

                userCourse.setProcess((Integer) tuple.get("process"));
                return userCourse;
            }).findFirst().orElse(new UserCourseDTO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UserCourseDTO();
    }

    @Override
    public List<UserCourseDTO> getUserComments(Long courseId) {
        List<UserCourseDTO> comments = userCourseRepository.getUserComments(courseId).stream().map(tuple -> {
            UserCourseDTO userCourse = new UserCourseDTO();
            userCourse.setCourseComment((String) tuple.get("courseComment"));
            userCourse.setCourseRating((Float) tuple.get("courseRating"));
            UserDTO user = new UserDTO();
            user.setImageUrl((String) tuple.get("imageUrl"));
            userCourse.setUser(user);
            return userCourse;
        }).collect(Collectors.toList());
        if (comments.size() >= 3) {
            return comments.subList(0, 3);
        }
        return comments;
    }
}
