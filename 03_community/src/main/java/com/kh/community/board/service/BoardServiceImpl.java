package com.kh.community.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.community.board.model.dto.BoardDTO;
import com.kh.community.board.model.dto.BoardImageDTO;
import com.kh.community.board.model.dto.BoardListResult;
import com.kh.community.board.model.dto.BoardSearchCondition;
import com.kh.community.board.model.mapper.BoardMapper;
import com.kh.community.common.util.FileUploadUtil;
import com.kh.community.common.util.SavedFile;
import com.kh.community.member.model.dto.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	private final BoardMapper mapper;
	private final FileUploadUtil fileUploadUtil;
	@Value("${file.upload-dir.board}")
	private String boardUploadDir;

	@Override
	public BoardListResult getBoardList(BoardSearchCondition condition) {
		// 전체 게시글 수 조회
		int totalCount = mapper.selectBoardListCount(condition);
		
		// 페이징 정보 저장 (PageInfo 객체 생성)
		PageInfo pi = new PageInfo(condition.getPage(), condition.getSize(), totalCount);
		condition.setOffset(pi.getOffset());
		
		return new BoardListResult(mapper.selectBoardList(condition), pi);
	}

	@Override
	public Long writeBoard(BoardDTO board, List<MultipartFile> images)
			throws IllegalStateException, IOException {
		// DB에 게시글 정보 저장
		mapper.insertBoard(board);
		// => mapper가 실행된 후 BoardDTO에는 boardId 값이 채워짐
		
		Long boardId = board.getBoardId();
		// 이미지 파일을 서버에 저장
		saveImages(boardId, images);
		
		// 게시글 id값 리턴
		return boardId;
	}
	
	private void saveImages(Long boardId, List<MultipartFile> images) 
			throws IllegalStateException, IOException {
		// 이미지가 없으면 메서드 종료
		if (images == null || images.isEmpty()) {
			return;
		}
		int order = 0;
		for (MultipartFile file : images) {
			// 서버에 이미지 파일 저장
			SavedFile saved = fileUploadUtil.save(file, boardUploadDir, "/uploads/board");
			if (saved == null) {
				continue;
				// null이면 쿨하게 건너뛰고 다음 로직을 수행하기 위한 continue;
			}
			
			// 저장된 이미지 정보를 기준으로 DTO 객체 생성
			BoardImageDTO boardImage = new BoardImageDTO(
										null, boardId,
										saved.getOriginalName(), /*원본 파일명*/
										saved.getSaveName(), /*저장된 파일명*/
										saved.getPath(),
										order++, null);
			
			// DB에 게시글 이미지 저장
			mapper.insertBoardImage(boardImage);
		}
	}

	@Override
	public BoardDTO getBoardDetail(Long boardId) {
		// 상세 페이지 접근 시 조회수 1 증가 (업데이트)
		mapper.increaseViewCount(boardId);
		
		// boardId에 해당하는 게시글 조회
		BoardDTO board = mapper.selectBoardDetail(boardId);
		
		// boardId에 해당하는 게시글 이미지 조회 후 BoardDTO에 저장
		board.setImages(mapper.selectImagesByBoardId(boardId));
		
		return board;
	}

	@Override
	public void deleteBoard(Long boardId) {
		mapper.deleteBoard(boardId);
		
		// 회원 탈퇴 때처럼... 이미지 서버에서 삭제: 실습에선 생략 TODO
//		
//		BoardDTO board = mapper.selectBoardDetail(boardId);
//		List<BoardImageDTO> images = board.getImages();
//		if (images != null) {
//			fileUploadUtil.delete("/uploads/board",  boardUploadDir);
//		}
	}
	
	
}
