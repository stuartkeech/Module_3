<%--Created by Chin Han Chen 2018/08/15 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Claim Rejection Reason</title>
</head>
<body>
<header>
	<h1>Claim Rejection Reason</h1>
</header>

<form action="Controller" method="get">
<textarea name="Rejection" placeholder="Please enter reason here" rows="25" cols="100"></textarea><br>
<input type="submit" value="Submit Rejection">

<%
try{
	String val = request.getAttribute("fail").toString();
	if(val.equalsIgnoreCase("fail"))
		{ %>
			<p>You input something wrong...</p>
			<%
		}
	}
catch(Exception e){
	
}
%>
</form>
</body>
</html>