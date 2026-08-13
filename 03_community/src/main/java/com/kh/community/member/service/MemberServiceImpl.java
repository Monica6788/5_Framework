package com.kh.community.member.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	// PasswordEncoder DI
	private final PasswordEncoder passwordEncoder;
	
	// application.properties 파일에 사용자정의 경로를 설정함
	@Value("${file.upload-dir.profile}")
	private String profileUploadDir;

	@Override
	public void join(MemberDTO member, MultipartFile profileImage) throws IOException {
		// 아이디 중복검사
		if (isMemberIdCheck(member.getMemberId())) {
			throw new IllegalStateException("이미 사용 중인 아이디입니다.");
		}
		
		/* 비밀번호 암호화 처리 
		 * -> Spring Security 프레임워크에 있는 BCryptPasswordEncoder (SecurityConfig에서 설정)
		 * 암호화: passwordEncoder.encode(입력받은비밀번호)
		 */
		 String encodePwd = passwordEncoder.encode(member.getMemberPwd());
		 //비밀번호 필드를 암호화된 값으로 변경 
		member.setMemberPwd(encodePwd);
		
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
	public boolean isMemberIdCheck(String memberId) {
		// 중복된 아이디가 있을 경우: 1
		return mapper.countByMemberId(memberId) > 0; /* == 1도 가능 */
	}

	@Override
	public MemberDTO login(String memberId, String memberPwd) throws IllegalStateException {
		// ID를 기준으로 회원정보 조회
		MemberDTO member = mapper.selectByMemberId(memberId);
		
		// 조회된 정보 중 비밀번호(암호문)와 전달된 비밀번호(평문)가 일치하는지 확인
		// 암호화 된 비밀번호: DB에서 조회한 값 (member.getMemberPwd())
		// 평문 비밀번호: 전달된 값 (memberPwd)
		// passwordEncoder.matches(평문, 암호문)으로 일치 여부 확인 후 boolean 반환
		boolean checkMember = passwordEncoder.matches(memberPwd, member.getMemberPwd());
		if (member == null/* 해당 id를 가진 회원 없음 */ || !checkMember /* 비밀번호 틀림 */) {
			throw new IllegalStateException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		// 회원정보 반환
		return null;
	}

	@Override
	public void withdraw(String memberId) {
		// TODO Auto-generated method stub
		
	}
	
}
