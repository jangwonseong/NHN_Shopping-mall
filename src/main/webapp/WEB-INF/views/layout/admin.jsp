<!-- /WEB-INF/views/layout/admin.jsp -->
<%@ page import="com.nhnacademy.shoppingmall.user.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/admin.css">

    <%
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
    %>
</head>
<body>
<div class="mainContainer">
    <!-- Header -->
    <header class="p-3 bg-dark text-white">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-between">
                <!-- 로고 -->
                <a href="/admin/index.do" class="d-flex align-items-center text-white text-decoration-none">
                    <h1 class="h4 m-0 fw-bolder fs-2">관리자 페이지</h1>
                </a>

                <!-- 로그아웃 버튼 -->
                <div class="text-end">
                    <a href="/logout.do" class="btn btn-outline-light">로그아웃</a>
                </div>
            </div>
        </div>
    </header>

    <div class="container-fluid">
        <div class="row">
            <!-- 사이드바 메뉴 -->
            <nav class="col-md-2 d-md-block bg-light sidebar">
                <div class="position-sticky pt-3">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/category/list.do">
                                <i class="bi bi-list-ul"></i> 카테고리 관리
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/product/list.do">
                                <i class="bi bi-box"></i> 상품 관리
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/user/list.do">
                                <i class="bi bi-people"></i> 회원 관리
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- 메인 컨텐츠 영역 -->
            <main class="col-md-10 ms-sm-auto px-md-4">
                <jsp:include page="${layout_content_holder}" />
            </main>
        </div>
    </div>

    <footer class="text-muted py-5">
        <div class="container">
            <p class="float-end mb-1">
                <a href="#">Back to top</a>
            </p>
            <p class="mb-1">Admin Panel © nhnacademy.com</p>
        </div>
    </footer>
</div>
</body>
</html>
