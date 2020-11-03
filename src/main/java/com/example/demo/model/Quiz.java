package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "quiz")
public class Quiz extends BaseModel {
	private String quizTitle;
}
