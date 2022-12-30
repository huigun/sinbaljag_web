package com.huigwon.sinbaljang.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producttbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@Column(name = "pnum")
	private int pnum;
	
	@Column(name = "pname")
	private String pname;
	
	@Column(name = "pprice")
	private int pprice;
	
	@Column(name = "psize")
	private int psize;
	
	@Column(name = "pimage")
	private String pimage;
}
