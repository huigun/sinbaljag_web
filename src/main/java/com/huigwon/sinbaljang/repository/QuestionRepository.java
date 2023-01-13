package com.huigwon.sinbaljang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huigwon.sinbaljang.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	public List<Question> findByQpnameOrderByQdateDesc(String qpname);
	
}
