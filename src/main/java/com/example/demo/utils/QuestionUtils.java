package com.example.demo.utils;

import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.QuizDTO;
import com.example.demo.model.dto.UserCourseDTO;

import java.util.List;

public class QuestionUtils {
    private  QuestionUtils() {

    }

    public static Long[] getNextId(List<Object> objects, UserCourseDTO userCourse) {
        Long nextLessonId = null;
        Long nextQuizId = null;
        Long currentLessonId = userCourse.getCurrentLesson().getId();
        for (int i = 0; i < objects.size() - 1; i++) {
            Object current = objects.get(i);
            if (current instanceof LessonDTO && currentLessonId.equals(((LessonDTO) current).getId())) {
                if (i + 1 < objects.size() - 1) {
                    Object next = objects.get(i + 1);
                    if (next instanceof LessonDTO) {
                        nextLessonId = ((LessonDTO) next).getId();
                        nextQuizId = userCourse.getCurrentQuiz().getId();
                    } else if (next instanceof QuizDTO) {
                        nextLessonId = userCourse.getCurrentLesson().getId();
                        nextQuizId = ((QuizDTO) next).getId();
                    }
                    return new Long[]{nextLessonId, nextQuizId};
                }
            }
        }
        return null;
    }
}
