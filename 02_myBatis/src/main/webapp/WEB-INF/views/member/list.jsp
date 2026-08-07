<%@ page language="java" contentType="text/html;charsert=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>MyBatis Practice</title>
</head>
<body>
	<%-- message 값이 있을 경우 alert 메시지 내용 출력 --%>
	<c:if test="${message != null}">
		<script>
			alert("${message}");
			<c:remove var="message" />
		</script>
	</c:if>
	<h1>회원 목록</h1>
	
	<table border="1">
		<thaed>
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>이메일</th>
				<th>나이</th>
				<th>수정</th>
				<th>삭제</th>
			</tr>
		</thaed>
		<tbody>
			<%-- JSTL 반복문을 사용하여 조회 결과(memberList)를 한 행씩 출력 --%>
			<c:if test="${memberList == null}">
				<td colspan="4">등록된 회원이 없습니다.</td>
			</c:if>
			<c:forEach var="member" items="${memberList}">
				<tr>
					<td>${member.id}</td>
					<td>${member.name}</td>			
					<td>${member.email}</td>
					<td>${member.age}</td>
					<td><a href="/member/update/${member.id}">수정</a></td>
					<td><a href="/member/delete/${member.id}">삭제</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>