package com.huigwon.sinbaljang.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ItemForm {
	
	@NotEmpty(message = "제품명은 필수 입력사항 입니다!")
	private String pname;
	
	@NotEmpty(message = "가격은 필수 입력사항 입니다!")
	private String pprice;
	
	@NotEmpty(message = "성별은 필수 입력사항 입니다!")
	private String pgender;
	
	//@NotEmpty(message = "이미지는 필수 입력사항 입니다!")
	private String pimage;
}
