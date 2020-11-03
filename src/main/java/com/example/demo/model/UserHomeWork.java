package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserHomeWork extends BaseModel {

	private String file;
	private Date submitTime;
	private float point;
	private User student;

}
