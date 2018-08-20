<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pending Claims</title>
</head>
<body>


	<form action="Controller" name="policyClosings">
	<table>
	<tr>
		<td>
		User ID
		</td>
		<td>
		Policy ID
		</td>		
		<td>
		Claim Amount
		</td>	
		
	</tr>
	<%	
	String[][] DBout=(String[][])session.getAttribute("claims");
	for(int i=0;i<DBout.length;i++){
	%>
	<tr>
		<td>
		<%=DBout[i][0]%>
		</td>
		<td>
		<%=DBout[i][1]%>
		</td>
		<td>
		<%=DBout[i][2]%>
		</td>		
		<td>		
		<button name="info" value="<%=Integer.parseInt(DBout[i][3])%>">More Information</button>
		</td>				
	</tr>
	
	<%
	}
	%>
	</table>
	</form>
</body>
</html>