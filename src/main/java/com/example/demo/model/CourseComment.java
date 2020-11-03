package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseComment extends BaseModel {

	private User user;
	private String comment;

}
