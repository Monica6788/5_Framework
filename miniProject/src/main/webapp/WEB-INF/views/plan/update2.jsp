<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/form.css">
</head>
<body>
	<form action="update/2" method="post">
		<input type="hidden" name="planId" value="${p.planId}">
		<input type="hidden" name="planType" value="${p.planType}">	
		<label>제목  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="text-box" name="planTitle" value="${p.planTitle}" required></label> <br>
		<label>날짜  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="text-box" name="planDate" value="${p.planDate}" required></label><br>
		<label>마감일&nbsp;&nbsp;&nbsp; <input type="text" class="text-box" name="planDue" value="${p.planDue}" required></label><br>
		<button>수정 완료</button>
	</form>
</body>
</html>