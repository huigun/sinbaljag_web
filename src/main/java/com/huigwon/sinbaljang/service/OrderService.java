package com.huigwon.sinbaljang.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huigwon.sinbaljang.entity.Order;
import com.huigwon.sinbaljang.entity.PList;
import com.huigwon.sinbaljang.entity.PSize;
import com.huigwon.sinbaljang.repository.OrderRepository;
import com.huigwon.sinbaljang.repository.PListRepository;
import com.huigwon.sinbaljang.repository.SizeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final PListRepository pListRepository;
	private final SizeRepository sizeRepository;
	private final OrderRepository orderRepository;
	
	public Page<PList> findPAll(int page) {
		
		List<Sort.Order> sort = new ArrayList<>();
		
		sort.add(Sort.Order.desc("pnum"));
		
		Pageable pageable = PageRequest.of(page, 8, Sort.by(sort));//페이지당 표시되는 글 개수
		
		Page<PList> orders = pListRepository.findAll(pageable);
		return orders;
	}
	
	public List<PList> findPAll() {
		List<PList> plist = pListRepository.findAll();
		
		return plist;
	}
	
	public List<PList> productView(String pname) {
		List<PList> order = pListRepository.findByPname(pname);
		
		return order;
	}
	
	public List<Integer> PSize(String pname) {
		List<Integer> psize = new ArrayList<>();
		List<PSize> size = sizeRepository.findAll();
		List<PList> order = pListRepository.findByPname(pname);
		if(order.get(0).getPgender().equals("woman")) {
			for(int i=0;i<size.size();i++) {
				psize.add(size.get(i).getWsize());
			}
		} else if(order.get(0).getPgender().equals("man")){
			for(int i=0;i<size.size();i++) {
				psize.add(size.get(i).getMsize());
			}
		}
		return psize;
	}
	
	public void BuyItem(String maddress, String mname, String mtel, String pname, int osize) {
		Order order = new Order();
		order.setOcon(1);
		order.setOcount(1);
		order.setOdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		order.setOmaddress(maddress);
		order.setOmname(mname);
		order.setOmtel(mtel);
		order.setOpname(pname);
		order.setOsize(osize);
		
		orderRepository.save(order); 
	}

	public void saveItem(PList plist, MultipartFile imgFile, String pname, String pgender, String pprice) throws Exception{
		String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/uploadfiles/";
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName
        imgName = savedFileName;
        File saveFile = new File(projectPath, imgName);
        imgFile.transferTo(saveFile);
        plist.setPimage(imgName);
        plist.setPimagepath("/uploadfiles/" + imgName);
        if(pgender.equals("W")||pgender.equals("w")) {
        	pgender="woman";
        } else if(pgender.equals("M")||pgender.equals("m")) {
        	pgender="man";
        }
        
        plist.setPgender(pgender);
        plist.setPname(pname);
        plist.setPprice(Integer.parseInt(pprice));
        pListRepository.save(plist);
	}
}
