package com.kh.community.board.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDTO {
	// TB_COMMENT 기준으로 필드 정의
	private Long commentId;
	private Long boardId;
	private String memberId;
	private String content;
	private LocalDateTime createAt;
	
	private String createAtStr;
}
