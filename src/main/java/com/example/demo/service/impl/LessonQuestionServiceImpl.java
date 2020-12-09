package com.example.demo.service.impl;

import com.example.demo.model.Lesson;
import com.example.demo.model.LessonAnswer;
import com.example.demo.model.LessonQuestion;
import com.example.demo.model.dto.AnswerResultDTO;
import com.example.demo.model.dto.LessonAnswerDTO;
import com.example.demo.model.dto.LessonQuestionDTO;
import com.example.demo.model.helper.LessonQuestionHelper;
import com.example.demo.repository.LessonQuestionRepository;
import com.example.demo.service.LessonAnswerService;
import com.example.demo.service.LessonQuestionService;
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
            setAnswerForLessonQuestion(lessonQuestionDTO, (Long) tuple.get("correctAnswerId"));
            return lessonQuestionDTO;
        }).collect(Collectors.toList());
    }

    private void setAnswerForLessonQuestion(LessonQuestionDTO lessonQuestionDTO, Long correctAnswerId) {
        List<LessonAnswerDTO> lessonAnswers = lessonAnswerService
                .getAllLessonAnswerByLessonId(lessonQuestionDTO.getId());
        lessonAnswers.forEach(answer -> {
            if (answer.getId().equals(correctAnswerId)) {
                lessonQuestionDTO.setCorrectAnswer(answer);
            }
        });
        lessonQuestionDTO.setLessonAnswers(lessonAnswers);
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

    @Override
    public LessonQuestionDTO insertOrUpdate(LessonQuestionDTO lessonQuestionDTO) {
        LessonQuestionHelper lessonQuestionHelper = new LessonQuestionHelper(lessonQuestionDTO);
        LessonQuestion lessonQuestion = lessonQuestionHelper.lessonQuestionDTOToLessonQuestion();
        lessonQuestion = lessonQuestionRepository.save(lessonQuestion);
        lessonQuestionDTO.setId(lessonQuestion.getId());

        insertOrUpdateAnswer(lessonQuestionDTO);

        updateCorrectAnswer(lessonQuestion, lessonQuestionDTO);
        return lessonQuestionDTO;
    }

    private void updateCorrectAnswer(LessonQuestion lessonQuestion, LessonQuestionDTO lessonQuestionDTO) {
        List<LessonAnswerDTO> lessonAnswerDTOs = lessonQuestionDTO.getLessonAnswers();
        LessonAnswerDTO correctAnswer = lessonAnswerDTOs.get(lessonQuestionDTO.getCorrectQuestionPosition());
        lessonQuestion.setCorrectAnswerId(correctAnswer.getId());
        lessonQuestionRepository.save(lessonQuestion);
    }


    private void insertOrUpdateAnswer(LessonQuestionDTO lessonQuestionDTO) {

        //fake dto ignore loop in response
        LessonQuestionDTO lessonQuestion = new LessonQuestionDTO();
        lessonQuestion.setId(lessonQuestionDTO.getId());
        if (lessonQuestionDTO.getLessonAnswers() != null) {
            lessonQuestionDTO.getLessonAnswers().forEach(lessonAnswerDTO -> lessonAnswerDTO.setLessonQuestion(lessonQuestion));
            lessonAnswerService.insertOrUpdate(lessonQuestionDTO.getLessonAnswers());
        }
    }

    @Override
    public List<LessonQuestionDTO> insertOrUpdate(List<LessonQuestionDTO> lessonQuestionDTOs) {
        lessonQuestionDTOs.forEach(this::insertOrUpdate);
        return lessonQuestionDTOs;
    }
}
