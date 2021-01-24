package com.example.demo.service.impl;

import com.example.demo.model.Quiz;
import com.example.demo.model.QuizAnswer;
import com.example.demo.model.UserCourse;
import com.example.demo.model.dto.*;
import com.example.demo.model.helper.QuizHelper;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.SectionRepository;
import com.example.demo.repository.UserCourseRepository;
import com.example.demo.service.QuizQuestionService;
import com.example.demo.service.QuizService;
import com.example.demo.utils.QuestionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizQuestionService quizQuestionService;
    private final SectionRepository sectionRepository;
    private final LessonRepository lessonRepository;
    private final UserCourseRepository userCourseRepository;

    @Override
    public QuizDTO getQuizDetail(Long quizId) {
        return quizRepository.getQuizDetail(quizId).stream().map(tuple -> {
            QuizDTO quiz = new QuizDTO();
            quiz.setId((Long) tuple.get("id"));
            quiz.setQuizTitle((String) tuple.get("quizTitle"));
            quiz.setQuizQuestions(quizQuestionService.getAllQuizQuestionByQuizId(quizId));
            return quiz;
        }).findFirst().orElse(new QuizDTO());
    }

    @Override
    public AnswerResultDTO submitQuiz(Long userCourseId, Long quizId, List<QuizAnswer> currentAnswers) {
        AnswerResultDTO answerResultDTO = new AnswerResultDTO();
        // get correct answer from database
        List<QuizQuestionDTO> questions = quizQuestionService.getQuizQuestionWithCorrectAnswer(quizId);
        boolean passQuiz = checkUserAnswers(questions, currentAnswers);

        if (passQuiz) {
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
            if (userCourseDTO.getCurrentQuiz().getId() != null &&
                    userCourseDTO.getCurrentQuiz().getId().equals(quizId)) {
                goToNextLesson(answerResultDTO, userCourseDTO);
            }
            answerResultDTO.setNextLessonId(userCourseDTO.getCurrentLesson().getId());
            answerResultDTO.setNextQuizId(userCourseDTO.getCurrentQuiz().getId());
        }
        answerResultDTO.setQuizQuestions(questions);
        return answerResultDTO;
    }

    private void goToNextLesson(AnswerResultDTO answerResultDTO, UserCourseDTO userCourse) {
        List<Object> lessonAndQuiz = getAllLessonAndQuiz(userCourse.getCourse().getId());
        Long[] result = QuestionUtils.getNextId(lessonAndQuiz, userCourse);
        UserCourse entity = userCourseRepository.getOne(userCourse.getId());
        Long nextLessonId = result[0];
        Long nextQuizId = result[1];
        Long process = result[2];
        entity.setCurrentLessonId(nextLessonId);
        userCourse.getCurrentLesson().setId(nextLessonId);
        entity.setCurrentQuizId(nextQuizId);
        userCourse.getCurrentQuiz().setId(nextQuizId);
        if (isTheLastQuiz(nextLessonId)) {
            answerResultDTO.setUserCourseStatus(UserCourse.UserCourseStatus.COMPLETE);
            entity.setStatus(UserCourse.UserCourseStatus.COMPLETE);
        }
        entity.setProcess(Math.round(process));
        userCourseRepository.save(entity);
    }

    private boolean isTheLastQuiz(Long nextLessonId) {
        return nextLessonId == null;
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


    @Override
    public QuizDTO insertOrUpdate(QuizDTO quizDTO) {
        QuizHelper quizHelper = new QuizHelper(quizDTO);
        Quiz quiz = quizHelper.quizDTOToQuiz();
        quiz = quizRepository.save(quiz);
        quizDTO.setId(quiz.getId());

        saveQuizQuestion(quizDTO, quizDTO.getQuizQuestions());
        return quizDTO;
    }

    private void saveQuizQuestion(QuizDTO quizDTO, List<QuizQuestionDTO> quizQuestions) {
        if (quizQuestions != null) {
            // ignore duplicate when send back DTO
            QuizDTO quiz = new QuizDTO();
            quiz.setId(quizDTO.getId());

            quizQuestions.forEach(quizQuestion -> quizQuestion.setQuiz(quiz));
            quizQuestionService.insertOrUpdate(quizQuestions);
        }
    }

    private boolean checkUserAnswers(List<QuizQuestionDTO> questions, List<QuizAnswer> currentAnswers) {
        boolean passQuiz = true;
        for (int i = 0; i < questions.size(); i++) {
            QuizAnswer currentAnswer = currentAnswers.get(i);
            if (currentAnswer.getId().equals(questions.get(i).getCorrectAnswer().getId())) {
                questions.get(i).setQuizQuestionStatus(QuizQuestionDTO.QuizQuestionStatus.SUCCESS);
            } else {
                passQuiz = false;
                questions.get(i).setQuizQuestionStatus(QuizQuestionDTO.QuizQuestionStatus.ERROR);
            }
        }
        return passQuiz;
    }

}
