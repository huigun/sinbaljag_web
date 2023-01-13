package com.huigwon.sinbaljang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.huigwon.sinbaljang.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Integer>{
	public List<Basket> findByBunameAndBpnameAndBpsize(String buname,String pname, String psize);
	public List<Basket> findByBunameOrderByBnumAsc(String buname);
	
	@Transactional 
	public void deleteByBunameAndBpnameAndBpsize(String buname,String bpname, String bpsize);
}
