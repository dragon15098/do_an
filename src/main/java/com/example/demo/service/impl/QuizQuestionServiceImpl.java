package com.example.demo.service.impl;

import com.example.demo.model.QuizQuestion;
import com.example.demo.model.dto.QuizAnswerDTO;
import com.example.demo.model.dto.QuizQuestionDTO;
import com.example.demo.model.helper.QuizQuestionHelper;
import com.example.demo.repository.QuizQuestionRepository;
import com.example.demo.service.QuizAnswerService;
import com.example.demo.service.QuizQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizQuestionServiceImpl implements QuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAnswerService quizAnswerService;

    @Override
    public List<QuizQuestionDTO> getAllQuizQuestionByQuizId(Long quizId) {
        return quizQuestionRepository.getAllQuizQuestionByQuizId(quizId)
                .stream()
                .map(tuple -> {
                    QuizQuestionDTO quizQuestionDTO = this.tupleToQuizQuestionDTO(tuple);
                    QuizAnswerDTO correctAnswer = quizQuestionDTO.getCorrectAnswer();
                    for (int i = 0; i < quizQuestionDTO.getQuizAnswers().size(); i++) {
                        QuizAnswerDTO quizAnswer = quizQuestionDTO.getQuizAnswers().get(i);
                        if (correctAnswer.getId().equals(quizAnswer.getId())) {
                            quizQuestionDTO.setCorrectAnswerPosition(i);
                            break;
                        }
                    }
                    return quizQuestionDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizQuestionDTO> getQuizQuestionWithCorrectAnswer(Long quizId) {
        return quizQuestionRepository
                .getAllQuizQuestionByQuizId(quizId)
                .stream()
                .map(this::tupleToQuizQuestionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizQuestionDTO> insertOrUpdate(List<QuizQuestionDTO> quizQuestionDTOs) {
        quizQuestionDTOs.forEach(quizQuestionDTO -> {
            QuizQuestionHelper quizQuestionHelper = new QuizQuestionHelper(quizQuestionDTO);
            QuizQuestion quizQuestion = quizQuestionHelper.quizQuestionDTOToQuizQuestion();
            quizQuestion = quizQuestionRepository.save(quizQuestion);
            quizQuestionDTO.setId(quizQuestion.getId());
            saveQuizAnswers(quizQuestionDTO);

            updateCorrectAnswer(quizQuestion, quizQuestionDTO);
        });
        return quizQuestionDTOs;
    }

    @Override
    public void deleteById(Long id) {
        deleteAnswerByQuestionId(id);
        deleteQuestionById(id);
    }

    private void deleteQuestionById(Long id) {
        quizQuestionRepository.deleteById(id);

    }

    private void deleteAnswerByQuestionId(Long id) {
        quizAnswerService.deleteAnswerByQuestionId(id);
    }

    private void updateCorrectAnswer(QuizQuestion quizQuestion, QuizQuestionDTO quizQuestionDTO) {
        List<QuizAnswerDTO> quizAnswerDTOS = quizQuestionDTO.getQuizAnswers();
        QuizAnswerDTO correctAnswer = quizAnswerDTOS.get(quizQuestionDTO.getCorrectAnswerPosition());
        quizQuestion.setCorrectAnswerId(correctAnswer.getId());
        quizQuestionRepository.save(quizQuestion);
    }

    private void saveQuizAnswers(QuizQuestionDTO quizQuestionDTO) {
        //fake dto ignore loop dto when send response
        QuizQuestionDTO quizQuestion = new QuizQuestionDTO();
        quizQuestion.setId(quizQuestionDTO.getId());

        quizQuestionDTO.getQuizAnswers().forEach(quizAnswer -> quizAnswer.setQuizQuestion(quizQuestion));
        quizAnswerService.insertOrUpdate(quizQuestionDTO.getQuizAnswers());
    }

    private QuizQuestionDTO tupleToQuizQuestionDTO(Tuple tuple) {
        QuizQuestionDTO quizQuestion = new QuizQuestionDTO();
        quizQuestion.setId((Long) tuple.get("id"));
        quizQuestion.setQuestion((String) tuple.get("question"));
        quizQuestion.setQuestionTitle((String) tuple.get("questionTitle"));
        quizQuestion.setQuizAnswers(quizAnswerService.getAllQuizAnswerByQuestionId(quizQuestion.getId()));
        QuizAnswerDTO quizAnswer = new QuizAnswerDTO();
        quizAnswer.setId((Long) tuple.get("correctAnswerId"));
        quizQuestion.setCorrectAnswer(quizAnswer);
        return quizQuestion;
    }
}
