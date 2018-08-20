<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
welcome <%=session.getAttribute("id") %> you are logged in as <%=session.getAttribute("role") %>
<a href="initiateClaim.jsp">init claim</a><br>
<%if(session.getAttribute("role").equals("admin")||session.getAttribute("role").equals("manager")){ %>
<a href="RegularClosing.jsp">regular policy closing</a><br>
<a href="IntermitentClosing.jsp">intermitent policy closing</a><br>
<a href="PolicyAproval.jsp">review policies</a><br>
<%} %>
</body>
</html>