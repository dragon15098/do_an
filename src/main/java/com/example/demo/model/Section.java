package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "section")
public class Section extends BaseModel {
    private String sectionTitle;
    private int length;
    private Long quizId;
    private Long courseId;
}
