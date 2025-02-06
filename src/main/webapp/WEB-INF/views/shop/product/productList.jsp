<%@ page import="com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: nhnacademy
  Date: 25. 2. 6.
  Time: 오후 5:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>상품 목록</title>
</head>
<body>
<h2>상품 목록</h2>
<table border="1">
    <tr>
        <th>상품명</th>
        <th>가격</th>
        <th>카테고리</th>
        <th>상세 보기</th>
    </tr>
    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        for (Product product : products) {
    %>
    <tr>
        <td><%= product.getProductName() %></td>
        <td><%= product.getProductPrice() %> 원</td>
        <td><%= product.getCategoryId() %></td>
        <td><a href="productDetail.jsp?productId=<%= product.getProductId() %>">상세 보기</a></td>
    </tr>
    <%
        }
    %>
</table>

<br>
<a href="/addProduct.do">상품 추가하기</a>
</body>
</html>
