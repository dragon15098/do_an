package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_cart")
public class UserCart extends BaseModel {

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties({"roles"})
	private User user;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@JsonIgnoreProperties({"lessons", "sections", "instructor"})
	private Course course;
}
