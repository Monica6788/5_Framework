<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<h2 class="page-title">게시판</h2>
	<!-- 글쓰기 버튼을 추가 -->
	<h4 class="text-right">
	    <a href="/board/write" class="btn btn-outline">글쓰기</a>
	</h4>
	<c:choose>
        <c:when  test="${empty boardList}">
            <p> 등록된 게시글이 없습니다.</p>
        </c:when>
        <c:otherwise>
            <div class="board-table-wrap">
                <table class="board-table">
                    <thead>
                        <tr>
                            <th class="board-table_col-no">번호</th>
                            <th class="board-table_col-category">카테고리</th>
                            <th class="board-table_col-title">제목</th>
                            <th class="board-table_col-writer">작성자</th>
                            <th class="board-table_col-date">작성일</th>
                            <th class="board-table_col-count">조회수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="board" items="${boardList}" varStatus="status">
                            <tr onclick="location.href='/board/detail/${board.boardId}'">
                                <th class="board-table_col-no">${board.boardId}</th>
                                <th class="board-table_col-category">
                                    <span class="board-table_category">${board.category}</span>
                                </th>
                                <th class="board-table_col-title">${board.title}</th>
                                <th class="board-table_col-writer">${board.writerNickname}</th>
                                <th class="board-table_col-date">${board.createAtStr}</th>
                                <th class="board-table_col-count">${board.count}</th>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />