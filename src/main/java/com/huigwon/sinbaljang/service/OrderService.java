package com.huigwon.sinbaljang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.huigwon.sinbaljang.entity.Product;
import com.huigwon.sinbaljang.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	public Page<Product> findPAll(int page) {
		
		List<Sort.Order> sort = new ArrayList<>();
		
		sort.add(Sort.Order.desc("pnum"));
		
		Pageable pageable = PageRequest.of(page, 8, Sort.by(sort));//페이지당 표시되는 글 개수
		
		Page<Product> orders = orderRepository.findAll(pageable);
		return orders;
	}
	
	public List<Product> findPAll() {
		List<Product> plist = orderRepository.findAll();
		
		return plist;
	}
	
	public Product productView(int pnum) {
		
		Optional<Product> optOrder = orderRepository.findById(pnum);
		
		Product order = optOrder.get();
		
		return order;
	}
	
}
