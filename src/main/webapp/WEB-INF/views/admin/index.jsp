<!-- /WEB-INF/views/admin/index.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container py-4">
    <div class="row">
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">총 상품 수</h5>
                    <p class="card-text h2">${totalProducts}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">총 회원 수</h5>
                    <p class="card-text h2">${totalUsers}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">카테고리 수</h5>
                    <p class="card-text h2">${totalCategories}</p>
                </div>
            </div>
        </div>
    </div>

    <!-- 최근 등록된 상품 목록 -->
    <div class="card mb-4">
        <div class="card-header">
            최근 등록된 상품
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th>상품명</th>
                        <th>카테고리</th>
                        <th>가격</th>
                        <th>재고</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="product" items="${recentProducts}">
                        <tr>
                            <td>${product.productName}</td>
                            <td>${categoryMap[product.categoryId]}</td>
                            <td><fmt:formatNumber value="${product.productPrice}" pattern="#,###"/>원</td>
                            <td>${product.productStock}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
