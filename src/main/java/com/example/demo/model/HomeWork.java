package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HomeWork extends BaseModel {

	private String file;
	private String description;
	private Date toDate;

}
