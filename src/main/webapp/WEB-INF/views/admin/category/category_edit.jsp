<%--
  Created by IntelliJ IDEA.
  User: nhnacademy
  Date: 25. 2. 9.
  Time: 오후 4:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container py-4">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card">
        <div class="card-header">
          <h4 class="mb-0">카테고리 수정</h4>
        </div>
        <div class="card-body">
          <form action="/admin/category/update.do" method="POST">
            <input type="hidden" name="category_id" value="${category.categoryId}">

            <div class="mb-3">
              <label for="category_name" class="form-label">카테고리명</label>
              <input type="text" class="form-control" id="category_name"
                     name="category_name" value="${category.categoryName}" required>
            </div>

            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <button type="submit" class="btn btn-primary">수정</button>
              <a href="/admin/category/list.do" class="btn btn-secondary">취소</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
