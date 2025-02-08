<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>마이 페이지</title>
    <link rel="stylesheet" href="/css/index.css">
</head>
<body>
<!-- 회원 정보 섹션 -->
<h1>회원 정보</h1>
<form action="/user/update.do" method="POST">
    <div class="mb-3">
        <label for="userName" class="form-label">이름:</label>
        <input type="text" id="userName" name="userName" class="form-control" value="${user.userName}" required/>
    </div>

    <div class="mb-3">
        <label for="userPassword" class="form-label">비밀번호:</label>
        <input type="password" id="userPassword" name="userPassword" class="form-control" value="${user.userPassword}" required/>
    </div>

    <div class="mb-3">
        <label for="userBirth" class="form-label">생년월일:</label>
        <input type="text" id="userBirth" name="userBirth" class="form-control" value="${user.userBirth}" required/>
    </div>

    <button type="submit" class="btn btn-primary">수정하기</button>
</form>

<hr/>

<!-- 주소 관리 섹션 -->
<h1>주소 관리</h1>
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

<h2>내 주소 목록</h2>
<ul class="list-group">
    <c:forEach var="address" items="${addresses}">
        <li class="list-group-item d-flex justify-content-between align-items-center">
            <div>
                우편번호: ${address.postalCode}<br/>
                주소: ${address.streetAddress}
            </div>
            <div>
                <a href="/mypage/editAddress.do?addressId=${address.addressId}" class="btn btn-sm btn-warning">수정</a>
                <a href="/mypage/deleteAddress.do?addressId=${address.addressId}" class="btn btn-sm btn-danger">삭제</a>
            </div>
        </li>
    </c:forEach>
</ul>

<hr/>

<!-- 주문 내역 섹션 (주석 처리된 부분은 나중에 구현) -->
<%--<h1>주문 내역</h1>--%>
<%--<ul>--%>
<%--    <c:forEach var="order" items="${orders}">--%>
<%--        <li>--%>
<%--            주문번호: ${order.id} - 주문일: ${order.orderDate} - 금액: ${order.totalAmount}--%>
<%--            <a href="/orderDetails.do?id=${order.id}">상세보기</a>--%>
<%--        </li>--%>
<%--    </c:forEach>--%>
<%--</ul>--%>

<%--<hr/>--%>

<%--<!-- 포인트 내역 섹션 -->--%>
<%--<h1>포인트 사용 내역</h1>--%>
<%--<ul>--%>
<%--    <c:forEach var="pointHistory" items="${pointHistories}">--%>
<%--        <li>--%>
<%--            포인트 사용일: ${pointHistory.date} - 사용 포인트: ${pointHistory.amount}--%>
<%--        </li>--%>
<%--    </c:forEach>--%>
<%--</ul>--%>
</body>
</html>
