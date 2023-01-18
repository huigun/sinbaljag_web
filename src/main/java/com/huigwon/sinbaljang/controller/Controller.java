package com.huigwon.sinbaljang.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.huigwon.sinbaljang.dto.MemberForm2;
import com.huigwon.sinbaljang.dto.NoticeForm;
import com.huigwon.sinbaljang.entity.Basket;
import com.huigwon.sinbaljang.entity.Buy;
import com.huigwon.sinbaljang.entity.Notice;
import com.huigwon.sinbaljang.entity.Order;
import com.huigwon.sinbaljang.entity.PList;
import com.huigwon.sinbaljang.entity.Product;
import com.huigwon.sinbaljang.entity.Question;
import com.huigwon.sinbaljang.entity.Review;
import com.huigwon.sinbaljang.entity.SMember;
import com.huigwon.sinbaljang.repository.BuyRepository;
import com.huigwon.sinbaljang.repository.MemberRepository;
import com.huigwon.sinbaljang.repository.NoticeRepository;
import com.huigwon.sinbaljang.repository.ReviewRepository;
import com.huigwon.sinbaljang.service.BasketService;
import com.huigwon.sinbaljang.service.MemberService;
import com.huigwon.sinbaljang.service.NoticeService;
import com.huigwon.sinbaljang.service.OrderService;
import com.huigwon.sinbaljang.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class Controller {
	private final BuyRepository buyRepository;
	private final NoticeRepository noticeRepository;
	private final OrderService orderService;
	private final BasketService basketService;
	private final MemberService memberService;
	private final ReviewService reviewService;
	private final MemberRepository memberRepository;
	private final NoticeService noticeService;
	public int iflag=0;
	public int flag=0;
	public int rvflag=0;
	public int bflag=0;
	public int ri=0;
	public int aflag=0;
	public int uflag=0;
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
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value= "mypage")
	public String mypage(Model model,Principal principal) {
		SMember member = memberService.memberView(principal.getName());
		List<Order> order = orderService.ShowOrder(principal.getName());
		model.addAttribute("orders", order);
		model.addAttribute("rvflag", rvflag);
		rvflag=0;
			return "myPage";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value= "myPagehome")
	public String myPagehome() {
			return "myPagehome";
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
	
	@RequestMapping(value= "reviewInsert")
	public String reviewInsert(Model model,HttpServletRequest request,Principal principal) {
		String referer = request.getHeader("Referer");
		String pname = request.getParameter("pname");
		String rcontent = request.getParameter("rcontent");
		String prate = request.getParameter("prate");
		if(prate==null) {
			prate="0";
		}
		if(rcontent.equals("")) {
			rcontent=" ";
		}
		String odnum = request.getParameter("odnum");
		String onum = request.getParameter("onum");
		reviewService.reviewInsert(principal.getName(), prate, rcontent, pname, odnum, onum);
		rvflag=1;
		return "redirect:"+referer;
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
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value= "buypage")
	public String buypage(Model model,Principal principal,HttpServletRequest request) {
		//
		String referer = request.getHeader("Referer");
		buyRepository.deleteAll();
		String mname = principal.getName();
		SMember member = memberService.memberView(mname);
		String bindex = request.getParameter("bindex");
		if(!bindex.equals("")) {
			List<Basket> baskets = basketService.showBasket(principal.getName());
			
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
				Buy buy = new Buy();
				buy.setBypname(baskets.get(idx).getBpname());
				buy.setBypimage(baskets.get(idx).getBpimage());
				buy.setBypprice(baskets.get(idx).getBpprice());
				buy.setBycount(1);
				buy.setBypsize(baskets.get(idx).getBpsize());
				buy.setByuname(mname);
				buyRepository.save(buy);
			}
		} else {
			bflag=1;
			return "redirect:"+referer;
		}
				List<Buy> buys = buyRepository.findAll();
				model.addAttribute("buy", buys);
				model.addAttribute("member", member);
	      
		//
			return "buypage";
	}
	
	@RequestMapping(value= "notice")
	public String notice(Model model,Principal principal,@RequestParam(value = "page", defaultValue = "0")int page) {
		Page<Notice> notices = noticeService.ntAll(page);
		List<Notice> notices2 = noticeService.findntAll();
		if(principal==null) {
			model.addAttribute("loginId", "guest");
		} else { 
			model.addAttribute("loginId", principal.getName());
		}
		model.addAttribute("notice", notices);
		model.addAttribute("notice2", notices2);
		model.addAttribute("page", page);
		return "notice";
	}
	
	@RequestMapping(value= "ntwrite")
	public String ntwrite(Model model,Principal principal,NoticeForm noticeForm) {
		String loginId = principal.getName();
		model.addAttribute("loginId", loginId);
		model.addAttribute("noticeForm", noticeForm);
		return "ntwrite";
	}
	
	@RequestMapping(value= "ntwriteOk")
	public String ntwriteOk(HttpServletRequest request,@Valid NoticeForm noticeForm, BindingResult bindingResult) {
		String nttitle = request.getParameter("nttitle");
		String ntcontent = request.getParameter("ntcontent");
		
		if(bindingResult.hasErrors()) {
			return "ntwrite";
		}
		
		try {
			noticeService.ntinsert(nttitle, ntcontent);
		}catch(Exception e){
			e.printStackTrace();
			bindingResult.reject("insertFail", "등록에 실패 했습니다.");
			return "ntwrite";
		}
		
		return "redirect:notice";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value= "addBasket")
	public String addBasket(Model model,HttpServletRequest request,Principal principal){
		String referer = request.getHeader("Referer");
		String pname = request.getParameter("pname");
		String psize = request.getParameter("psize");
		String pimagepath = request.getParameter("pimagepath");
		if(psize.equals("")) {
			flag =3;
		} else {
		flag = basketService.addBasket(pname,principal.getName(),psize,pimagepath);
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
		String pimagepath = request.getParameter("pimagepath");
		if(psize.equals("")) {
			String referer = request.getHeader("Referer");
			flag =3;
			model.addAttribute("flag", flag);
			return "redirect:"+referer;
		} else {
		basketService.addBasket(pname,principal.getName(),psize,pimagepath);
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
	
	@RequestMapping(value = "/reviewDel/{rnum},{onum}")
	public String reviewDel(@PathVariable("rnum") Integer rnum,@PathVariable("onum") String onum,HttpServletRequest request,Principal principal) {
		reviewService.reviewDelete(rnum, principal.getName(), onum);
		String referer = request.getHeader("Referer");
		return "redirect:"+referer;
	}
	
	@RequestMapping(value = "/buy")
	public String buy(HttpServletRequest request,Principal principal,Model model) {
		String bindex = request.getParameter("bindex");
		String loginname= principal.getName();
		SMember member = memberService.memberView(loginname);
		
		String mname = request.getParameter("mname");
		String mtel = request.getParameter("mtel");
		String maddress = request.getParameter("maddress");
		if(mname=="") {
			mname = member.getSmname();
		}
		if(mtel=="") {
			mtel = member.getSmtel();
		}
		if(maddress=="") {
			maddress = member.getSmaddress();
		}
		
		if(!bindex.equals("")) {
			List<Basket> baskets = basketService.showBasket(principal.getName());
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
			orderService.BuyItem(maddress, mname, mtel,principal.getName(), baskets.get(idx).getBpname(), Integer.parseInt(baskets.get(idx).getBpsize()),baskets.get(idx).getBpimage(),Integer.toString(baskets.get(idx).getBpprice()));
			
			}
			List<Buy> buyList = buyRepository.findAll();
			for(int i=0;i<buyList.size();i++) {
			basketService.BDeleteBasket(loginname, buyList.get(i).getBypname(),buyList.get(i).getBypsize());
			}
		} 
		List<Buy> buyList = buyRepository.findAll();
		model.addAttribute("buyList", buyList);
		bflag=5;
		model.addAttribute("bflag", bflag);
		bflag=0;
		return "buyList";
	}
	
	@RequestMapping(value = "/buyListOk")
	public String buyListOk() {
		buyRepository.deleteAll();
		return "redirect:mypage";
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
	
	@RequestMapping(value = "/infoUpdate")
	public String infoUpdate(MemberForm2 memberForm2,Model model, Principal principal) {
		Optional<SMember> smembers = memberRepository.findById(principal.getName());
			SMember smember = smembers.get();
			String name = smember.getSmname();
			String address = smember.getSmaddress();
			String tel = smember.getSmtel();
			memberForm2.setUsername(principal.getName());
			memberForm2.setName(name);
			memberForm2.setAddress(address);
			memberForm2.setTel(tel);
			model.addAttribute("member", smember);
		return "infoUpdate";
	}
	
	@RequestMapping(value = "/updateOk")
	public String updateOk(@Valid MemberForm2 memberForm2, BindingResult bindingResult, Principal principal, Model model) {
		if(bindingResult.hasErrors()) {
			return "infoUpdate";
		}
		try {
			memberService.memberUpdate(principal.getName(), memberForm2.getPassword(), memberForm2.getName(), memberForm2.getAddress(), memberForm2.getTel());
		}catch(Exception e){
			e.printStackTrace();
			bindingResult.reject("updateFail", "수정에 실패 했습니다.");
			return "infoUpdate";
		}
		uflag=1;
		
		model.addAttribute("username", memberForm2.getUsername());
		model.addAttribute("uname", memberForm2.getName());
		model.addAttribute("upw", memberForm2.getPassword());
		model.addAttribute("uadd", memberForm2.getAddress());
		model.addAttribute("utel", memberForm2.getTel());
		model.addAttribute("uflag", uflag);
		return "infoUpdateOk";
	}
	
	@RequestMapping(value = "/ntview/{ntnum}")
	public String review(@PathVariable("ntnum") int ntnum,Model model) {
		Notice notice = noticeService.findById(ntnum);
		notice.setNthit(notice.getNthit()+1);
		noticeRepository.save(notice);
		model.addAttribute("notice", notice);
		return "ntview";
	}
}

