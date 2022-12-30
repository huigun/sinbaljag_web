package com.huigwon.sinbaljang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huigwon.sinbaljang.entity.Basket;

public interface BasketReposutory extends JpaRepository<Basket, Integer>{
	public List<Basket> findByBpnum(String pnum);
	
}
