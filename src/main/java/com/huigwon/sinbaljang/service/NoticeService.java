package com.huigwon.sinbaljang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.huigwon.sinbaljang.entity.Notice;
import com.huigwon.sinbaljang.entity.PList;
import com.huigwon.sinbaljang.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	
	public void ntinsert(String nttitle, String ntcontent) {
		Notice notice = new Notice();
		notice.setNtcontent(ntcontent);
		notice.setNttitle(nttitle);
		notice.setNtdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		notice.setNthit(0);
		notice.setNtwriter("관리자");
		noticeRepository.save(notice);
	}
	
	public Page<Notice> ntAll(int page){
		List<Sort.Order> sort = new ArrayList<>();
		sort.add(Sort.Order.desc("ntnum"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sort));//페이지당 표시되는 글 개수
		
		Page<Notice> notices = noticeRepository.findAll(pageable);
		
		return notices;
	}
	
	public List<Notice> findntAll() {
		List<Notice> ntlist = noticeRepository.findAll();
		
		return ntlist;
	}
	
	public Notice findById(int ntnum) {
		Optional<Notice> ntlists = noticeRepository.findById(ntnum);
		Notice ntlist = null;
		if(!ntlists.isEmpty()) {
			ntlist = ntlists.get();
		}
		return ntlist;
	}
}
