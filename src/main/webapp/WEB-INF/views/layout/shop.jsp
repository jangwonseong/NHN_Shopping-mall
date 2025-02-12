<%@ page import="com.nhnacademy.shoppingmall.user.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>
<%--
/home/nhnacademy/Desktop/ATGN02-014/shopping-mall/src/main/webapp/WEB-INF/views/layout/shop.jsp
--%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <!-- 문자 인코딩 설정 -->
    <meta charset="UTF-8">

    <!-- 반응형 웹 디자인을 위한 뷰포트 설정 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- 웹 페이지 제목 설정 -->
    <title>CREAM</title>

    <!-- Bootstrap CSS 라이브러리 불러오기 (CDN 사용) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Icons CDN 추가 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <!-- 프로젝트 내부의 CSS 파일 불러오기 -->
    <link rel="stylesheet" href="/css/shop.css">

    <%
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
    %>

</head>
<body>
<div class="mainContainer">
    <!-- Header -->
    <header class="p-3 bg-white text-black">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-between">
                <!-- 로고 -->
                <a href="/" class="d-flex align-items-center text-black text-decoration-none">
                    <h1 class="h4 m-0 fw-bolder fs-2 logo-rotated">CREAM</h1>
                </a>

                <!-- 검색창 -->
                <div class="search-container">
                    <form action="/index.do" method="GET" class="d-flex">
                        <div class="input-group">
                            <input type="search" name="search" class="form-control form-control-dark"
                                   value="${param.search}" placeholder="브랜드, 상품, 프로필, 태그 등">
                            <button type="submit" class="input-group-text btn btn-outline-dark">
                                <i class="bi bi-search"></i>
                            </button>
                        </div>
                    </form>
                </div>

                <!-- 로그인 및 회원가입 버튼 -->
                <c:choose>
                    <c:when test="${not empty user}">
                        <div class="text-end">
                            <a href="/logout.do" class="btn btn-light">로그아웃</a>
                            <a href="/mypage.do" class="btn btn-light">마이페이지</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-end">
                            <a href="/login.do" class="btn btn-light">로그인</a>
                            <a href="/signup.do" class="btn btn-light">회원가입</a>
                        </div>
                    </c:otherwise>
                </c:choose>


                <div class="shopping-cart">
                    <a href="/cart/list.do" class="text-dark text-decoration-none">
                        <i class="bi bi-bag fs-4"></i>
                        <c:if test="${not empty cartItems}">
            <span class="badge bg-danger rounded-pill position-absolute">
                    ${fn:length(cartItems)}
            </span>
                        </c:if>
                    </a>
                </div>

            </div>
        </div>
    </header>

    <main>
        <div class="album py-5 bg-light">
            <div class="container">
                <jsp:include page="${layout_content_holder}" />
            </div>
        </div>

    </main>

    <footer class="text-muted py-5">
        <div class="container">
            <p class="float-end mb-1">
                <a href="#">Back to top</a>
            </p>
            <p class="mb-1">shoppingmall example is © nhnacademy.com</p>
        </div>
    </footer>
</div>
</body>
</html>