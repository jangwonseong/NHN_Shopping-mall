<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container py-4">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2>상품 관리</h2>
    <a href="/admin/product/add.do" class="btn btn-primary">
      <i class="bi bi-plus-lg"></i> 상품 추가
    </a>
  </div>

  <div class="card">
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>상품 ID</th>
            <th>상품명</th>
            <th>카테고리</th>
            <th>가격</th>
            <th>재고</th>
            <th>관리</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="product" items="${products}">
            <tr>
              <td>${product.productId}</td>
              <td>${product.productName}</td>
              <td>${categoryMap[product.categoryId]}</td>
              <td><fmt:formatNumber value="${product.productPrice}" pattern="#,###"/>원</td>
              <td>${product.productStock}</td>
              <td>
                <a href="/admin/product/edit.do?id=${product.productId}"
                   class="btn btn-sm btn-outline-primary">수정</a>
                <a href="/admin/product/delete.do?id=${product.productId}"
                   class="btn btn-sm btn-outline-danger"
                   onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
