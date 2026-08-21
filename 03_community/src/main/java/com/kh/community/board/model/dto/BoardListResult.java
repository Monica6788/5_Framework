package com.kh.community.board.model.dto;

import java.util.List;

import com.kh.community.member.model.dto.PageInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 반환해야 할 DTO가 여러 개일 경우 (지금 상태로는 List<BoardDTO>와 PageInfo)
 * 하나로 묶어주는 DTO를 하나로 
 */
@AllArgsConstructor
@Getter
public class BoardListResult {
	private List<BoardDTO> boardList; // 조회 목록
	private PageInfo pageInfo; // 페이징 정보

}
