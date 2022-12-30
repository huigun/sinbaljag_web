package com.huigwon.sinbaljang.service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Service;

import com.huigwon.sinbaljang.controller.Alert;
import com.huigwon.sinbaljang.entity.Basket;
import com.huigwon.sinbaljang.entity.Product;
import com.huigwon.sinbaljang.repository.BasketReposutory;
import com.huigwon.sinbaljang.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class BasketService {
	
	private final BasketReposutory basketReposutory;
	private final OrderRepository orderRepository;
	
	public int addBasket(String pnum,String buname) {
			List<Basket> Basket = basketReposutory.findByBpnum(pnum);
			if(Basket.size()>0) {
				return 1; 
			} else {
				Optional<Product> optProduct = orderRepository.findById(Integer.parseInt(pnum));
				Product product = optProduct.get(); // 제품 정보를 받아옴
				
				Basket basket = new Basket(); // 새로운 장바구니를 생성
				basket.setBpname(product.getPname()); // 제품명 입력
				basket.setBpnum(Integer.toString(product.getPnum())); // 제품번호 입력
				basket.setBuname(buname); // 장바구니 주인
				System.out.println("1");
				basketReposutory.save(basket);
				System.out.println("2");
				return 2;
			}
		}
	
	public List<Basket> showBasket( ) {
		List<Basket> basket = basketReposutory.findAll();
		
		return basket;
		
	}
}
