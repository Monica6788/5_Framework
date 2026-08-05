package com.kh.spring.member;

import java.util.List;

import org.springframework.stereotype.Service;

@Service	// = (@Component + 이 클래스가 비즈니스 로직 계층임)을 나타내는 어노테이션
public class MemberService {
	private final MemberDAO dao;
	
	// DI. MemberDAO -> 생성자 주입방식
	public MemberService(MemberDAO dao) {
		this.dao = dao;
	}
	// 회원 목록 조회: DB에서 조회된 결과(List)를 반환
	public List<MemberDTO> getMemberList() {
		
		return dao.findAll();	//TODO
	}
	
	// 회원 정보 추가: 회원 정보(DTO)를 전달받아 DB에 추가
	public void insertMember(MemberDTO member) {
		// 반환형으로 int나 boolean으로 하여 결과를 반환받을 수도 있다
		dao.insert(member);
	}
	
	// 회원 정보 삭제: 회원 정보(id)를 전달받아 DB에서 삭제
	public void deleteMember(int id) {
		dao.delete(id);
	}
}
