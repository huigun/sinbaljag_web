package com.huigwon.sinbaljang.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.huigwon.sinbaljang.entity.SMember;
import com.huigwon.sinbaljang.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
		private final PasswordEncoder passwordEncoder;
		private final MemberRepository memberRepository;
		
	public SMember memberCreate(String smid, String smpw, String smname) {
	SMember member = new SMember();
	
	member.setSmid(smid);
	member.setSmpw(passwordEncoder.encode(smpw));
	member.setSmname(smname);
	
	memberRepository.save(member);
	
	return member;
	
	}
}
