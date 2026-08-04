package com.kh.spring.test;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.spring.member.MemberDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 	어노테이션 아무것도 없는 상태일 때는 순수한 POJO 
 */

/*
 * 	@Controller: 요청을 받기 위한 클래스에 설정으로, 기본적으로 @Component 기능이 포함되어 있음
 * 
 * 	@Component: 이 어노테이션이 설정된 클래스는 스프링이 생성 및 삭제 등 관리를 수행하게 된다
 * 				스프링이 객체를 만들 클래스들을 모아 놓은 목록을 Bean Factory(빈 팩토리)라고 한다
 * 				스프링은 빈 팩토리에 등록된 클래스로만 객체를 생성해서 활용할 수 있음
 * 				@Component를 사용하면 해당 클래스를 빈으로 등록해서 스프링이 사용할 수 있음!
 */
@Controller
@RequestMapping("/test")	// 주소 매핑: 해당 컨트롤러의 모든 메서드 호출 시 앞에 붙는 주소
// 한 프로젝트 내에서도 path를 나누어 두는 거라고 보면 됨
/*
 * 	@ResponseBody: 메서드의 return값을 View(화면)가 아니라 HTTP 응답 body에
 * 				   직접 텍스트로 보내는 설정
 * 
 * 		* @RestController: = @Controller + @ResponseBody
 */
@ResponseBody
public class RequestTestController {
	// ========== 스프링의 컨트롤러에서 요청 데이터를 처리하는 방법 ==========
	
	// * 기존 방법인 HttpServletRequest 객체를 사용하는 방법
	// @RequestMapping은 GET방식이든 POST방식이든 매핑해주는 거고, @GetMapping은 GET방식만!
	@GetMapping("/servlet-request") 
	// => /test/servlet-request의 GET 요청을 해당 메서드로 처리하겠다는 의미
	public String servletRequestTest(HttpServletRequest req) {
		// 요청 파라미터 추출: req.getParameter("key")
		String userId = req.getParameter("userId");
		int age = Integer.parseInt(req.getParameter("age"));
		
		return "servletRequest 응답: " + userId + ", " + age;
	}
	
	// => localhost:8888/test/servlet-request?userId=xxx&age=xxx

	// * @RequestParam 어노테이션 사용
	/*
	 * 	파라미터를 하나씩 받을 때 사용
	 * 	스프링이 HttpServletRequest 객체에서 값을 꺼내서 매개변수의 인자값으로 전달해줌
	 * 	=> 따로 변환할 필요가 없음!
	 * 	
	 * 	- value : 요청 파라미터 이름(키값)
	 * 	- defaultValue : 파라미터가 없을 때 사용할 기본값
	 * 	
	 * 	=> 파라미터의 이름(키값)과 변수명이 일치하는 경우 어노테이션을 생략할 수 있다.
	 */
	@GetMapping("/request-param")	// => /test/request-param의 get 요청을 처리
	public String requestParamTest(
			@RequestParam(value="userId", defaultValue="guest") String userId,
//			@RequestParam(value="age") int age
			// 키값과 변수명이 같을 때는 @RequestParam(value="keyName") 생략 가능
			int age) {
		return "requestParam 응답: " + userId + ", " + age;
	}

	// => localhost:8888/test/request-param?userId=nero&age=22

	// * @ModelAttribute
	/*
	 * 	클래스 타입 등 객체를 받을 수 있는 어노테이션
	 * 	MemberDTO과 같은 객체로 요청 파라미터를 받을 때 사용
	 * 	요청에 전달된 파라미터와 받아주는 객체의 필드명이 매칭되어 전달됨
	 * 	이때, 내부적으로는 setter가 실행됨 (호출됨) => setter를 정의해야 함
	 */
	@GetMapping("/request-member") // => localhost:8888/test/request-member
	public String requestModelTest (
				@ModelAttribute MemberDTO member) {
	
		System.out.println(member);
		return "requestMember 응답: " + member.getId() 
										+ ", " + member.getName();
				
	}
	
	// => localhost:8888/test/requets-member?id=1&name=nero&email=nero@email.com&age=22


	/*
	 * @PathVariable
	 * 	- URL 경로에 있는 값을 변수로 받을 수 있음
	 * 	- RESTful URL 방식으로, 조회 대상 ID 등을 URL 경로에 직접 포함할 때 사용
	 * 	ex) /test/member/1
	 */
	@GetMapping("/path-variable/{memberId}")
	public String pathVariableTest(
			@PathVariable("memberId") int id) {
		return "pathVariable 응답: " + id;
	}
	// => localhost:8888/test/path-variable/숫자
	
	// * Map: DTO를 따로 정의하지 않고, 여러 값을 한번에 받고자 할 경우 사용
	//		(받을 값이 많은 회원가입 등에 사용됨)
	//		@RequestParam Map 사용
	@GetMapping("/query-map")
	public String queryMapTest(@RequestParam Map<String, String> params) {
		return "queryMap 응답: " + params;
	}
	
	// 쿠키 저장 및 조회
	@GetMapping("/cookie/set")
	public String setCookieTest(HttpServletResponse resp) {
		// Cookie 생성 후 저장
		Cookie cookie = new Cookie("userId", "cookieLover");
		// ㄴ> 생성자를 통해 쿠키에 데이터 저장
		cookie.setMaxAge(60*60);
		cookie.setPath("/");
		
		// 응답 객체에 쿠키 추가
		resp.addCookie(cookie);
		
		return "Cookie Saved!";
	}
	
	@GetMapping("/cookie")
	public String getCookieTest(
			@CookieValue(value="userId", defaultValue="404 Not Found") String userId) {
		return "Cookie에 저장된 id: " + userId;
	}
	
	
	
	
	
	
	
	












}
