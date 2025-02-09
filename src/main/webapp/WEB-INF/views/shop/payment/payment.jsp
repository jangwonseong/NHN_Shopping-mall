<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container py-5">
    <div class="text-center mb-5">
        <h2>주문이 완료되었습니다</h2>
        <p class="text-muted">주문번호: ${order.orderId}</p>
    </div>

    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">주문 내역</h5>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>상품명</th>
                                <th>수량</th>
                                <th>가격</th>
                                <th>합계</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${order.orderItems}">
                                <tr>
                                    <td>${item.productName}</td>
                                    <td>${item.quantity}</td>
                                    <td>₩<fmt:formatNumber value="${item.priceAtOrder}" pattern="#,###"/></td>
                                    <td>₩<fmt:formatNumber value="${item.priceAtOrder * item.quantity}" pattern="#,###"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="3" class="text-end"><strong>총 결제금액</strong></td>
                                <td><strong>₩<fmt:formatNumber value="${order.totalPrice}" pattern="#,###"/></strong></td>
                            </tr>
                            <tr>
                                <td colspan="3" class="text-end"><strong>적립 포인트</strong></td>
                                <td><strong><fmt:formatNumber value="${order.totalPrice * 0.1}" pattern="#,###"/>P</strong></td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>

            <div class="text-center">
                <a href="/index.do" class="btn btn-primary">쇼핑 계속하기</a>
                <a href="/mypage/orders.do" class="btn btn-outline-secondary">주문 내역 보기</a>
            </div>
        </div>
    </div>
</div>
