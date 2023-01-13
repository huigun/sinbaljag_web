package com.huigwon.sinbaljang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huigwon.sinbaljang.entity.PList;

public interface PListRepository extends JpaRepository<PList, Integer>{
	public List<PList> findByPname(String pname);
}
