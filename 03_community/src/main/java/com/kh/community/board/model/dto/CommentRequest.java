package com.kh.community.board.model.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 요청 데이터만 담은 DTO
 * 
 * JavaScript에서 JSON 문자열로 전달한 데이터를 컨트롤러에서 @RequestBody로 받게 되는데,
 * JSON 변환 라이브러리(Jackson)가 전달된 키값을 DTO의 필드로 변환해줌.
 */
@Getter
@Setter
public class CommentRequest {
	private String content;
}
