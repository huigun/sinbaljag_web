package com.huigwon.sinbaljang.service;

import java.util.Optional;

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
		
	public SMember memberCreate(String smid, String smpw, String smname, String smaddress, String smtel) {
	SMember member = new SMember();
	
	member.setSmid(smid);
	member.setSmpw(passwordEncoder.encode(smpw));
	member.setSmname(smname);
	member.setSmaddress(smaddress);
	member.setSmtel(smtel);
	
	memberRepository.save(member);
	
	return member;
	
	}
	
	public SMember memberView(String mname) {
		Optional<SMember> members = memberRepository.findById(mname);
		SMember member = members.get();
		
		return member;
	}
	
	public SMember memberUpdate(String sid, String smpw, String smname, String smaddress, String smtel) {
		Optional<SMember> members = memberRepository.findById(sid);
		SMember member = members.get();
		
		member.setSmpw(passwordEncoder.encode(smpw));
		member.setSmname(smname);
		member.setSmaddress(smaddress);
		member.setSmtel(smtel);
		
		memberRepository.save(member);
		
		return member;
		
		}
}
