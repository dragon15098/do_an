package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDetailDTO extends BaseDTO {
    private String school;
    private Integer grade;
}
