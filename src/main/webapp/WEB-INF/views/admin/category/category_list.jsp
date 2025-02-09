<%--
  Created by IntelliJ IDEA.
  User: nhnacademy
  Date: 25. 2. 9.
  Time: 오후 3:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>카테고리 관리</h2>
        <a href="/admin/category/add.do" class="btn btn-primary">
            <i class="bi bi-plus-lg"></i> 카테고리 추가
        </a>
    </div>

    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>순서</th>
                        <th>카테고리 ID</th>
                        <th>카테고리명</th>
                        <th>관리</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>${category.orderSeq}</td>
                            <td>${category.categoryId}</td>
                            <td>${category.categoryName}</td>
                            <td>
                                <a href="/admin/category/edit.do?id=${category.categoryId}"
                                   class="btn btn-sm btn-outline-primary">수정</a>
                                <a href="/admin/category/delete.do?id=${category.categoryId}"
                                   class="btn btn-sm btn-outline-danger"
                                   onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
