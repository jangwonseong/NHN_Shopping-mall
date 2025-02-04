<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <link rel="stylesheet" href="/css/index.css">
</head>
<body>
    <!-- Main Content -->
<%--    <main>--%>
<%--        <div class="album py-5 bg-light">--%>
<%--            <div class="container">--%>
<%--                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">--%>
<%--                    <!-- Product Cards -->--%>
<%--                    <c:forEach items="${products}" var="product">--%>
<%--                        <div class="col">--%>
<%--                            <div class="card shadow-sm">--%>
<%--                                <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">--%>
<%--                                <div class="card-body">--%>
<%--                                    <p class="product-brand">${product.brand}</p>--%>
<%--                                    <h5 class="product-name">${product.name}</h5>--%>
<%--                                    <p class="product-price">--%>
<%--                                        <strong>₩<fmt:formatNumber value="${product.price}" pattern="#,###"/></strong>--%>
<%--                                    </p>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </c:forEach>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </main>--%>

<%--    <!-- Footer -->--%>
<%--    <footer class="text-muted py-5 bg-dark">--%>
<%--        <div class="container">--%>
<%--            <p class="float-end mb-1">--%>
<%--                <a href="#" class="text-white">Back to top</a>--%>
<%--            </p>--%>
<%--            <p class="mb-1 text-white-50">KREAM Clone Project - Sample Implementation</p>--%>
<%--        </div>--%>
<%--    </footer>--%>
</body>
</html>