<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   Trang chủ của USER
    <form action="${pageContext.request.contextPath}/logout" >
        <button type="submit">Log Out</button>
    </form>
</body>
</html>