<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String[][] DBout=(String[][])session.getAttribute("claims");
session.setAttribute("DBout",DBout);
%>
<t:genericpage>
	<!-- This is where to put your main content. -->
	<div id="main-body">

		<form action="Controller" name="policyClosings" method="post">
			<table>
				<tr>
					<td>User ID</td>
					<td>Policy ID</td>
					<td>Claim Amount</td>

				</tr>
				<c:forEach items="${DBout}" var="item" varStatus="loop">
					<tr>
						<td><c:out value="${item[0]}"></c:out></td>
						<td><c:out value="${item[1]}"></c:out></td>
						<td><c:out value="${item[2]}"></c:out></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</t:genericpage><%@ page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>