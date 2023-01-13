package com.huigwon.sinbaljang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.huigwon.sinbaljang.entity.Question;
import com.huigwon.sinbaljang.entity.Review;
import com.huigwon.sinbaljang.repository.QuestionRepository;
import com.huigwon.sinbaljang.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	private final QuestionRepository questionRepository;
	
	public List<Review> Preview(String rpname) {
		List<Review> reviews = reviewRepository.findByRpname(rpname);
		return reviews;
	}
	
	public List<Question> PQuestion(String qpname) {
		 List<Question> questions = questionRepository.findByQpnameOrderByQdateDesc(qpname);
		return questions;
	}
	
	public void reviewDelete(int rnum) {
		reviewRepository.deleteById(rnum);
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
}
