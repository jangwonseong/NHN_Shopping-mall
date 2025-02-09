<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container py-4">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card">
        <div class="card-header">
          <h4 class="mb-0">상품 수정</h4>
        </div>
        <div class="card-body">
          <form action="/admin/product/update.do" method="POST">
            <input type="hidden" name="product_id" value="${product.productId}">

            <div class="mb-3">
              <label for="name" class="form-label">상품명</label>
              <input type="text" class="form-control" id="name" name="name"
                     value="${product.productName}" required>
            </div>

            <div class="mb-3">
              <label for="category_id" class="form-label">카테고리</label>
              <select class="form-select" id="category_id" name="category_id" required>
                <c:forEach var="category" items="${categories}">
                  <option value="${category.categoryId}"
                    ${category.categoryId eq product.categoryId ? 'selected' : ''}>
                      ${category.categoryName}
                  </option>
                </c:forEach>
              </select>
            </div>

            <div class="mb-3">
              <label for="price" class="form-label">가격</label>
              <input type="number" class="form-control" id="price"
                     name="price" value="${product.productPrice}" required min="0">
            </div>

            <div class="mb-3">
              <label for="stock" class="form-label">재고</label>
              <input type="number" class="form-control" id="stock"
                     name="stock" value="${product.productStock}" required min="0">
            </div>

            <div class="mb-3">
              <label for="description" class="form-label">상품 설명</label>
              <textarea class="form-control" id="description" name="description"
                        rows="3" required>${product.productDescription}</textarea>
            </div>

            <div class="mb-3">
              <label for="image" class="form-label">상품 이미지</label>
              <div class="mb-2">
                <img src="${pageContext.request.contextPath}/resources/no-image.png"
                     alt="현재 이미지" class="img-thumbnail" style="max-width: 200px;">
              </div>
              <input type="file" class="form-control" id="image" name="image"
                     accept="image/*">
              <small class="text-muted">새 이미지를 선택하지 않으면 기존 이미지가 유지됩니다.</small>
            </div>


            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <button type="submit" class="btn btn-primary">수정</button>
              <a href="/admin/product/list.do" class="btn btn-secondary">취소</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
