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
@Table(name = "noticetbl")
@SequenceGenerator(
		name = "notice_SEQ_GENERATOR", //시퀀스 제너레이터 이름
		sequenceName = "notice_SEQ", //시퀀스 이름
		initialValue=1, //시작값
		allocationSize=1 //메모리를 통해 할당할 범위 사이즈
		)     
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_SEQ_GENERATOR")
	@Column(name = "ntnum")
	private int ntnum;
	
	@Column(name = "nthit")
	private int nthit;
	
	@Column(name = "nttitle")
	private String nttitle;
	
	@Column(name = "ntcontent")
	private String ntcontent;
	
	@Column(name = "ntdate")
	private String ntdate;
	
	@Column(name = "ntwriter")
	private String ntwriter;
}
