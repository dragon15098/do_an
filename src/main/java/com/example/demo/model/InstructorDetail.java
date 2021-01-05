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
	private String facebookLink;
	private String twitterLink;
	private String youtubeLink;
	private String achievement;
}
