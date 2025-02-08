<%@ page import="com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
index.jsp
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="album py-5 bg-light">
    <div class="container">
        <jsp:include page="/WEB-INF/views/shop/product/product_list.jsp"/>
    </div>
</div>