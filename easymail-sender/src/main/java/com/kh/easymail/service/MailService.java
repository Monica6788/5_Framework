package com.kh.easymail.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender sender;
	
	public void sendCode(String email) {
		String subject = "[KH] 이고은";
		String text = "다음 주는 주 3일제!!!!!";
		
		// 자바는 파일 전체를 한 번에 컴파일하므로 위에서 호출 가능
		sendMail(subject, text, email);
		
		// 인증코드의 유효시간을 설정하려면?
		// 한계시간을 설정하고 해당 값까지 파라미터로 받고 DTO를 따로 만들고
		// 그 다음에 또 뭐라고 하셧지 놓쳤다 TODO
		// 이거를 15:43 ~ 15:45 이쯤에 얘기해주셨는데 녹화본 확인하기
	}
	
	/**
	 * 메일 전송 메서드
	 * @param subject 메일제목
	 * @param text	  메일내용
	 * @param to	  받는사람
	 */
	public void sendMail(String subject, String text, String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setSubject(subject);
		message.setText(text);
		message.setTo(to);
		
		sender.send(message);
	}

}
