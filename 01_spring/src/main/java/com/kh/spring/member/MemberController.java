package com.kh.spring.member;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller		// = (@Componenet + 컨트롤러 계층의 기능) 어노테이션
// 이 클래스의 메서드가 반환하는 문자열은 "View"의 이름으로 해석(포워드 처리)됨
@RequestMapping("/member")	// 클래스 레벨의 공통 URL 지정
// 내부 메서드의 매핑 URL 앞에 자동으로 "/member"가 붙게 됨
public class MemberController {
	
	// MemberService 클래스를 주입 (생성자 주입 방식)
	private final MemberService service;
	
//	@Autowired (생략 가능)
	public MemberController(MemberService service) {
		this.service = service;
	}

	/**
	 * 회원 목록 조회 R (Read)
	 * URL: [GET] /member/list
	 */
	@GetMapping("/list")
	public String memberList(Model model) {
		List<MemberDTO> list = service.getMemberList();
		
		// 조회된 결과(list)를 request 영역에 저장 (k: memberList)
		model.addAttribute("memberList", list);
		
		// @Controller가 있어서 기본적으로 포워드 처리!
		return "member/list";	// => /WEB-INF/views/member/list.jsp
	}
	
	/**
	 * 회원 등록 C (Create)
	 * URL: [POST] /member/insert
	 * Parameter: age (나이), email (이메일), name (이름) 
	 * 			=> MemberDTO로 한 번에 받을 수 있다!
	 */
	@PostMapping("/insert")
	public String insert(@ModelAttribute MemberDTO member) {
		service.insertMember(member);
		
		return "redirect:/member/list";
	}
	
	/**
	 * 회원 삭제 D (Delete)
	 * URL: [GET] /member/delete/{id}
	 * 
	 */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable/*("id")*/ int id) {
		service.deleteMember(id);
		
		// 회원 목록 페이지로 재요청 (리다이렉트)
		return "redirect:/member/list";
	}
	
	/**
	 * 회원 수정 U (Update)
	 * URL: [POST] /member/update
	 * 요청 파라미터: {id: 회원번호, name: 이름, email: 이메일, age: 나이} -> MemberDTO
	 */
	@PostMapping("/update")
	public String update(@ModelAttribute MemberDTO member) {
		// 서비스로 수정 요청
		service.updateMember(member);
		
		return "redirect:/member/list";
	}
	
	/**
	 * 회원 수정 페이지 응답
	 * [GET] /member/update/회원번호
	 */
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable int id, Model model) {
		// 회원 번호를 기준으로 회원 정보를 조회
		MemberDTO member = service.getMember(id);
		
		// request 영역에 회원 정보 저장
		model.addAttribute("member", member);
		
		return "member/updateForm";
		// => /WEB-INF/views/member/updateForm.jsp
	}
	
}
