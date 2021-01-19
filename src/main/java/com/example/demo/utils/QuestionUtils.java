package com.example.demo.utils;

import com.example.demo.model.dto.BaseDTO;
import com.example.demo.model.dto.LessonDTO;
import com.example.demo.model.dto.QuizDTO;
import com.example.demo.model.dto.UserCourseDTO;

import java.util.List;

public class QuestionUtils {
    private QuestionUtils() {

    }

    public static Long[] getNextId(List<Object> lessonAndQuiz, UserCourseDTO userCourse) {
        Long nextLessonId;
        Long nextQuizId;
        Long currentLessonId = userCourse.getCurrentLesson().getId();
        Long currentQuizId = userCourse.getCurrentQuiz().getId();

        for (int i = 0; i < lessonAndQuiz.size(); i++) {
            BaseDTO current = (BaseDTO) lessonAndQuiz.get(i);
            if (currentLessonId != null) {
                if (current.getId().equals(currentLessonId)) {
                    if (i + 1 < lessonAndQuiz.size()) {
                        BaseDTO nextObject = (BaseDTO) lessonAndQuiz.get(i + 1);
                        if (nextObject instanceof LessonDTO) {
                            nextLessonId = nextObject.getId();
                            return new Long[]{nextLessonId, null, (long) ((i + 1) * 100 / lessonAndQuiz.size())};
                        } else if (nextObject instanceof QuizDTO) {
                            nextQuizId = nextObject.getId();
                            return new Long[]{null, nextQuizId, (long) ((i + 1) * 100 / lessonAndQuiz.size())};
                        }
                    }
                }
            } else if (currentQuizId != null) {
                if (current.getId().equals(currentQuizId)) {
                    if (i + 1 < lessonAndQuiz.size()) {
                        BaseDTO nextObject = (BaseDTO) lessonAndQuiz.get(i + 1);
                        if (nextObject instanceof LessonDTO) {
                            nextLessonId = nextObject.getId();
                            return new Long[]{nextLessonId, null, (long) ((i + 1) * 100 / lessonAndQuiz.size())};
                        }
                    }
                }
            }
        }
        return new Long[]{currentLessonId, currentQuizId, 100L};
    }
}
