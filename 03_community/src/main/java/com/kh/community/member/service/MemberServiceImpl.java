package com.kh.community.member.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.community.common.util.FileUploadUtil;
import com.kh.community.common.util.SavedFile;
import com.kh.community.member.model.dto.MemberDTO;
import com.kh.community.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

// MemberService를 implement 하는 클래스라서 MemberServiceImpl (네이밍 관례라고 보면 됨)
@Service
@RequiredArgsConstructor 
public class MemberServiceImpl implements MemberService {
	// FileUploadUtil을 lombok으로 DI 처리 (생성자 주입 방식)
	private final FileUploadUtil uploadUtil;
	// MemberMapper DI
	private final MemberMapper mapper;
	
	// application.properties 파일에 사용자정의 경로를 설정함
	@Value("${file.upload-dir.profile}")
	private String profileUploadDir;

	@Override
	public void join(MemberDTO member, MultipartFile profileImage) throws IOException {
		// 프로필 이미지 파일을 "서버"에 저장 --> 공통 클래스로 분리
		SavedFile saved = uploadUtil.save(profileImage, 
										"uploads/profile",
										"/uploads/profile");
		if (saved != null) {
			// 저장된 경로를 DTO에 설정
			member.setProfile(saved.getPath());
		}
		
		// TB_MEMBER 테이블, 즉 "DB"에 데이터 저장 --> Mapper
		mapper.insertMember(member);
	}

	@Override
	public boolean insMemberIdCheck(String memberId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MemberDTO login(String memberId, String memberPwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void withdraw(String memberId) {
		// TODO Auto-generated method stub
		
	}
	
}
