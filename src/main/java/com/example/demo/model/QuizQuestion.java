package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "quiz_question")
public class QuizQuestion extends BaseModel {
    private String questionTitle;
    private String question;
    private int correctAnswerId;
    private Long quizId;
}
