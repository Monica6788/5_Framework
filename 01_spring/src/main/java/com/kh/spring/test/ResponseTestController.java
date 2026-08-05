package com.kh.spring.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

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
	// 클래스에 공통 주소로 /test를 선언해두었으므로 "/index"
	public String responseIndex() {
		// prefix, suffix가 각각 앞뒤로 붙을 예정
		// {prefix}returnValue{suffix}
		// => /WEB-INF/views/pagePath.jsp
		// 위 파일을 ViewResolver라는 객체가 매칭시켜 응답해줄 것임
		// return "포워드처리할페이지경로";
		return "test/index";	// => /WEB-INF/views/test/index.jsp
	}
	
	// 요청 받을 주소가 /test/model-forward (GET 방식)
	
	@GetMapping("/model-forward")
	public String modelForwardTest(Model model) {
		// forward 방식으로 JSP(View)에 값을 전달할 때
		// request 영역을 사용 => 요청이 끝나는 시점까지만 데이터를 저장하여 사용 가능
		
		// 기존(Servlet) 방식: HttpServletRequest
		// Spring 방식: Model 객체로 처리 가능
		// => (spring이 줄 거니까 import도 springframework)
		model.addAttribute("message", "Model을 통해 데이터 저장");
		
		return "test/modelTest";
		// => /WEB-INF/views/test/modelTest.jsp
	}
	
	// 요청 받을 주소: /test/session/set (GET)
	@GetMapping("/session/set")
	public String sessionTest(HttpSession session) {
		// 세션 영역에 데이터 저장 (HttpSession)
		//	=> 로그인 정보와 같이 여러 페이지에서 지속적으로 유지해야 하는 데이터 저장
		session.setAttribute("user", "dante");
		
		// Redirect 처리
		// "redirect:" 접두사 사용!		
		return "redirect:/test/session";
		// 브라우저로 /test/session 재요청 지시
		// => 새로운 요청이 발생하고 브라우저의 주소창에서 요청한 주소도 변경됨
		
		// context path가 /spring이라면 redirect:_____? (현재 context path: "/")
		// => "redirect:/spring/test/session"
	}
	
	@GetMapping("/session")
	public String sessionTest(HttpSession session, Model model) {
		// 세션 영역에서 "user" 데이터 추출 -> HttpSession 객체 필요
		String user = (String)session.getAttribute("user");
		// request 영역에 "message" 이름으로 "user" 데이터 저장 -> Model 객체 사용
		model.addAttribute("message", user);
		// modelTest.jsp로 포워드 처리	
		return "test/modelTest";
	}
	
	
	
	
	
	
	
	
	
	
	
}
