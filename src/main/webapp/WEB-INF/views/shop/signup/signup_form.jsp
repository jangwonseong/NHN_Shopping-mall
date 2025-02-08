<%--
  Created by IntelliJ IDEA.
  User: nhnacademy
  Date: 25. 2. 6.
  Time: 오후 6:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>회원가입</h2>
    <form  method="post" action="/signup.do">
        <div class="mb-3">
            <label for="userId" class="form-label">아이디</label>
            <input type="text" class="form-control" id="userId" name="userId" required>
        </div>
        <div class="mb-3">
            <label for="userName" class="form-label">이름</label>
            <input type="text" class="form-control" id="userName" name="userName" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">비밀번호</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="mb-3">
            <label for="userBirth" class="form-label">생년월일</label>
            <input type="date" class="form-control" id="userBirth" name="userBirth" required>
        </div>
        <button type="submit" class="btn btn-primary">회원가입</button>
    </form>
    <br>
    <a href="/login.do">이미 계정이 있으신가요? 로그인</a>
</div>
</body>
</html>
