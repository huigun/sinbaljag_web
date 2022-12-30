package com.huigwon.sinbaljang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huigwon.sinbaljang.entity.Product;

public interface OrderRepository extends JpaRepository<Product, Integer>{

}
