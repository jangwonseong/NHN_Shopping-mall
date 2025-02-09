<%-- index.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="album py-5 bg-light">
    <div class="container">
        <jsp:include page="/WEB-INF/views/shop/product/product_list.jsp"/>
    </div>
</div>

<!-- 페이징 -->
<div class="container">
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="/index.do?page=${currentPage-1}&search=${param.search}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="pageNum">
                <li class="page-item ${pageNum eq currentPage ? 'active' : ''}">
                    <a class="page-link" href="/index.do?page=${pageNum}&search=${param.search}">
                            ${pageNum}
                    </a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="/index.do?page=${currentPage+1}&search=${param.search}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
