package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "student_detail")
public class StudentDetail extends BaseModel {
	private String school;
	private String grade;
}
