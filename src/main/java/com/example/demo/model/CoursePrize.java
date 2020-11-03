package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CoursePrize extends BaseModel {

	private Float price;
	private Date fromDate;
	private Date toDate;
	private Currency currency;
	private Course course;

}
