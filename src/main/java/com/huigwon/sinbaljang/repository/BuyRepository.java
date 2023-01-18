package com.huigwon.sinbaljang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.huigwon.sinbaljang.entity.Buy;

public interface BuyRepository extends JpaRepository<Buy, Integer>{
	public List<Buy> findByByunameAndBypnameAndBypsize(String buname,String pname, String psize);
	public List<Buy> findByByunameOrderByBynumAsc(String buname);
	
	@Transactional 
	public void deleteByByunameAndBypnameAndBypsize(String buname,String bpname, String bpsize);
}
