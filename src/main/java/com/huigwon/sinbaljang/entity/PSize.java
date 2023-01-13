package com.huigwon.sinbaljang.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "psizetbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PSize {
	
	@Id
	@Column(name = "msize")
	private int msize;
	
	@Column(name = "wsize")
	private int wsize;
}
