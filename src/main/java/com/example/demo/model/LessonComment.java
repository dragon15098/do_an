package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonComment extends BaseModel {

	private String comment;
	private User user;

}
