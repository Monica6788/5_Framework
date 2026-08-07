<%@ page language="java" contentType="text/html;charsert=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원 수정</title>
</head>
<body>
	<h1>회원 정보 수정</h1>
	<form action="/member/update" method="post">
		<!-- 이름, 이메일, 나이를 입력받아 서버로 요청 -->
		<label>이름: <input type="text" name="name" value="${name}" required> <br></label>
		<label>이메일: <input type="text" name="email" value="${email}" required> <br></label>
		<label>나이: <input type="number" name="age" value="${age}"> <br></label>
		<input type="submit" value="완료">
	</form>
</body>
</html>