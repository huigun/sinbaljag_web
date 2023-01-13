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
		name = "basket_SEQ_GENERATOR", //시퀀스 제너레이터 이름
		sequenceName = "basket_SEQ", //시퀀스 이름
		initialValue=1, //시작값
		allocationSize=1 //메모리를 통해 할당할 범위 사이즈
		)            
@Table(name = "baskettbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
	
	
	@Column(name = "buname")
	private String buname;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_SEQ_GENERATOR")
	@Column(name = "bnum")
	private int bnum;
	
	@Column(name = "bpnum")
	private String bpnum;
	
	@Column(name = "bpname")
	private String bpname;
	
	@Column(name = "bpimage")
	private String bpimage;
	
	@Column(name = "bpprice")
	private int bpprice;
	
	@Column(name = "bcount")
	private int bcount;
	
	@Column(name = "bpsize")
	private String bpsize;
}
