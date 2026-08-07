package com.kh.mybatis.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.mybatis.model.dto.MemberDTO;
import com.kh.mybatis.model.mapper.MemberMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller	// RestController는 JSON으로 직접 응답할 때
@RequestMapping("/member")
@RequiredArgsConstructor // 꼭 필요한 final 필드를 매개변수로 하여 생성자 제공
public class MemberController {
	// MemberMapper 주입 (생성자 주입 방식)
	private final MemberMapper mapper;
	// final을 붙여야 필수값인 줄을 알고, 할당 못 받을 시 나 이거 할당 못 받았다고! 내놔! 하고 오류 뱉음
	
//	public MemberController(MemberMapper mapper) {
//		this.mapper = mapper;
//	}
	
	/**
	 * 회원 목록 조회
	 * URL: [GET] /member/list
	 * Param: x
	 * 응답: 회원 목록 페이지(WEB-INF/views/member/list.jsp) 포워딩
	 */
	@GetMapping("/list")
	public String memberList(Model model) {
		// DB에서 조회된 회원 목록을 request 영역에 저장 (k: memberList)
		List<MemberDTO> memberList = mapper.findAll();
		model.addAttribute("memberList", memberList);
		
		return "member/list";
	}
	
	/**
	 * 회원 가입 페이지
	 * URL: [GET] /member/insert
	 * Param: x
	 * 응답: 회원 가입 페이지(/WERb-INF/views/member/insertForm.jsp) 포워딩
	 */
	@GetMapping("/insert")
	public String insert() {
		return "member/insertForm";
	}
	
	/**
	 * 회원 추가
	 * URL: [POST] /member/insert
	 * Param: name(String), email(String), age(int)
	 * 응답: 회원 목록 페이지로 리다이렉트
	 */
	@PostMapping("/insert")
	public String memberInsert(
//			@RequestParam(value="name", defaultValue="x") String name
//			String name, String email, int age
			// 프로젝트 할 때는 요청 파라미터가 n개 이상이면 DTO를 사용하기로 약속하는 것이 좋음
			// n은 팀원들과 협의 후 결정...
			@ModelAttribute MemberDTO member, HttpSession session) {
		int result = mapper.insert(member);
		/*	추가 성공: "회원 가입 성공!" 메시지 저장
		 * 	추가 실패: "회원 가입 실패..." 메시지 저장
		 * 	리다이렉트이므로 재요청이 들어가고 request에 담으면 리다이렉트 되면서 저장된 메시지 날림
		 * 	=> session 영역에 저장 (HttpSession)
		 */
		if(result > 0) {
			session.setAttribute("message", "회원 가입 성공!");
		} else { session.setAttribute("message", "회원 가입 실패..."); }
	
		return "redirect:/member/list";
	}
	
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable int id, Model model) {
		MemberDTO member = mapper.findById(id);
		
		model.addAttribute("member", member);
		
		return "/member/updateForm";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute MemberDTO member) {
		mapper.update(member);
		
		return "redirect:/member/list";
	}
}
