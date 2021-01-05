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
	private Long userId;

	private Long courseId;
}
