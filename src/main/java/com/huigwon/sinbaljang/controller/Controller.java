package com.huigwon.sinbaljang.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huigwon.sinbaljang.dto.ItemForm;
import com.huigwon.sinbaljang.dto.MemberForm;
import com.huigwon.sinbaljang.entity.Basket;
import com.huigwon.sinbaljang.entity.PList;
import com.huigwon.sinbaljang.entity.Product;
import com.huigwon.sinbaljang.entity.Question;
import com.huigwon.sinbaljang.entity.Review;
import com.huigwon.sinbaljang.entity.SMember;
import com.huigwon.sinbaljang.repository.ReviewRepository;
import com.huigwon.sinbaljang.service.BasketService;
import com.huigwon.sinbaljang.service.MemberService;
import com.huigwon.sinbaljang.service.OrderService;
import com.huigwon.sinbaljang.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class Controller {
	
	private final OrderService orderService;
	private final BasketService basketService;
	private final MemberService memberService;
	private final ReviewService reviewService;
	public int iflag=0;
	public int flag=0;
	public int bflag=0;
	public int ri=0;
	public int aflag=0;
	public String ipname="";
	public String ipprice="";
	public String ipgender="";
	int pflag =0;
	@RequestMapping(value= "")
	public String index(Model model,Principal principal) {
		
		return "index";
	}
	
	@RequestMapping(value= "/home")
	public String home(Model model) {
		
		
		return "index";
	}
	
	@RequestMapping(value= "/myPage")
	public String myPage() {
		
		
		return "myPage";
	}
	
	@RequestMapping(value= "/addItem")
	public String addItem(Model model){
		model.addAttribute("pflag", pflag);
		model.addAttribute("ipgender", ipgender);
		model.addAttribute("ipname", ipname);
		model.addAttribute("ipprice", ipprice);
		pflag=0;
		ipprice="";
		ipname="";
		ipgender="";
		return "addItem";
	}
	
	@RequestMapping(value= "/itemInsert")
	public String itemInsert(Model model, PList plist,HttpServletRequest request,MultipartFile pimagefile)throws Exception {
		String pname = request.getParameter("pname");
		String referer = request.getHeader("Referer");
		String pgender = request.getParameter("pgender");
		String pprice = request.getParameter("pprice");
		if(!orderService.productView(pname).isEmpty()) {
			if(orderService.productView(pname).get(0).getPname().equals(pname)) {
				ipname = request.getParameter("pname");
				ipprice = request.getParameter("pprice");
				ipgender = request.getParameter("pgender");
				pflag = 1;
				return "redirect:"+referer;
			} else {
			orderService.saveItem(plist, pimagefile, pname, pgender, pprice);
			}
		} else {
			orderService.saveItem(plist, pimagefile, pname, pgender, pprice);
		}
		return "redirect:list";
	}
	
	
	@RequestMapping(value= "list")
	public String list(Model model,@RequestParam(value = "page", defaultValue = "0")int page,Principal principal) {
		ri=0;
		Page<PList> orders = orderService.findPAll(page);
		List<PList> orders2 = orderService.findPAll();
		if(principal==null) {
			model.addAttribute("loginId", "guest");
		} else { 
			model.addAttribute("loginId", principal.getName());
		}
		model.addAttribute("pList2", orders2);
		model.addAttribute("pList", orders);
		model.addAttribute("page", page);
		return "list";
	}
	
	@RequestMapping(value= "pView/{pname}")
	public String pView(Model model,@PathVariable("pname") String pname,@RequestParam(value = "page", defaultValue = "0")int page, HttpServletRequest request,Principal principal) {
		PList order = orderService.productView(pname).get(0);
		double ar = 0;
		List<Integer> psize = orderService.PSize(pname);
		List<Review> reviews = reviewService.Preview(pname);
		List<Question> questions = reviewService.PQuestion(pname);
		
		if(reviews.size()!=0) {
			for(int i=0;i<reviews.size();i++) {
				String rating="";
				switch (Integer.parseInt(reviews.get(i).getRrating())) {
				case 0: rating = "☆☆☆☆☆";
						break;
	            case 1:  rating = "★☆☆☆☆";
	                    break;
	            case 2:  rating = "★★☆☆☆";
	                    break;
	            case 3:  rating = "★★★☆☆";
	                    break;
	            case 4:  rating = "★★★★☆";
	                    break;
	            case 5:  rating = "★★★★★";
	                    break;
	        }
				int rate = Integer.parseInt(reviews.get(i).getRrating());
				ar = ar + rate;
				reviews.get(i).setRrating(rating);
				
			}ar = ar/reviews.size();
			ar = Math.round(ar*10)/10.0;
		} else if(reviews.size()==0) {
			ar=0;
		}
		if(principal==null) {
			model.addAttribute("loginId", "guest");
		} else { 
			model.addAttribute("loginId", principal.getName());
		}
		model.addAttribute("ar", ar);
		model.addAttribute("reviews", reviews);
		model.addAttribute("questions", questions);
		model.addAttribute("pd", order);
		model.addAttribute("flag", flag);
		model.addAttribute("psize", psize);
		model.addAttribute("ri", ri);
		model.addAttribute("iflag", iflag);
		iflag=0;
		flag=0;
		
		return "pView";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value= "basket")
	public String basket(Model model,Principal principal) {
		List<Basket> baskets = basketService.showBasket(principal.getName());
		model.addAttribute("baskets", baskets);
		model.addAttribute("bflag", bflag);
		bflag=0;
			return "basket";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value= "pView/basket")
	public String basket2(Model model,Principal principal) {
		List<Basket> baskets = basketService.showBasket(principal.getName());
		model.addAttribute("baskets", baskets);
		model.addAttribute("bflag", bflag);
		bflag=0;
			return "basket";
	}
	
	@RequestMapping(value= "board")
	public String board(Model model) {
		
		
		return "board";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value= "addBasket")
	public String addBasket(Model model,HttpServletRequest request,Principal principal){
		String referer = request.getHeader("Referer");
		String pname = request.getParameter("pname");
		String psize = request.getParameter("psize");
		if(psize.equals("")) {
			flag =3;
		} else {
		flag = basketService.addBasket(pname,principal.getName(),psize);
		// flag 1 = 이미있음, 2 = 장바구니 등록 성공 , 3 = 사이즈 선택안함
		List<PList> order = orderService.productView(pname);
		model.addAttribute("pd", order);
		model.addAttribute("flag", flag);
		}
		return "redirect:"+referer;
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value= "/buyItem")
	public String buyItem(HttpServletRequest request,Principal principal, Model model){
		String pname = request.getParameter("pname");
		String psize = request.getParameter("psize");
		if(psize.equals("")) {
			String referer = request.getHeader("Referer");
			flag =3;
			model.addAttribute("flag", flag);
			return "redirect:"+referer;
		} else {
		basketService.addBasket(pname,principal.getName(),psize);
		}
		return "redirect:basket";
	}
	
	@RequestMapping(value="memberJoin")
	public String memberJoin(MemberForm memberForm) {
		
		return "memberJoin";
	}
	
	@RequestMapping(value="memberJoinOk")
	public String memberJoinOk(@Valid MemberForm memberForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "memberJoin";
		}
		
		try {
			memberService.memberCreate(memberForm.getUsername(), memberForm.getPassword(), memberForm.getName(), memberForm.getAddress(), memberForm.getTel());
		}catch(Exception e){
			e.printStackTrace();
			bindingResult.reject("joinFail", "이미 등록된 아이디입니다.");
			return "memberJoin";
		}
		
		return "index";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		return "memberLogin";
	}
	
	@RequestMapping(value = "/basketDel/{bnum}")
	public String basketDel(@PathVariable("bnum") Integer bnum,HttpServletRequest request) {
		basketService.deleteBasket(bnum);
		String referer = request.getHeader("Referer");
		return "redirect:"+referer;
	}
	
	@RequestMapping(value = "/questionDel/{qnum}")
	public String questionDel(@PathVariable("qnum") Integer qnum,HttpServletRequest request) {
		reviewService.questionDelete(qnum);
		String referer = request.getHeader("Referer");
		return "redirect:"+referer;
	}
	
	@RequestMapping(value = "/reviewDel/{rnum}")
	public String reviewDel(@PathVariable("rnum") Integer rnum,HttpServletRequest request) {
		reviewService.reviewDelete(rnum);
		String referer = request.getHeader("Referer");
		return "redirect:"+referer;
	}
	
	@RequestMapping(value = "/buy")
	public String buy(HttpServletRequest request,Principal principal,Model model) {
		String bindex = request.getParameter("bindex");
		String referer = request.getHeader("Referer");
		if(!bindex.equals("")) {
			List<Basket> baskets = basketService.showBasket(principal.getName());
			String mname = principal.getName();
			SMember member = memberService.memberView(mname);
			
			List<String> index = new ArrayList<>();
			int bind = bindex.indexOf(",");
			
			if(bind==-1) {
				index.add(bindex);
			} else {
				while(true){
				index.add(bindex.substring(0, bind));
				bindex = bindex.substring(bind+1);
				bind = bindex.indexOf(",");
					if(bind==-1) {
						index.add(bindex);
						break;
					}
				}
			}
			for(int i=0;i<index.size();i++) {
				int idx = Integer.parseInt(index.get(i));
			orderService.BuyItem(member.getSmaddress(), mname, member.getSmtel(), baskets.get(idx).getBpname(), Integer.parseInt(baskets.get(idx).getBpsize()));
			basketService.BDeleteBasket(mname, baskets.get(idx).getBpname(),baskets.get(idx).getBpsize());
			}
			bflag = 2;
			model.addAttribute("bflag", bflag);
		} else if(bindex.equals("")) {
			bflag = 1;
			model.addAttribute("bflag", bflag);
		}
		return "redirect:"+referer;
	}
	
	@RequestMapping(value = "/review")
	public String review(HttpServletRequest request) {
		ri=0;
		String referer = request.getHeader("Referer");
		return "redirect:"+referer;
	}
	
	@RequestMapping(value = "/inquiry")
	public String inquiry(HttpServletRequest request,Model model) {
		String referer = request.getHeader("Referer");
		ri=1;
		return "redirect:"+referer;
	}
	
	@RequestMapping(value = "/inquiryAdd")
	public String inquiryAdd(HttpServletRequest request,Model model, Principal principal) {
		String referer = request.getHeader("Referer");
		String inquiry = request.getParameter("inquiry");
		String pname = request.getParameter("pname");
		if(inquiry.equals("")) {
			iflag = 1;
		} else {
		reviewService.questionInsert(principal.getName(), inquiry, pname);
		}
		return "redirect:"+referer;
	}
	
	@RequestMapping(value = "/answerAdd")
	public String answerAdd(HttpServletRequest request,Model model, Principal principal) {
		String referer = request.getHeader("Referer");
		String answer = request.getParameter("answer");
		String qnum = request.getParameter("qnum");
		if(answer.equals("")) {
			aflag = 1;
		} else {
		reviewService.answerInsert(Integer.parseInt(qnum), answer);
		}
		return "redirect:"+referer;
	}
}

