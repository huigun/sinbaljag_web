package com.huigwon.sinbaljang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huigwon.sinbaljang.entity.SMember;

public interface MemberRepository extends JpaRepository<SMember, String>{

}
