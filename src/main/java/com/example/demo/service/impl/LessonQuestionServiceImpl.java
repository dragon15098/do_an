package com.example.demo.service.impl;

import com.example.demo.model.dto.AnswerResultDTO;
import com.example.demo.model.dto.LessonAnswerDTO;
import com.example.demo.model.dto.LessonQuestionDTO;
import com.example.demo.model.dto.UserCourseDTO;
import com.example.demo.repository.LessonQuestionRepository;
import com.example.demo.service.LessonAnswerService;
import com.example.demo.service.LessonQuestionService;
import com.example.demo.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LessonQuestionServiceImpl implements LessonQuestionService {
    public final LessonQuestionRepository lessonQuestionRepository;
    public final LessonAnswerService lessonAnswerService;
//    public final UserCourseService userCourseService;

    @Override
    public List<LessonQuestionDTO> getLessonQuestion(Long lessonId) {
        return lessonQuestionRepository.findAllByLessonId(lessonId).stream().map(tuple -> {
            LessonQuestionDTO lessonQuestionDTO = new LessonQuestionDTO();
            lessonQuestionDTO.setId((Long) tuple.get("id"));
            lessonQuestionDTO.setQuestion((String) tuple.get("question"));
            lessonQuestionDTO.setQuestionTitle((String) tuple.get("questionTitle"));
            List<LessonAnswerDTO> lessonAnswers = lessonAnswerService
                    .getAllLessonAnswerByLessonId(lessonQuestionDTO.getId());
            lessonQuestionDTO.setLessonAnswers(lessonAnswers);
            return lessonQuestionDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public AnswerResultDTO checkLessonQuestion(Long userCourseId, Long lessonId, List<LessonAnswerDTO> userAnswers) {
        AnswerResultDTO answerResult = new AnswerResultDTO();

        // get correct answer from data base
        List<LessonQuestionDTO> lessonQuestions = lessonQuestionRepository.findCorrectAnswerIdByLessonId(lessonId).stream().map(tuple -> {
            LessonQuestionDTO lessonQuestion = new LessonQuestionDTO();
            LessonAnswerDTO lessonAnswer = new LessonAnswerDTO();
            lessonAnswer.setId(Long.parseLong(tuple.get("correctAnswerId").toString()));
            lessonQuestion.setCorrectAnswer(lessonAnswer);
            lessonQuestion.setLessonQuestionStatus(LessonQuestionDTO.LessonQuestionStatus.NOT_ANSWERED);
            return lessonQuestion;
        }).collect(Collectors.toList());

        // compare user answer with correct answer
        AtomicBoolean passed = new AtomicBoolean(true);
        IntStream.range(0, lessonQuestions.size()).forEach(idx -> {
            LessonQuestionDTO lessonQuestion = lessonQuestions.get(idx);
            if (userAnswers.get(idx).getId().equals(lessonQuestion.getCorrectAnswer().getId())) {
                lessonQuestion.setLessonQuestionStatus(LessonQuestionDTO.LessonQuestionStatus.SUCCESS);
            } else {
                passed.set(false);
                lessonQuestion.setLessonQuestionStatus(LessonQuestionDTO.LessonQuestionStatus.ERROR);
            }
        });

        // if all true -> move user to next lesson
//        if (passed.get()) {
//            UserCourseDTO userCourse = userCourseService.getUserCourseByUserCourseId(userCourseId);
//            if (userCourse.getCurrentLesson().getId().equals(lessonId)) {
//                userCourseService.goToNextLesson(userCourse);
//            }
//        }

        // set result for response
        answerResult.setLessonQuestions(lessonQuestions);
        return answerResult;
    }
}
