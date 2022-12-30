package com.huigwon.sinbaljang.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "smember")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SMember {
	
	@Id
	@Column(name = "smid")
	private String smid;
	
	@Column(name = "smpw")
	private String smpw;
	
	@Column(name = "smname")
	private String smname;
	
}
