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
@Table(name = "plisttbl")
@SequenceGenerator(
		name = "plist_SEQ_GENERATOR", //시퀀스 제너레이터 이름
		sequenceName = "plist_SEQ", //시퀀스 이름
		initialValue=1, //시작값
		allocationSize=1 //메모리를 통해 할당할 범위 사이즈
		)     
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PList {
	
	
	@Column(name = "pname")
	private String pname;
	
	@Column(name = "pgender")
	private String pgender;
	
	@Column(name = "pprice")
	private int pprice;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plist_SEQ_GENERATOR")
	@Column(name = "pnum")
	private int pnum;
	
	@Column(name = "pimage")
	private String pimage;
	
	@Column(name = "pimagepath")
	private String pimagepath;
}
