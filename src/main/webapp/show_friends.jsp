<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="jsp.css" rel="stylesheet">
<title>Insert title here</title>
</head>
	<body>
		<main> 
			<p class="user"><strong><% out.println((String) request.getSession().getAttribute("user")); %></strong></p>
			<p><br><br><br></p>
			<p><% out.println((String) request.getSession().getAttribute("friend0")); %></p>
			<p><% out.println((String) request.getSession().getAttribute("friend1")); %></p>
			<p><% out.println((String) request.getSession().getAttribute("friend2")); %></p>
			<p><% out.println((String) request.getSession().getAttribute("friend3")); %></p>
			<p><% out.println((String) request.getSession().getAttribute("friend4")); %></p>
			<p><% out.println((String) request.getSession().getAttribute("code")); %></p>
			<p></p>
		</main>
	</body>
</html>