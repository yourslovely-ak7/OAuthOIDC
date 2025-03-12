<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DashBoard</title>
</head>
<body>
		<%
			if(request.getAttribute("name") == null)
			{
				response.sendRedirect("login.html");
				return;
			}
		%>

		<h1>Welcome, ${name}</h1>
		<p>First Name: ${first_name}</p>
		<p>Last Name: ${last_name}</p>
		<p>Email Address: ${email}</p>
		<p>Gender : ${gender}</p>
</body>
</html>