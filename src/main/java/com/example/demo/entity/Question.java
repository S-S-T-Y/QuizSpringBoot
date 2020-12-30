package com.example.demo.entity;

import lombok.Data;

@Data
public class Question {

	private int id;
	private String question;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private int answer;
	private String description;
	private boolean answerd;
	private boolean misstake;

}
