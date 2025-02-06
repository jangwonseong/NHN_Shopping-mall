<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <!-- 문자 인코딩 설정 -->
    <meta charset="UTF-8">

    <!-- 반응형 웹 디자인을 위한 뷰포트 설정 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- 웹 페이지 제목 설정 -->
    <title>KREAM</title>

    <!-- Bootstrap CSS 라이브러리 불러오기 (CDN 사용) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Icons CDN 추가 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <!-- 프로젝트 내부의 CSS 파일 불러오기 -->
    <link rel="stylesheet" href="/css/shop.css">
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
                    <div class="input-group">
                        <input type="search" class="form-control form-control-dark" placeholder="브랜드, 상품, 프로필, 태그 등">
                        <span class="input-group-text">
                        <i class="bi bi-search"></i> <!-- 아이콘 -->
                    </span>
                    </div>
                </div>


                <!-- 로그인 및 회원가입 버튼 -->
                <div class="text-end">
                    <a href="/loginAction.do" class="btn btn-light">로그인</a>
                    <a href="/signup.do" class="btn btn-light">회원가입</a>
                </div>

                <div class = "shopping-cart">
                    <i class="bi bi-bag"></i>
                </div>
            </div>
        </div>
    </header>
</div>
</body>
</html>