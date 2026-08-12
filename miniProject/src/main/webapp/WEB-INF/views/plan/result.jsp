<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Planner</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Gowun+Batang&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/3dd9964fc0.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
    <!-- header area: logo, serach bar, logout/delete account buttons -->
    <header>
        <!-- logo -->
        <a href="/plan/list" class="logo">My Plan</a>

        <!-- search bar -->
        <form action="/plan/result">
            <div class="search">
                <input type="text" name="keyword" placeholder="검색어를 입력하세요">
                <button>
                    <i class="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>
        </form>

        <!-- logout/delete account buttons -->
        <div class="header-links">
            <a href="/">로그아웃</a>
            <a href="#">회원탈퇴</a>
        </div>
    </header>

    <!-- main area: navigation, planlist -->
    <main>
        <!-- navigation: user-info, add-plan, file-save-load -->
        <nav class="menu">
            <ul class="user-info">
                <div class="img-block">
                    <img src="${pageContext.request.contextPath}/pics/profile_img.png" alt="프로필 이미지">
                    <p class="update-delete">
                        <a href="#">수정</a>
                        <a href="#">삭제</a>
                    </p>
                </div>
                <li>Monica6788</li>
                <li>user001@gmail.com</li>
            </ul>
            <ul class="add-plan">
<!--                <li>
                    <span>기념일 추가</span><a href="insert">+</a>
                </li>
                <li>
                    <span>기간일정 추가</span><a href="insert">+</a>
                </li>-->
				<li>
				    <span>일정 추가</span><a href="insert">+</a>
				</li>
            </ul>
            <ul class="file-save-load">
                <li><a href="#">백업파일 저장하기</a></li>
                <li><a href="#">백업파일 불러오기</a></li>
            </ul>
        </nav>
        <section class="planlist">
            <article class="blocks">
                <c:forEach var="p" items="${planList}">
                    <c:choose>
                        <c:when test="${p.planType == 1}">
                            <div class="block" id="anniversary">
                                <h3>${p.planTitle}</h3>
                                <h4>${p.planDate}</h4>
                                <div class="update-delete">
                                    <a href="update?planId=${p.planId}&planType=${p.planType}">수정</a>
                                    <a href="delete?planId=${p.planId}">삭제</a>
                                </div>
                                <span class="badge" data-planid="${p.planId}">중요!</span>
                            </div>
                        </c:when>

                        <c:when test="${p.planType == 2}">
                            <div class="block" id="period">
                                <h3>${p.planTitle}</h3>
                                <h4>${p.planDate} ~ ${p.planDue}</h4>
                                <div class="update-delete">
                                    <a href="update?planId=${p.planId}&planType=${p.planType}">수정</a>
                                    <a href="delete?planId=${p.planId}">삭제</a>
                                </div>
                                <span class="badge" data-planid="${p.planId}">중요!</span>
                            </div>
                        </c:when>

                        <c:when test="${p.planType == 3}">
                            <div class="block" id="oneday">
                                <h3>${p.planTitle}</h3>
                                <h4>${p.planDate}&nbsp; ${p.planTime}</h4>
                                <div class="update-delete">
                                    <a href="update?planId=${p.planId}&planType=${p.planType}">수정</a>
                                    <a href="delete?planId=${p.planId}">삭제</a>
                                </div>
                                <span class="badge" data-planid="${p.planId}">중요!</span>
                            </div>
                        </c:when>

                    </c:choose>    
                </c:forEach>
                
            </article>
        </section>
    </main>

    <!-- footer area: links, Developer info,Copyright -->
    <footer>
        <!-- footer-links -->
        <div class="footer-links">
            <a href="#">이용약관</a>
            <a href="#">개인정보처리방침</a>
            <a href="#">버전정보</a>
            <a href="#">고객센터</a>
        </div>
        <!-- Developer info -->
        <div class="dev-info">
            <p>PHONE: 010-2718-2818</p>
            <p>EMAIL ADDRESS: example001@gmail.com</p>
        </div>
        <!-- CopyRight -->
        <p class="copy">
            CopyRight &copy; Monica6788 All Rights Reserved
        </p>
    </footer>

    <script src="/script.js"></script>
</body>
</html>