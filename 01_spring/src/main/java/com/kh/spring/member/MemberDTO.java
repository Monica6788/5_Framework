package com.kh.spring.member;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor			// 기본 생성자
@AllArgsConstructor			// 모든 필드를 매개변수로 가지는 생성자
@Getter						// getter 메서드
@Setter						// setter 메서드
@ToString					// toString 메서드
@EqualsAndHashCode			// equals, hashcode 자동생성
public class MemberDTO {
	// JDBC 계정의 MEMBER 테이블 기준으로 필드 작성해보기
	/*
	 * 	ID NUMBER
	 * 	NAME VARCHAR2(50BYTPE)	- > String name
	 * 	EMAIL VARCHAR2(100 BYTE) -> String email
	 * 	AGE NUMBER
	 */
	private int id;
	private String name;
	private String email;
	private int age;
	

}
