<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정 수정</title>
</head>
<body>
	<form action="update?planId=${planId}&planType=${planType}" method="post">

		<label>제목: <input type="text" name="planTitle" value="${p.planTitle}" required></label> <br>
		<label>날짜: <input type="text" name="planDate" value="${p.planDate}" required></label><br>
		<label>시간: <input type="text" name="planTime" value="${p.planTime}" required></label><br>
		<button>수정 완료</button>
	</form>
</body>
</html>