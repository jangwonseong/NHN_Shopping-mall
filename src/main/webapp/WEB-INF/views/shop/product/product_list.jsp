<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page import="com.nhnacademy.shoppingmall.category.domain.Category" %>
<%@ page import="java.util.Map" %>

<%
    List<Product> productList = (List<Product>) request.getAttribute("products");
    Map<String, String> categoryMap = (Map<String, String>) request.getAttribute("categoryMap");
%>

<div class="container py-4">
    <div class="row row-cols-2 row-cols-md-4 g-3">
        <% for(Product product : productList) { %>
        <div class="col">
            <div class="card border-0 shadow-sm">
                <img src="<%= request.getContextPath() %>/resources/no-image.png" class="card-img-top" alt="<%= product.getProductName() %>">
                <div class="card-body text-center">
                    <p class="text-muted mb-1"><%= categoryMap.get(String.valueOf(product.getCategoryId())) %></p>
                    <h5 class="fw-bold"><%= product.getProductName() %></h5>
                    <p class="fw-bold text-black-50"><%= String.format("%,d원", product.getProductPrice()) %></p>
                    <div class="d-flex justify-content-center">
                        <a href="/product/detail.do?id=<%= product.getProductId() %>" class="btn btn-dark btn-sm">자세히 보기</a>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>


