package com.huigwon.sinbaljang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huigwon.sinbaljang.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	List<Order> findByOmidOrderByOdateDesc(String omid);

}
