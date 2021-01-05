package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user_wish_list")
public class UserWishList extends BaseModel {

	private Long userId;

	private Long courseId;
}
