<!-- /WEB-INF/views/shop/mypage/edit_address.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card">
        <div class="card-body">
          <h2 class="card-title">주소 수정</h2>

          <form action="/mypage/editAddress.do" method="POST">
            <input type="hidden" name="addressId" value="${address.addressId}"/>

            <div class="mb-3">
              <label for="streetAddress" class="form-label">상세주소:</label>
              <input type="text" id="streetAddress" name="streetAddress"
                     class="form-control" value="${address.streetAddress}" required/>
            </div>

            <div class="mb-3">
              <label for="postalCode" class="form-label">우편번호:</label>
              <input type="text" id="postalCode" name="postalCode"
                     class="form-control" value="${address.postalCode}" required/>
            </div>

            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <button type="submit" class="btn btn-primary me-md-2">수정하기</button>
              <a href="/mypage/address.do" class="btn btn-secondary">취소</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
