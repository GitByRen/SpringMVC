<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h4>Success Page</h4>

	time : ${requestScope.time }
	<br/><br/>
	
	names : ${requestScope.names }
	<br/><br/>
	
	requestUser:${requestScope.user }
	<br/>
	sessionUser:${sessionScope.user }
	
	<br/><br/>
	requestSchool:${requestScope.school }
	<br/>
	sessionSchool:${sessionScope.school }
	
	<!-- <fmt:message key="i18n.username"></fmt:message> -->
</body>
</html>