package com.kh.community.member.model.dto;

import lombok.Getter;

/*
 * 페이징 정보를 계산하고 저장하는 클래스
 * 
 * - 게시판 목록처럼 데이터가 많아질 경우 한 페이지에 전부 가져올 필요 없이
 * 	 필요한 만큼만 끊어서 보여주고자 할 때 사용하는 방법 (페이징 처리)
 * 
 * - 화면에 보여줄 개수(size), 현재 페이지 번호(page), 전체 게시글 수를 통해 계산을 수행함.
 */
@Getter
public class PageInfo {
	private int page;		// 현재 페이지 번호
	private int size;		// 한 페이지에 보여줄 게시글 개수
	private int totalCount;	// 전체 목록의 개수 (게시글 개수)
	
	private int totalPages;	// 전체 페이지 개수
	private int startPage;	// 화면에서 보여줄 페이지 시작 번호
	// 5개의 페이지씩 보여준다고 하면? 1, 6, 11, ...
	private int endPage;	// 화면에서 보여줄 페이지 끝 번호
	// 5개의 페이지씩 보여준다고 하면? 5, 10, 15, ...
	
	private boolean hasPrevGroup;	// 이전 페이지 그룹 존재 여부
	// hasPreviousGroup: 이전 페이지 그룹 예 - {1, 2, 3, 4, 5} 페이지 그룹
	private boolean hasNextGroup;	// 다음 페이지 그룹 존재 여부
	// 예 - {6, 7, 8, 9, 10} 페이지 그룹
	
	private static final int PAGE_GROUP_SIZE = 5;
	// 하단에 한 버에 보여줄 페이지 번호 개수 (고정)
	
	public PageInfo(int page, int size, int totalCount) {
		this.page = page < 1 ? 1 : page;
		// 혹시 모르니 1 미만이면 1로 초기화, 1 이상이면 원래 페이지 번호인 page
		this.size = size;
		this.totalCount = totalCount;
		
		/* 전체 페이지 수: 전체 게시글 수 / 한 페이지당 게시글 수인데,
		 * 나머지가 있는 경우 (몫 + 1)개가 되므로 올림 처리
		 * Math.ceil(전체 게시글 수 / (double)한 페이지당 게시글 수)
		 */
		this.totalPages = (int)Math.ceil(totalCount / (double)size);
		
		/* 표시되는 페이지 번호: page = 7, PAGE_GROUP_SIZE = 5일 경우,
		 * 하단에 표시되는 페이지 선택 버튼은 6, 7, 8, 9, 10
		 */
		this.startPage = ((this.page - 1) / PAGE_GROUP_SIZE) * PAGE_GROUP_SIZE + 1;
		this.endPage = Math.min(startPage + PAGE_GROUP_SIZE - 1, totalPages);
		// 페이지 번호가 13까지밖에 없는데 계산 결과가 15일 경우 13까지만 나타내기 위해 최솟값 처리
		
		// 이전/다음 그룹 존재 여부
		this.hasPrevGroup = startPage > 1;
		this.hasNextGroup = endPage < totalPages;
	}
	
	public int getOffset() {
		return (page - 1) * size;
	}
}
