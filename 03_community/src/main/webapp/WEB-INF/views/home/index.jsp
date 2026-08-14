<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<section class="hero">
	<h1 class="hero-title">커뮤니티 페이지</h1>
	<p class="hero-desc">자유롭게 글을 쓰고 이야기를 나눠보세요.</p>
	<div class="hero-actions">
		<a href="/board/list" class="btn">게시글 목록</a>
		<a href="/member/login" class="btn">로그인</a>
	</div>
</section>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
