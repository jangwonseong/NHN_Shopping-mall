<!-- /WEB-INF/views/shop/mypage/address.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container">
  <h2 class="mb-4">주소 관리</h2>

  <!-- 주소 추가 폼 -->
  <div class="card mb-4">
    <div class="card-body">
      <h3 class="card-title">새 주소 추가</h3>
      <form action="/mypage/addAddress.do" method="POST">
        <div class="mb-3">
          <label for="streetAddress" class="form-label">상세주소:</label>
          <input type="text" id="streetAddress" name="streetAddress" class="form-control" required/>
        </div>

        <div class="mb-3">
          <label for="postalCode" class="form-label">우편번호:</label>
          <input type="text" id="postalCode" name="postalCode" class="form-control" required/>
        </div>

        <button type="submit" class="btn btn-primary">주소 추가</button>
      </form>
    </div>
  </div>

  <!-- 주소 목록 -->
  <div class="card">
    <div class="card-body">
      <h3 class="card-title">내 주소 목록</h3>
      <c:choose>
        <c:when test="${empty addresses}">
          <p class="text-muted">등록된 주소가 없습니다.</p>
        </c:when>
        <c:otherwise>
          <div class="list-group">
            <c:forEach var="address" items="${addresses}">
              <div class="list-group-item">
                <div class="d-flex justify-content-between align-items-center">
                  <div>
                    <p class="mb-1"><strong>우편번호:</strong> ${address.postalCode}</p>
                    <p class="mb-1"><strong>주소:</strong> ${address.streetAddress}</p>
                  </div>
                  <div>
                    <a href="/mypage/editAddress.do?addressId=${address.addressId}"
                       class="btn btn-warning btn-sm">수정</a>
                    <a href="/mypage/deleteAddress.do?addressId=${address.addressId}"
                       class="btn btn-danger btn-sm"
                       onclick="return confirm('이 주소를 삭제하시겠습니까?');">삭제</a>
                  </div>
                </div>
              </div>
            </c:forEach>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
