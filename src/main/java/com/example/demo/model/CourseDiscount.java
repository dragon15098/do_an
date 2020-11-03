package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseDiscount extends BaseModel {

	private String code;
	private int discountPercent;
	private Date fromDate;
	private Date toDate;
	private Course course;

}
