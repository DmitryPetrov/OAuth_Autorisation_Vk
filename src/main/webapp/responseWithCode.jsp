<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p><% out.println("actor: " + (String) request.getSession().getAttribute("actor")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend1")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend2")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend3")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend4")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend5")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend6")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend7")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend8")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend9")); %></p>
<p><% out.println("friend: " + (String) request.getSession().getAttribute("friend10")); %></p>
</body>
</html>