package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "instructor_detail")
public class InstructorDetail extends BaseModel {
	private String aboutMe;
	private int totalStudents;
	private int reviewCount;
	private String imageLink;
	private String facebookLink;
	private String twitterLink;
	private String youtubeLink;
	private int numberCourses;
	private float ratings;
	private String achievement;
}
