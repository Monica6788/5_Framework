package com.kh.easymail.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.easymail.model.MailRequest;
import com.kh.easymail.service.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j	// 로그를 추가해주는 어노테이션
@RestController
@RequiredArgsConstructor
public class MailController {
	private final MailService service;
	
	/**
	 * 인증코드 발송
	 * 
	 * @param email: 받는사람
	 * @return "ok"
	 * @throws Exception
	 */
	@PostMapping("/mail")
	public String sendMail(@RequestBody MailRequest request)
											throws Exception {
		String email = request.getEmail();
		
		if (email == null) {
			throw new Exception("필수 항목 누락 - email");
		}
		
		// Slf4j가 로그를 자동으로 만들어줌
		log.info("* email: {}", email);
		
		// 서비스를 통해 메일 전송
		service.sendCode(email);
		
		return "ok";
	}
	
	/**
	 * 인증코드 검증
	 * 
	 * @param email: 받는사람, code: 인증코드
	 */
	
	
}
