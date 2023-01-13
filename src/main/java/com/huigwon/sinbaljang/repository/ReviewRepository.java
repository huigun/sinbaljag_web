package com.huigwon.sinbaljang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huigwon.sinbaljang.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

	public List<Review> findByRpname(String rpname);

}
