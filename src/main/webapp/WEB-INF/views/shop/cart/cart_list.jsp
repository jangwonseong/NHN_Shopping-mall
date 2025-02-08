<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container py-5">
  <h2 class="mb-4">장바구니</h2>

  <c:choose>
    <c:when test="${empty cartItems}">
      <div class="text-center py-5">
        <p class="mb-3">장바구니가 비어있습니다.</p>
        <a href="/index.do" class="btn btn-primary">쇼핑 계속하기</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="table-responsive">
        <table class="table table-hover">
          <thead class="table-light">
          <tr>
            <th>상품명</th>
            <th>단가</th>
            <th>수량</th>
            <th>금액</th>
            <th>관리</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="item" items="${cartItems}">
            <tr>
              <td>
                <div class="d-flex align-items-center">
                  <img src="${pageContext.request.contextPath}/resources/no-image.png"
                       alt="상품이미지"
                       style="width: 50px; height: 50px; object-fit: cover;">
                  <span class="ms-3">${productMap[item.productId].productName}</span>
                </div>
              </td>
              <td>
                ₩<fmt:formatNumber value="${productMap[item.productId].productPrice}" pattern="#,###" />
              </td>
              <td>
                <form action="/cart/update.do" method="POST" class="d-flex align-items-center">
                  <input type="hidden" name="productId" value="${item.productId}">
                  <input type="number" name="quantity" value="${item.quantity}"
                         min="1" max="${productMap[item.productId].productStock}"
                         class="form-control form-control-sm" style="width: 70px"
                         onchange="if(this.value > ${productMap[item.productId].productStock}) {
                                 alert('재고가 부족합니다.');
                                 this.value=${productMap[item.productId].productStock};
                                 }">
                  <button type="submit" class="btn btn-sm btn-outline-secondary ms-2">변경</button>
                </form>
              </td>
              <td>
                ₩<fmt:formatNumber value="${productMap[item.productId].productPrice * item.quantity}" pattern="#,###" />
              </td>
              <td>
                <a href="/cart/delete.do?productId=${item.productId}"
                   class="btn btn-sm btn-danger"
                   onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
              </td>
            </tr>
          </c:forEach>
          </tbody>
          <tfoot>
          <tr>
            <td colspan="3" class="text-end"><strong>총 결제금액</strong></td>
            <td colspan="2">
              <strong>₩<fmt:formatNumber value="${totalAmount}" pattern="#,###" /></strong>
            </td>
          </tr>
          </tfoot>
        </table>
      </div>

      <div class="d-flex justify-content-between mt-4">
        <a href="/index.do" class="btn btn-outline-secondary">쇼핑 계속하기</a>
        <div>
          <a href="/cart/clear.do" class="btn btn-outline-danger me-2"
             onclick="return confirm('장바구니를 비우시겠습니까?');">장바구니 비우기</a>
          <a href="/order/checkout.do" class="btn btn-primary">주문하기</a>
        </div>
      </div>
    </c:otherwise>
  </c:choose>
</div>

<script>
  // 수량 입력 시 숫자만 입력되도록 처리
  document.querySelectorAll('input[type="number"]').forEach(function(input) {
    input.addEventListener('keypress', function(e) {
      if (!/[\d]/.test(e.key)) {
        e.preventDefault();
      }
    });
  });
</script>
