package com.huigwon.sinbaljang.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.huigwon.sinbaljang.entity.Basket;
import com.huigwon.sinbaljang.entity.PList;
import com.huigwon.sinbaljang.entity.Product;
import com.huigwon.sinbaljang.repository.BasketRepository;
import com.huigwon.sinbaljang.repository.OrderRepository;
import com.huigwon.sinbaljang.repository.PListRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class BasketService {
	
	private final BasketRepository basketReposutory;
	private final PListRepository pListRepository;
	
	public int addBasket(String pname,String buname,String pSize,String pimagepath) {
			List<Basket> Basket = basketReposutory.findByBunameAndBpnameAndBpsize(buname, pname, pSize);
			if(Basket.size()>0) {
				return 1; 
			} else if(Basket.size()==0) {
				List<PList> pList = pListRepository.findByPname(pname);
				Basket basket = new Basket(); // 새로운 장바구니를 생성
				// bnum 은 시퀀스로 자동생성.
				basket.setBpnum(Integer.toString(pList.get(0).getPnum())); // 제품번호 입력
				basket.setBpname(pList.get(0).getPname()); // 제품명 입력
				basket.setBuname(buname); // 장바구니 주인
				basket.setBpimage(pimagepath); // 제품 이미지
				basket.setBpprice(pList.get(0).getPprice()); // 제품 가격
				basket.setBcount(1); // 추가 수량
				basket.setBpsize(pSize);
				basketReposutory.save(basket);
				return 2;
			}
			return 0;
		}
	
	public List<Basket> showBasket(String buname) {
		List<Basket> basket = basketReposutory.findByBunameOrderByBnumAsc(buname);
		return basket;
		
	}
	
	public void deleteBasket(int bnum) {
		basketReposutory.deleteById(bnum);
	}
	public void BDeleteBasket(String buname, String bpname, String bpsize) {
		basketReposutory.deleteByBunameAndBpnameAndBpsize(buname, bpname, bpsize);
	}
}
