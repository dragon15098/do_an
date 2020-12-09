package com.example.demo.service.impl;

import com.example.demo.model.Quiz;
import com.example.demo.model.QuizAnswer;
import com.example.demo.model.dto.AnswerResultDTO;
import com.example.demo.model.dto.QuizDTO;
import com.example.demo.model.dto.QuizQuestionDTO;
import com.example.demo.model.helper.QuizHelper;
import com.example.demo.repository.QuizRepository;
import com.example.demo.service.QuizQuestionService;
import com.example.demo.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizQuestionService quizQuestionService;

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
        answerResultDTO.setQuizQuestions(questions);
        if (passQuiz) {
//            UserCourseDTO userCourse = userCourseService.getUserCourseByUserCourseId(userCourseId);
//            if (userCourse.getCurrentQuiz() != null && userCourse.getCurrentQuiz().getId().equals(quizId)) {
//                goToNextLesson(userCourse);
//            }
        }
        return answerResultDTO;
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

//    private void goToNextLesson(UserCourseDTO userCourse) {
//        userCourseService.goToNextLesson(userCourse);
//    }
}
