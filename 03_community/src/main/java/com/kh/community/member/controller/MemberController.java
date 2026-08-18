package com.kh.community.member.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.community.common.SessionConst;
import com.kh.community.member.model.dto.ApiResponse;
import com.kh.community.member.model.dto.MemberDTO;
import com.kh.community.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * "회원" 관련 화면 이동, 폼 처리 등을 담당할 컨트롤러
 */
@Controller
@RequestMapping("/member")
public class MemberController {
	// MemberService를 DI 처리 (생성자 주입 방식)
	private final MemberService service;
	public MemberController (MemberService service) {
		this.service = service;
	}
	
	// ----- 화면 이동 요청 ----------
	@GetMapping("/join")
	public String joinForm() {
		return "member/join";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "member/login";
	}
	
	@GetMapping("/mypage")
	public String mypage() {
		return "member/mypage";
	}
	
	// ----------------------------
	@PostMapping("/join")
	public String join(@ModelAttribute MemberDTO member,
						@RequestParam(required=false) MultipartFile profileImage,
//						HttpSession session,
						RedirectAttributes redirectAttr) {
		System.out.println(member);
		System.out.println(profileImage);
		
		try {
			service.join(member, profileImage);
		} catch (IOException e) {
			e.printStackTrace();
			// "회원 가입 실패" 메세지를 저장 -> 클라이언트에서 사용
			// 세션 영역에 저장 (HttpSetssion)
//			session.setAttribute("error", "회원 가입 실패");
			// Redirect 후 딱 한 번 다음 요청에서만 사용되는 데이터 RedirectAttributes를 추가
			redirectAttr.addFlashAttribute("error", "회원 가입 실패");
			
			// 예외 발생 시 회원 가입 페이지로 리다이렉트
			return "redirect:/member/join";
		}
		// 회원 가입 성공 시 로그인 페이지로 리다이렉트
		redirectAttr.addFlashAttribute("joinSuccess", true);
		return "redirect:/member/login";
	}
	
	// @ResponseBody: 응답 본문에 데이터를 담아 처리
	/*
	 * URL: [GET] /member/checkId?memberId=XXX
	 */
	@ResponseBody
	@GetMapping("/checkId")
	public ApiResponse<Boolean> checkId(String memberId) {
		boolean isDuplicate = service.isMemberIdCheck(memberId);
		
		String message = isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.";
		// 응답 데이터용 DTO를 추가해야 함
		
		return ApiResponse.success(message, isDuplicate);
	}
	
	// login.jsp를 참고하여 로그인 요청을 받을 메서드 추가
	@PostMapping("/login")
	public String login(String memberId, String memberPwd,
						@RequestParam(required=false) String redirectURL,
						HttpSession session, RedirectAttributes redirectAttr) {
		try {
			MemberDTO member = service.login(memberId, memberPwd);
			// 로그인 성공: 세션에 로그인 정보 저장
			session.setAttribute(SessionConst.LOGIN_MEMBER, member);
			System.out.println(redirectURL);
			
			if (redirectURL != null && !redirectURL.isBlank()) {
				return "redirect:" + redirectURL;
			}
			
			return "redirect:/";
		} catch (IllegalStateException e) {
			redirectAttr.addFlashAttribute("error", e.getMessage());
			return "redirect:/member/login";
		}
		
		

	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		// HttpServletRequest
		// : 클라이언트가 서버로 보낸 모든 요청(Request) 정보를 담고 있는 객체
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();	// 세션 자체를 만료시킴(모두 삭제)
		}
		return "redirect:/";
	}
	
	@PostMapping("/withdraw")
	public String withdraw(HttpSession session, RedirectAttributes redirectAttr) {
		// 세션에 저장된 사용자 정보 추출
		MemberDTO loginMember = (MemberDTO)session.getAttribute(SessionConst.LOGIN_MEMBER);
		
		// 서비스에게 비즈니스 로직 요청
		service.withdraw(loginMember.getMemberId());
		
		// 세션 영역에서 모든 데이터 삭제 (세션 만료시키기)
		session.invalidate();

		return "redirect:/";
	}
	
}
