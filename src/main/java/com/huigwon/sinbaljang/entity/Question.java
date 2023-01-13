package com.huigwon.sinbaljang.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questiontbl")
@SequenceGenerator(
		name = "question_SEQ_GENERATOR", //시퀀스 제너레이터 이름
		sequenceName = "question_SEQ", //시퀀스 이름
		initialValue=1, //시작값
		allocationSize=1 //메모리를 통해 할당할 범위 사이즈
		)     
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_SEQ_GENERATOR")
	@Column(name = "qnum")
	private int qnum;
	
	@Column(name = "qmname")
	private String qmname;
	
	@Column(name = "qcontent")
	private String qcontent;
	
	@Column(name = "qdate")
	private String qdate;
	
	@Column(name = "qpname")
	private String qpname;
	
	@Column(name = "qanswer")
	private String qanswer;
	
	@Column(name = "adate")
	private String adate;
}
