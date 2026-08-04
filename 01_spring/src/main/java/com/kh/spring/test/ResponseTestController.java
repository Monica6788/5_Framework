package com.kh.spring.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 어노테이션 추가
 * 	1) 요청을 받아 응답 처리하는 용도의 bean으로 등록	=> @Controller
 * 	2) 공통 주소: /test		=> @RequestMapping("/test")
 */
@Controller
@RequestMapping("/test")
public class ResponseTestController {
	// ========== 응답 방식 ==========
	/*
	 * @ResponseBody: 화면(View) 없이 결과를 바로 텍스트로 응답
	 * 	특정 메서드에만 붙여줘도 됨
	 */
	@ResponseBody
	@GetMapping		// => /test
	public String responseBodyTest() {
		return "결과를 텍스트로 응답";
	}

	// 기본적으로 forward 방식으로 응답 처리됨!
	@GetMapping("/index")	// => /test/index
	public String responseIndex() {
		// prefix, suffix가 각각 앞뒤로 붙을 예정
		// {prefix}returnValue{suffix}
		// => /WEB-INF/views/pagePath.jsp
		// 위 파일을 ViewResolver라는 객체가 매칭시켜 응답해줄 것임
		// return "포워드처리할페이지경로";
		return "test/index";	// => /WEB-INF/views/test/index.jsp
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
