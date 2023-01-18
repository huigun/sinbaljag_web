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
@Table(name = "reviewtbl")
@SequenceGenerator(
		name = "review_SEQ_GENERATOR", //시퀀스 제너레이터 이름
		sequenceName = "review_SEQ", //시퀀스 이름
		initialValue=1, //시작값
		allocationSize=1 //메모리를 통해 할당할 범위 사이즈
		)     
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_SEQ_GENERATOR")
	@Column(name = "rnum")
	private int rnum;
	
	@Column(name = "rrating")
	private String rrating;
	
	@Column(name = "rpname")
	private String rpname;
	
	@Column(name = "rmid")
	private String rmid;
	
	@Column(name = "rdate")
	private String rdate;
	
	@Column(name = "rcontent")
	private String rcontent;
	
	@Column(name = "onum")
	private String onum;
}
