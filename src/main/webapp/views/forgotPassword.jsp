<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reset Password</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/forgot-password" method="post">
		<div id="alert">${alert}</div>

		<label for="password">Password</label> 
		<input type="password" name="password" id="password" required> 

		<label for="password-verify">Re-type Password</label> 
		<input type="password" name="password-verify" id="password-verify" required>

		<button type="submit">Reset password</button>
	</form>
</body>
</html>
