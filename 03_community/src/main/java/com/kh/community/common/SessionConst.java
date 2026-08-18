package com.kh.community.common;
/*
 * 세션에 값을 저장할 때 사용하는 키를 한 곳에 모아서 관리하기 위한 클래스
 * -> 키값들을 상수로 관리
 * 
 * 인터페이스로 관리해도 상관 없고, 필요에 의해 만든 거라 필수로 넣어야 하는 클래스가 아님.
 */
public class SessionConst {
	public static final String LOGIN_MEMBER = "loginMember";
	
	// 이 클래스는 정적 상수만 관리하기 위해 객체 생성을 막고자 생성자를 private으로 선언
	private SessionConst() {}
	
	
}
