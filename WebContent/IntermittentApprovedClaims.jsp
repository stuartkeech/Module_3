<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String[][] DBout=(String[][])session.getAttribute("claims");
	
	double[] arr = (double[])session.getAttribute("EA");
	for (int i = 0; i < arr.length; i++) {
		DBout[i][2] = arr[i] + "";
	}
	session.setAttribute("DBout", DBout);
%>
<t:genericpage>
	<!-- This is where to put your main content. -->
	<div id="main-body">

		<form action="Controller" name="policyClosings" method="post">
			<table>
				<tr>
					<td>User ID</td>
					<td>Policy ID</td>
					<td>Eligible Amount</td>
				</tr>
				<c:forEach items="${DBout}" var="item">
					<tr>
						<td><c:out value="${item[0]}"></c:out></td>
						<td><c:out value="${item[1]}"></c:out></td>
						<td><c:out value="${item[2]}"></c:out></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</t:genericpage>