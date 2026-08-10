<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정 추가</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/form.css">
</head>
<body>
	<form action="insertForm" method="get">
		<h3>타입을 선택하세요.</h3>
		<div class="radio-box">
			<label><input type="radio" name="planType" value="1" checked> 기념일</label>
			<label><input type="radio" name="planType" value="2"> 기간</label>
			<label><input type="radio" name="planType" value="3"> 하루</label>
		</div>
		<br>
		<button>일정 추가</button>
	</form>
</body>
</html>