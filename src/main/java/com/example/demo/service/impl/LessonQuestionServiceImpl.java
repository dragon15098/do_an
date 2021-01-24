package com.example.demo.service.impl;

import com.example.demo.model.LessonQuestion;
import com.example.demo.model.UserCourse;
import com.example.demo.model.dto.*;
import com.example.demo.model.helper.LessonQuestionHelper;
import com.example.demo.repository.*;
import com.example.demo.service.LessonAnswerService;
import com.example.demo.service.LessonQuestionService;
import com.example.demo.utils.QuestionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LessonQuestionServiceImpl implements LessonQuestionService {
    public final LessonAnswerService lessonAnswerService;

    public final LessonQuestionRepository lessonQuestionRepository;
    private final UserCourseRepository userCourseRepository;
    private final SectionRepository sectionRepository;
    private final LessonRepository lessonRepository;

    private final QuizRepository quizRepository;

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
        if (passed.get()) {
            UserCourseDTO userCourseDTO = userCourseRepository.getUserCourseById(userCourseId).stream().map(tuple -> {
                UserCourseDTO userCourse = new UserCourseDTO();

                userCourse.setId((Long) tuple.get("id"));

                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setId((Long) tuple.get("courseId"));
                userCourse.setCourse(courseDTO);

                LessonDTO lessonDTO = new LessonDTO();
                lessonDTO.setId((Long) tuple.get("currentLessonId"));
                userCourse.setCurrentLesson(lessonDTO);

                QuizDTO quizDTO = new QuizDTO();
                quizDTO.setId((Long) tuple.get("currentQuizId"));
                userCourse.setCurrentQuiz(quizDTO);

                return userCourse;
            }).findFirst().orElse(new UserCourseDTO());
            if (userCourseDTO.getCurrentLesson().getId() != null && userCourseDTO.getCurrentLesson().getId().equals(lessonId)) {
                goToNextLesson(userCourseDTO);
            }
            answerResult.setNextLessonId(userCourseDTO.getCurrentLesson().getId());
            answerResult.setNextQuizId(userCourseDTO.getCurrentQuiz().getId());
        }

        // set result for response
        answerResult.setLessonQuestions(lessonQuestions);
        return answerResult;
    }

    private List<SectionDTO> getCourseSection(Long courseId) {
        return sectionRepository.getAllSectionByCourseId(courseId).stream().map(tuple -> {
            SectionDTO sectionDTO = new SectionDTO();
            sectionDTO.setId((Long) tuple.get("id"));
            sectionDTO.setLessons(getLessonDTO(sectionDTO.getId()));
            sectionDTO.setQuiz(getQuizDTO((Long) tuple.get("quizId")));
            return sectionDTO;
        }).collect(Collectors.toList());
    }

    private List<LessonDTO> getLessonDTO(Long sectionId) {
        return lessonRepository.findAllLessonBySectionId(sectionId).stream().map(tuple -> {
            LessonDTO lessonDTO = new LessonDTO();
            lessonDTO.setId((Long) tuple.get("id"));
            return lessonDTO;
        }).collect(Collectors.toList());
    }

    private QuizDTO getQuizDTO(Long quizId) {
        return quizRepository.getQuizDetail(quizId).stream().map(tuple -> {
            QuizDTO quizDTO = new QuizDTO();
            quizDTO.setId((Long) tuple.get("id"));
            return quizDTO;
        }).findFirst().orElse(new QuizDTO());
    }

    private void goToNextLesson(UserCourseDTO userCourse) {
        List<Object> lessonAndQuiz = getAllLessonAndQuiz(userCourse.getCourse().getId());
        Long[] result = QuestionUtils.getNextId(lessonAndQuiz, userCourse);
        UserCourse entity = userCourseRepository.getOne(userCourse.getId());
        entity.setCurrentLessonId(result[0]);
        userCourse.getCurrentLesson().setId(result[0]);
        entity.setCurrentQuizId(result[1]);
        userCourse.getCurrentQuiz().setId(result[1]);
        entity.setProcess(Math.round(result[2]));
        userCourseRepository.save(entity);
    }

    private List<Object> getAllLessonAndQuiz(Long courseId) {
        List<SectionDTO> courseSection = getCourseSection(courseId);
        List<Object> objects = new ArrayList<>();
        courseSection.forEach(section -> {
            objects.addAll(section.getLessons());
            if (section.getQuiz() != null) {
                objects.add(section.getQuiz());
            }
        });
        return objects;
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

    @Override
    public void deleteLessonQuestionById(Long questionId) {
        deleteAnswerByQuestionId(questionId);
        deleteQuestionById(questionId);
    }

    private void deleteAnswerByQuestionId(Long questionId) {
        lessonAnswerService.deleteAnswerByQuestionId(questionId);
    }

    private void deleteQuestionById(Long questionId) {
        lessonQuestionRepository.deleteById(questionId);
    }


}
