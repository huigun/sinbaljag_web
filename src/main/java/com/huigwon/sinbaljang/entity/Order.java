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
@Table(name = "ordertbl")
@SequenceGenerator(
		name = "order_SEQ_GENERATOR", //시퀀스 제너레이터 이름
		sequenceName = "order_SEQ", //시퀀스 이름
		initialValue=1, //시작값
		allocationSize=1 //메모리를 통해 할당할 범위 사이즈
		)     
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_SEQ_GENERATOR")
	@Column(name = "onum")
	private int onum;
	
	@Column(name = "ocount")
	private int ocount;
	
	@Column(name = "osize")
	private int osize;
	
	@Column(name = "ocon")
	private int ocon;
	
	@Column(name = "omname")
	private String omname;
	
	@Column(name = "odate")
	private String odate;
	
	@Column(name = "omtel")
	private String omtel;
	
	@Column(name = "opname")
	private String opname;
	
	@Column(name = "omaddress")
	private String omaddress;
	
	@Column(name = "oimage")
	private String oimage;
	
	@Column(name = "opprice")
	private String opprice;
	
	@Column(name = "orcon")
	private String orcon;
	
	@Column(name = "omid")
	private String omid;
}
