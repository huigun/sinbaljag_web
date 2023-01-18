package com.huigwon.sinbaljang.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class MemberForm2 {
	
	@NotEmpty(message = "아이디는 필수입력사항입니다!")
	@Size(min = 3, message = "아이디는 3자 이상입니다.")
	private String username;
	
	@NotEmpty(message = "비밀번호는 필수입력사항입니다!")
	@Size(min = 3, message = "비밀번호는 3자 이상입니다.")	
	private String password;
	
	@NotEmpty(message = "이름은 필수입력사항입니다!")	
	private String name;
	
	@NotEmpty(message = "주소는 필수입력사항입니다!")	
	private String address;
	
	@NotEmpty(message = "전화번호는 필수입력사항입니다!")	
	private String tel;
}
