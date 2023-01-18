package com.huigwon.sinbaljang.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NoticeForm {
	@Size(max = 100,message = "제목은 최대 100자입니다.")
	@NotEmpty(message = "제목은 필수 입력사항 입니다!")
	private String nttitle;
	
	@Size(max = 1500,message = "내용은 최대 1500자입니다.")
	@NotEmpty(message = "내용은 필수 입력사항 입니다!")
	private String ntcontent;
	
}
