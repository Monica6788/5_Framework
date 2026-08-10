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
	<form action="insert/3" method="post">
		<input type="hidden" name="planType" value="3">
		<label>제목 &nbsp;&nbsp;&nbsp; <input type="text" class="text-box" name="planTitle" required></label> <br>
		<label>날짜 &nbsp;&nbsp;&nbsp; <input type="text" class="text-box" name="planDate" required></label><br>
		<label>시간 &nbsp;&nbsp;&nbsp; <input type="text" class="text-box" name="planTime" required></label><br>
		<button>일정 추가</button>
	</form>
</body>
</html>