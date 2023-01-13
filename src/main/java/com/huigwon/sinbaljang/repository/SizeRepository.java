package com.huigwon.sinbaljang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huigwon.sinbaljang.entity.PSize;

public interface SizeRepository extends JpaRepository<PSize, Integer>{

	public List<PSize> findAll();
}
