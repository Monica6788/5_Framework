<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원 정보 수정</title>
</head>
<body>
    <h1>회원 등록</h1>

    <form action="/member/update" method="post">
		<!-- 기본값을 원래 저장되어 있던 정보로 표시하기 EL -->
		<!-- request.getAttribute (표현식)로도 가능 (오류 방지로 꺾쇠랑 퍼센트 기호랑 등호는 생략) -->
		<input type="hidden" name="id" value="${member.id}">
		
		<p>회원 번호: ${member.id}</p>
        <label for="name">이름: </label>
        <input type="text" id="name" name="name" value="${member.name}" required> <br>

        <label for="email">이메일: </label>
        <input type="text" id="email" name="email" value="${member.email}" required> <br>

        <label for="age">나이: </label>
        <input type="number" id="age" name="age" value="${member.age}"> <br>

        <button>등록</button>
    </form>

    <p>
        <a href="/member/list">목록으로 돌아가기...</a>
    </p>
</body>
</html>