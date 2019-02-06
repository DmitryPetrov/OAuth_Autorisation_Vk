<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p><% out.println("code: " + (String) request.getSession().getAttribute("code")); %></p>
<p><% out.println("access_token: " + (String) request.getSession().getAttribute("access_token")); %></p>
<p><% out.println("vkPesp: " + (String) request.getSession().getAttribute("vkPesp")); %></p>
<p><% out.println("vkUrl: " + (String) request.getSession().getAttribute("vkUrl")); %></p>
<p><% out.println("actor: " + (String) request.getSession().getAttribute("actor")); %></p>
</body>
</html>