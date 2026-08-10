<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정 추가</title>
</head>
<body>
	<form action="insert/3" method="post">
		<input type="hidden" name="planType" value="3">
		<label>제목: <input type="text" name="planTitle" required></label> <br>
		<label>날짜: <input type="text" name="planDate" required></label><br>
		<label>시간: <input type="text" name="planTime" required></label><br>
		<button>일정 추가</button>
	</form>
</body>
</html>