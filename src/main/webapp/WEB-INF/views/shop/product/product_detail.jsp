<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container py-4">
    <div class="row">
        <!-- 상품 이미지 -->
        <div class="col-md-6">
            <img src="${pageContext.request.contextPath}/resources/no-image.png"
                 class="img-fluid" alt="${product.productName}">
        </div>

        <!-- 상품 정보 -->
        <div class="col-md-6">
            <div class="mb-3">
                <small class="text-muted">${category.categoryName}</small>
                <h2 class="fw-bold">${product.productName}</h2>
            </div>

            <div class="mb-4">
                <h4 class="text-danger fw-bold">
                    ₩${product.productPrice}
                </h4>
            </div>

            <div class="mb-4">
                <p class="text-muted">${product.productDescription}</p>
            </div>

            <div class="mb-4">
                <p class="mb-2">재고: ${product.productStock}개</p>
            </div>

            <!-- 장바구니 담기 폼 -->
            <form action="/cart/add.do" method="POST" class="mb-4">
                <input type="hidden" name="productId" value="${product.productId}">

                <div class="row g-3 align-items-center mb-3">
                    <div class="col-auto">
                        <label for="quantity" class="col-form-label">수량:</label>
                    </div>
                    <div class="col-4">
                        <input type="number" id="quantity" name="quantity"
                               class="form-control" value="1" min="1" max="${product.productStock}">
                    </div>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">장바구니에 담기</button>
                    <a href="/index.do" class="btn btn-outline-secondary">목록으로 돌아가기</a>
                </div>
            </form>
        </div>
    </div>
</div>
