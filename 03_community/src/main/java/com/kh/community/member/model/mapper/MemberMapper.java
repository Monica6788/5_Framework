package com.kh.community.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kh.community.member.model.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	// 회원가입 -> 데이터 추가
	int insertMember(MemberDTO member);
	
	// ID 중복 확인 -> 데이터를 조회
	int countByMemberId(String memberId);
	
	// ID를 통한 회원 조회
	MemberDTO selectByMemberId(String memberId);
}
