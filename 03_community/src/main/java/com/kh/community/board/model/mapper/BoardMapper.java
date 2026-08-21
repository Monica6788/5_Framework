package com.kh.community.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.community.board.model.dto.BoardDTO;
import com.kh.community.board.model.dto.BoardImageDTO;
import com.kh.community.board.model.dto.BoardSearchCondition;

@Mapper
public interface BoardMapper {
	// 게시글 목록 조회 (SELECT)
	List<BoardDTO> selectBoardList(BoardSearchCondition condition);
	
	// 게시글 전체 개수 조회
	int selectBoardListCount(BoardSearchCondition condition);
	
	// 게시글 추가 (DML)
	int insertBoard(BoardDTO board);
	
	// 게시글 이미지 추가
	int insertBoardImage(BoardImageDTO boardImage);
	
	// 조회수 1 증가 (DML - UPDATE)
	int increaseViewCount(Long boardId);
	
	// 게시글 상세 조회 (SELECT)
	BoardDTO selectBoardDetail(Long boardId);
	
	// 게시글 이미지 조회
	List<BoardImageDTO> selectImagesByBoardId(Long boardId);
	
	// 게시글 삭제
	int deleteBoard(Long boardId);
	
}
