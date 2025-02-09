<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<% pageContext.setAttribute("dateFormatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); %>

<div class="container py-4">
    <h2 class="mb-4">회원 관리</h2>

    <!-- 관리자 목록 -->
    <div class="card mb-4">
        <div class="card-header">
            <h5 class="mb-0">관리자 목록</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>이름</th>
                        <th>생년월일</th>
                        <th>포인트</th>
                        <th>가입일자</th>
                        <th>최근 로그인</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${adminUsers}">
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.userName}</td>
                            <td>${user.userBirth}</td>
                            <td>${user.userPoint}</td>
                            <td>${user.createdAt.format(dateFormatter)}</td>
                            <td>${user.latestLoginAt != null ? user.latestLoginAt.format(dateFormatter) : '-'}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- 일반 회원 목록 -->
    <div class="card">
        <div class="card-header">
            <h5 class="mb-0">일반 회원 목록</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>이름</th>
                        <th>생년월일</th>
                        <th>포인트</th>
                        <th>가입일자</th>
                        <th>최근 로그인</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${normalUsers}">
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.userName}</td>
                            <td>${user.userBirth}</td>
                            <td>${user.userPoint}</td>
                            <td>${user.createdAt.format(dateFormatter)}</td>
                            <td>${user.latestLoginAt != null ? user.latestLoginAt.format(dateFormatter) : '-'}</td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- 페이징 -->
    <nav class="mt-4">
        <ul class="pagination justify-content-center">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="/admin/user/list.do?page=${currentPage - 1}">이전</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="pageNum">
                <li class="page-item ${pageNum eq currentPage ? 'active' : ''}">
                    <a class="page-link" href="/admin/user/list.do?page=${pageNum}">
                            ${pageNum}
                    </a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="/admin/user/list.do?page=${currentPage + 1}">다음</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
