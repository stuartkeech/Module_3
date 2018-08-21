<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="main.Claim" %>
<%@ page import="main.Customer" %>
<%@ page import="main.Policy" %>
<%@ page import="main.PolicyMap" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Claim</title>
</head>
<body>
	<%
	Claim specificClaim = (Claim) session.getAttribute("specificClaim");
	Customer specificCustomer = (Customer) session.getAttribute("specificCustomer");
	Policy specificPolicy = (Policy) session.getAttribute("specificPolicy");
	PolicyMap specificPolicyMap = (PolicyMap) session.getAttribute("specificPolicyMap");	
	%>
	<header>
		<h1>Claim with ID <% out.print(specificClaim.getClaimId()); %></h1>
	</header>

	<table border="1" width="100%">
		<tr>
			<th colspan="6">Claim Information</th>
		</tr>
		<tr>
			<th>Claim Id</th>
			<th>Claim Date</th>
			<th>Status</th>
			<th>Manager ID</th>
			<th>Reason for Claim</th>
			<th>Reason for Rejection</th>
		</tr>
		<tr>
			<td><% out.println(specificClaim.getClaimId()); %></td>
			<td><% out.println(specificClaim.getClaimDate()); %></td>
			<td><% out.println(specificClaim.getApproved()); %></td>
			<td><% out.println(specificClaim.getManagerId()); %></td>
			<td><% out.println(specificClaim.getReasonForClaim()); %></td>
			<td><% out.println(specificClaim.getReasonForRejection()); %></td>
		</tr>
	</table>
	
	<table border="1" width="100%">
		<tr>
			<th colspan="4">Policy Information</th>
		</tr>
		<tr>
			<th>Policy ID</th>
			<th>Name</th>
			<th>Type</th>
			<th>Pre-requirements</th>
		</tr>
		<tr>
			<td><% out.println(specificPolicy.getPolicyId()); %></td>
			<td><% out.println(specificPolicy.getPolicyName()); %></td>
			<td><% out.println(specificPolicy.getPolicyType()); %></td>
			<td><% out.println(specificPolicy.getPreReqs()); %></td>
		</tr>
	</table>
	
	<table border="1" width="100%">
		<tr>
			<th colspan="11">Customer Information</th>
		</tr>
		<tr>
			<th>Customer ID</th>
			<th>First Name</th>
			<th>Middle Name</th>
			<th>Last Name</th>
			<th>Date of Birth</th>
			<th>Gender</th>
			<th>Occupation</th>
			<th>Salary</th>
			<th>Marital Status</th>
			<th>Number of Children</th>
			<th>SIN</th>
		</tr>
		<tr>
			<td><% out.println(specificCustomer.getCustomerId()); %></td>
			<td><% out.println(specificCustomer.getFirstname()); %></td>
			<td><% out.println(specificCustomer.getMiddlename()); %></td>
			<td><% out.println(specificCustomer.getLastname()); %></td>
			<td><% out.println(specificCustomer.getDOB()); %></td>
			<td><% out.println(specificCustomer.getGender()); %></td>
			<td><% out.println(specificCustomer.getOccupation()); %></td>
			<td><% out.println(specificCustomer.getSalary()); %></td>
			<td><% out.println(specificCustomer.getMaritalStatus()); %></td>
			<td><% out.println(specificCustomer.getNumberChildren()); %></td>
			<td><% out.println(specificCustomer.getSIN()); %></td>
		</tr>
	</table>
	
	<form action="Controller" method="POST">
		<input type="submit" name="claimAction" value="Confirm">
		<input type="submit" name="claimAction" value="Reject">
		<input type="submit" name="claimAction" value="Go back">
		<input type="hidden" name="hidden" value="Claim">
	</form>
</body>
</html>