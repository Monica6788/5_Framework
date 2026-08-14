package com.kh.community.board.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor 
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {
	// TB_BOARD 테이블 기준으로 필드를 추가
	private Long boardId;
	private String memberId;	// 작성자
	private String category;
	private String title;
	private String content;		// CLOB도 문자타입
	private int count;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	
	// 로직을 위해 필요해서 추가한 필드
	private String createAtStr;
	private String updateAtStr;
	private String writerNickname;	// 작성자 닉네임 (join)
	
	// 상세 페이지에서 보여줄 이미지 목록
	private List<BoardImageDTO> images;
}
