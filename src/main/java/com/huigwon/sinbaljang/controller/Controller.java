package com.huigwon.sinbaljang.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huigwon.sinbaljang.dto.MemberForm;
import com.huigwon.sinbaljang.entity.Basket;
import com.huigwon.sinbaljang.entity.Product;
import com.huigwon.sinbaljang.repository.OrderRepository;
import com.huigwon.sinbaljang.service.BasketService;
import com.huigwon.sinbaljang.service.MemberService;
import com.huigwon.sinbaljang.service.OrderService;

import groovyjarjarasm.asm.commons.Method;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class Controller {
	
	private final OrderService orderService;
	private final BasketService basketService;
	private final MemberService memberService;
	public int flag=0;
	
	@RequestMapping(value= "")
	public String index(Model model,Principal principal) {
		
		return "index";
	}
	
	@RequestMapping(value= "/home")
	public String home(Model model) {
		
		
		return "index";
	}
	
	@RequestMapping(value= "list")
	public String list(Model model,@RequestParam(value = "page", defaultValue = "0")int page) {
		
		Page<Product> orders = orderService.findPAll(page);
		
		model.addAttribute("products", orders);
		model.addAttribute("page", page);
		return "list";
	}
	
	@RequestMapping(value= "pView/{pnum}")
	public String pView(Model model,@PathVariable("pnum") Integer pnum,@RequestParam(value = "page", defaultValue = "0")int page, HttpServletRequest request,Principal principal) {
		Product order = orderService.productView(pnum);
		
		model.addAttribute("pd", order);
		model.addAttribute("flag", flag);
		flag=0;
		return "pView";
	}
	
	@RequestMapping(value= "basket")
	public String basket(Model model) {
		List<Basket> baskets = basketService.showBasket();
		model.addAttribute("baskets", baskets);
		
		return "basket";
	}
	
	@RequestMapping(value= "board")
	public String board(Model model) {
		
		
		return "board";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value= "addBasket/{pnum}", method=RequestMethod.GET)
	public String addBasket(Model model,@PathVariable("pnum") Integer pnum,HttpServletRequest request,Principal principal){
		String referer = request.getHeader("Referer");
		flag = basketService.addBasket(Integer.toString(pnum),principal.getName());
		// flag 1 = 이미있음, 2 = 장바구니 등록 성공
		Product order = orderService.productView(pnum);
		model.addAttribute("pd", order);
		model.addAttribute("flag", flag);
		
		return "redirect:"+referer;
	}
	
	@RequestMapping(value="memberJoin")
	public String memberJoin(MemberForm memberForm) {
		
		return "memberJoin";
	}
	
	@RequestMapping(value="memberJoinOk")
	public String memberJoinOk(@Valid MemberForm memberForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "join_form";
		}
		
		try {
			memberService.memberCreate(memberForm.getUsername(), memberForm.getPassword(), memberForm.getEmail());
		}catch(Exception e){
			e.printStackTrace();
			bindingResult.reject("joinFail", "이미 등록된 아이디입니다.");
			return "join_form";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		return "memberLogin";
	}
	
}

