<%--
  Created by IntelliJ IDEA.
  User: nhnacademy
  Date: 25. 2. 6.
  Time: 오후 5:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>상품 상세</title>
</head>
<body>
<h2><%= request.getAttribute("productName") %></h2>
<p>가격: <%= request.getAttribute("productPrice") %> 원</p>
<p>카테고리: <%= request.getAttribute("productCategory") %></p>
<p>상세 정보: <%= request.getAttribute("productDescription") %></p>

<form method="post" action="/productDetail.do">
    <input type="hidden" name="productId" value="<%= request.getAttribute("productId") %>">
    <button type="submit">구매하기</button>
</form>
</body>
</html>
