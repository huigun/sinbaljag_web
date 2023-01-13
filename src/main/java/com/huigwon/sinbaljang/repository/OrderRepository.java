package com.huigwon.sinbaljang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huigwon.sinbaljang.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
