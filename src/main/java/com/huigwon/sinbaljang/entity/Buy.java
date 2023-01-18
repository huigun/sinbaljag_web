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
@SequenceGenerator(
		name = "buy_SEQ_GENERATOR", //시퀀스 제너레이터 이름
		sequenceName = "buy_SEQ", //시퀀스 이름
		initialValue=1, //시작값
		allocationSize=1 //메모리를 통해 할당할 범위 사이즈
		)            
@Table(name = "buytbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buy {
	
	
	@Column(name = "byuname")
	private String byuname;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buy_SEQ_GENERATOR")
	@Column(name = "bynum")
	private int bynum;
	
	@Column(name = "bypname")
	private String bypname;
	
	@Column(name = "bypimage")
	private String bypimage;
	
	@Column(name = "bypprice")
	private int bypprice;
	
	@Column(name = "bycount")
	private int bycount;
	
	@Column(name = "bypsize")
	private String bypsize;
}
