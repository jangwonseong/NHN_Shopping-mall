<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page import="com.nhnacademy.shoppingmall.category.domain.Category" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container py-4">
    <div class="row row-cols-2 row-cols-md-4 g-3">
        <c:forEach var="product" items="${products}">
            <div class="col">
                <div class="card border-0 shadow-sm">
                    <img src="${pageContext.request.contextPath}/resources/no-image.png"
                         class="card-img-top" alt="${product.productName}">
                    <div class="card-body text-center">
                        <p class="text-muted mb-1">${categoryMap[product.categoryId]}</p>
                        <h5 class="fw-bold">${product.productName}</h5>
                        <p class="fw-bold text-black-50">
                            <fmt:formatNumber value="${product.productPrice}" pattern="#,###"/>원
                        </p>
                        <div class="d-flex justify-content-center">
                            <a href="/product/detail.do?id=${product.productId}"
                               class="btn btn-dark btn-sm">자세히 보기</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- 페이징 버튼 -->
    <div class="d-flex justify-content-center mt-4">
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <!-- 이전 버튼 -->
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="/index.do?page=${currentPage - 1}&search=${param.search}"
                           aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <!-- 페이지 번호 -->
                <c:forEach begin="1" end="${totalPages}" var="pageNum">
                    <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                        <a class="page-link" href="/index.do?page=${pageNum}&search=${param.search}">
                                ${pageNum}
                        </a>
                    </li>
                </c:forEach>

                <!-- 다음 버튼 -->
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="/index.do?page=${currentPage + 1}&search=${param.search}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>