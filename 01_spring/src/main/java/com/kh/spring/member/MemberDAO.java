package com.kh.spring.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kh.spring.util.DBUtil;

// DAO: 실제 DB에 접근하는 객체
@Repository	// = (@Component + DB 접근 계층임)을 의미하는 어노테이션
public class MemberDAO {

	// 회원 목록 조회: member 테이블 전체 목록을 조회한 결과 반환 메서드
	public List<MemberDTO> findAll() {
		List<MemberDTO> list = new ArrayList<>();
		String sql = "SELECT ID, NAME, EMAIL, AGE FROM MEMBER ORDER BY ID";
		
		/*
		 * JDBC 실행 순서
		 * 	0) 드라이버 로드 (환경에 따라 생략 가능)
		 * 		톰캣에서는 외부 WAS를 쓰기 때문에 생략 불가
		 * 	1) Connection 객체 생성
		 * 	2) PreparedStatement 객체 생성
		 * 	3) 쿼리문 실행 후 결과 반환 받기
		 * 		=> 추출 및 처리 작업
		 * 	4) 자원 반납 (try-with-resources 구문 사용으로 생략 가능)
		 */
		try (Connection conn = DBUtil.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			
			// 조회된 결과를 추출하여 리스트에 추가
			// 조회 결과 유무 확인: .next()
			while (rset.next()) {
//				// 컬럼을 기준으로 데이터를 추출: getXXX("컬럼명")
//				int id = rset.getInt("id");	// id 컬럼 추출
//				String name = rset.getString("name");	// name 컬럼 추출
//				String email = rset.getString("email");	// email 컬럼 추출
//				int age = rset.getInt("age");	// age 컬럼 추출
				
				MemberDTO m = new MemberDTO(
							rset.getInt("id"), rset.getString("name"),
							rset.getString("email"), rset.getInt("age"));
				list.add(m);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 회원 추가: 전달된 회원 정보를 member 테이블에 추가하는 메서드
	public void insert(MemberDTO member) {
		// 전달된 회원 정보 (이름, 이메일, 나이)
		// --> 회원번호 (시퀀스)
		String sql = "INSERT INTO MEMBER VALUES (SEQ_MEMBER_ID.NEXTVAL, ?, ?, ?)";
		
		try (Connection conn = DBUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getEmail());
			pstmt.setInt(3, member.getAge());
			
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("회원 추가 성공!");
			} else { System.out.println("회원 추가 실패..."); }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 회원 삭제: 전달된 회원 번호를 기준으로 member 테이블에서 삭제하는 메서드
	public void delete(int id) {
		// member 테이블에서 회원 번호가 전달된 id값과 일치하는 행을 삭제
		String sql = "DELETE FROM MEMBER WHERE ID = ?";
		try (Connection conn = DBUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("회원 삭제 성공! : " + id);
			} else { System.out.println("회원 삭제 실패... : " + id); }
			
		} catch (SQLException e) {
			e.printStackTrace();
//			throw new RuntimeException(e.getMessage());
			// 위와 같이 처리할 수도 있음!
		}
	}
	
	// 회원 정보 수정: 전달된 회원 정보 중 회원번호를 기준으로 이름, 이메일, 나이 컬럼의 값을 변경
	public void update(MemberDTO member) {
		String sql = "UPDATE MEMBER SET NAME = ?, EMAIL = ?, AGE = ? WHERE ID = ?";
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getEmail());
			pstmt.setInt(3, member.getAge());
			pstmt.setInt(4, member.getId());
			
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("Update Completed! : " + member.getId());
			} else {
				System.out.println("Update Failed... : " + member.getId());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 회원 정보 조회: member 테이블에서 전달받은 회원번호에 해당하는 회원 정보를 조회한 결과 반환
	public MemberDTO findById(int id) {
		System.out.println(id);
		MemberDTO member = null;
		String sql = "SELECT ID, NAME, EMAIL, AGE FROM MEMBER WHERE ID = ?";
		// DB 연동 로직
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				member = new MemberDTO(rset.getInt("id"), rset.getString("name"),
								rset.getString("email"), rset.getInt("age"));
			} else {
				System.out.println("조회 결과 없음");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(member);
		return member;
	}
	
}
