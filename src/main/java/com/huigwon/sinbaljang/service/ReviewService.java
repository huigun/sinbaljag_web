package com.huigwon.sinbaljang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.huigwon.sinbaljang.entity.Order;
import com.huigwon.sinbaljang.entity.Question;
import com.huigwon.sinbaljang.entity.Review;
import com.huigwon.sinbaljang.repository.OrderRepository;
import com.huigwon.sinbaljang.repository.QuestionRepository;
import com.huigwon.sinbaljang.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	private final QuestionRepository questionRepository;
	private final OrderRepository orderRepository;
	
	public List<Review> Preview(String rpname) {
		List<Review> reviews = reviewRepository.findByRpname(rpname);
		return reviews;
	}
	
	public List<Question> PQuestion(String qpname) {
		 List<Question> questions = questionRepository.findByQpnameOrderByQdateDesc(qpname);
		return questions;
	}
	
	public void reviewDelete(int rnum,String mname, String onum) {
		Optional<Order> orders = orderRepository.findById(Integer.parseInt(onum));
		reviewRepository.deleteById(rnum);
		Order order = orders.get();
		order.setOrcon("0");;
		orderRepository.save(order);
	}
	
	public void questionDelete(int qnum) {
		questionRepository.deleteById(qnum);
	}
	
	public void questionInsert(String qmname, String qcontent, String qpname) {
		Question question = new Question();
		question.setQmname(qmname);
		question.setQcontent(qcontent);
		question.setQdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		question.setQpname(qpname);
		question.setQanswer("0");
		question.setAdate("0");
		questionRepository.save(question);
	}
	
	public void answerInsert(int qnum, String answer) {
		Optional<Question> questions = questionRepository.findById(qnum);
		Question question = questions.get();
		question.setAdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		question.setQanswer(answer);
		questionRepository.save(question);
	}
	
	public void reviewInsert(String mid, String rating, String rcontent, String pname,String odnum,String onum) {
		List<Order> orders = orderRepository.findByOmidOrderByOdateDesc(mid);
		Review review = new Review();
		review.setRmid(mid);
		review.setRrating(rating);
		review.setRdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		review.setRcontent(rcontent);
		review.setRpname(pname);
		review.setOnum(onum);
		reviewRepository.save(review);
		Order order = orders.get(Integer.parseInt(odnum));
		order.setOrcon("1");
		orderRepository.save(order);
	}
}
