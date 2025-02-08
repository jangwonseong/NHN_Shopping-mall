<!-- /WEB-INF/views/error/error.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Error</title>
</head>
<body>
<h1>오류가 발생했습니다</h1>
<p>${errorMessage}</p>
<a href="/index.do">메인으로 돌아가기</a>
</body>
</html>
