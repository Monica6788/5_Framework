<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>modelTest 파일</title>
	</head>	
	<body>
		
		<h1>결과 화면</h1>
		<p>스크립틀릿: <%= request.getAttribute("message") %></p>
		<p>표현식(EL): ${message}</p>
		
	</body>

</html>