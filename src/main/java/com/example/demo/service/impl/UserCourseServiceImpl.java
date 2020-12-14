package com.example.demo.service.impl;

import com.example.demo.model.UserCourse;
import com.example.demo.model.dto.CourseDTO;
import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.QuizDTO;
import com.example.demo.model.dto.UserCourseDTO;
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
    public UserCourseDTO getUserCourseByUserCourseId(Long userCourseId) {
        return userCourseRepository.getUserCourseById(userCourseId).stream().map(this::tupleToUserCourse).findFirst().orElse(new UserCourseDTO());
    }


    private UserCourseDTO tupleToUserCourse(Tuple tuple) {
        UserCourseDTO userCourse = new UserCourseDTO();
        userCourse.setId((Long) tuple.get("id"));
        userCourse.setCourse(courseService.getCourseById((Long) tuple.get("courseId")));
        userCourse.setStatus((UserCourse.UserCourseStatus) tuple.get("status"));
        userCourse.setPaymentStatus((UserCourse.PaymentStatus) tuple.get("paymentStatus"));
        userCourse.setPaymentDate((Date) tuple.get("paymentDate"));
        userCourse.setProcess((Integer) tuple.get("process"));
        Long lessonId = (Long) tuple.get("currentLessonId");
        if (lessonId != null) {
            LessonDTO lesson = new LessonDTO();
            lesson.setId(lessonId);
            userCourse.setCurrentLesson(lesson);
        }
        Long quizId = (Long) tuple.get("currentQuizId");
        if (quizId != null) {
            QuizDTO quiz = new QuizDTO();
            quiz.setId(quizId);
            userCourse.setCurrentQuiz(quiz);
        }
        userCourse.setProcess((Integer) tuple.get("process"));
        return userCourse;
    }

    @Override
    public boolean goToNextLesson(UserCourseDTO userCourse) {
        LessonDTO currentLesson = userCourse.getCurrentLesson();
        QuizDTO currentQuiz = userCourse.getCurrentQuiz();
//        List<SectionDTO> courseSection = sectionService.getCourseSection(userCourse.getCourse().getId());
        List<Object> objects = new ArrayList<>();
//        courseSection.forEach(section -> {
//            objects.addAll(section.getLessons());
//            if (section.getQuiz() != null) {
//                objects.add(section.getQuiz());
//            }
//        });
        Long[] result = null;
        for (int i = 0; i < objects.size(); i++) {
            Object o = objects.get(i);
            if (o instanceof LessonDTO) {
                if (currentLesson != null) {
                    if (((LessonDTO) o).getId().equals(currentLesson.getId())) {
                        result = getNextId(objects, i);
                    }
                } else {
                    if (((LessonDTO) o).getId().equals(currentQuiz.getId())) {
                        result = getNextId(objects, i);
                    }
                }
            } else if (o instanceof QuizDTO) {
                if (currentQuiz != null) {
                    if (((QuizDTO) o).getId().equals(currentQuiz.getId())) {
                        result = getNextId(objects, i);
                    }
                } else {
                    if (((QuizDTO) o).getId().equals(currentLesson.getId())) {
                        result = getNextId(objects, i);
                    }
                }
            }
        }

        UserCourse entity = userCourseRepository.getOne(userCourse.getId());
        if (result != null) {
            entity.setCurrentLessonId(result[0]);
            entity.setCurrentQuizId(result[1]);
        }
        userCourseRepository.save(entity);
        return true;
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

                Long lessonId = (Long) tuple.get("currentLessonId");
                if (lessonId != null) {
                    LessonDTO lesson = new LessonDTO();
                    lesson.setId(lessonId);
                    userCourse.setCurrentLesson(lesson);
                }
                Long quizId = (Long) tuple.get("currentQuizId");
                if (quizId != null) {
                    QuizDTO quiz = new QuizDTO();
                    quiz.setId(quizId);
                    userCourse.setCurrentQuiz(quiz);
                }

                userCourse.setProcess((Integer) tuple.get("process"));
                return userCourse;
            }).findFirst().orElse(new UserCourseDTO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UserCourseDTO();
    }

    private Long[] getNextId(List<Object> objects, int i) {
        Long nextLessonId = null;
        Long nextQuizId = null;
        if (i < objects.size() - 1) {
            Object next = objects.get(i + 1);
            if (next instanceof LessonDTO) {
                nextLessonId = ((LessonDTO) next).getId();
            } else if (next instanceof QuizDTO) {
                nextQuizId = ((QuizDTO) next).getId();
            }
        }
        return new Long[]{nextLessonId, nextQuizId};
    }
}
