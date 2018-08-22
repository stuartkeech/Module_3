<%@ page import="main.Claim" %>
<%@page import="main.Customer" %>
<%@page import="main.Policy" %>
<%@page import="main.PolicyMap" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="body">
	<%
	Claim specificClaim = (Claim) session.getAttribute("specificClaim");
	Customer specificCustomer = (Customer) session.getAttribute("specificCustomer");
	Policy specificPolicy = (Policy) session.getAttribute("specificPolicy");
	PolicyMap specificPolicyMap = (PolicyMap) session.getAttribute("specificPolicyMap");
	session.setAttribute("specificClaimId", specificClaim.getClaimId());
	%>
	<header class="text-center">
		<h1>Claim with ID <% out.print(specificClaim.getClaimId()); %></h1>
	</header>

	<br>

	<table class="table table-striped">
		<thead>
			<tr>
				<th class="text-center" class="text-cetner" colspan="6">Claim Information</th>
			</tr>
			<tr>
				<th scope="col">Claim Id</th>
				<th scope="col">Claim Date</th>
				<th scope="col">Status</th>
				<th scope="col">Manager ID</th>
				<th scope="col">Reason for Claim</th>
				<th scope="col">Reason for Rejection</th>
			</tr>
		</thead>
		<tr>
			<td><% out.println(specificClaim.getClaimId()); %></td>
			<td><% out.println(specificClaim.getClaimDate()); %></td>
			<td><% out.println(specificClaim.getApproved()); %></td>
			<td><% out.println(specificClaim.getManagerId()); %></td>
			<td><% out.println(specificClaim.getReasonForClaim()); %></td>
			<td><% out.println(specificClaim.getReasonForRejection()); %></td>
		</tr>
	</table>
	
	<br>
	
	<table class="table table-striped">
		<thead>
			<tr>
				<th class="text-center" colspan="4">Policy Information</th>
			</tr>
			<tr>
				<th scope="col">Policy ID</th>
				<th scope="col">Name</th>
				<th scope="col">Type</th>
				<th scope="col">Pre-requirements</th>
			</tr>
		</thead>
		<tr>
			<td><% out.println(specificPolicy.getPolicyId()); %></td>
			<td><% out.println(specificPolicy.getPolicyName()); %></td>
			<td><% out.println(specificPolicy.getPolicyType()); %></td>
			<td><% out.println(specificPolicy.getPreReqs()); %></td>
		</tr>
	</table>
	
	<br>
	
	<table class="table table-striped">
		<thead>
			<tr>
				<th class="text-center" colspan="11">Customer Information</th>
			</tr>
			<tr>
				<th scope="col">Customer ID</th>
				<th scope="col">First Name</th>
				<th scope="col">Middle Name</th>
				<th scope="col">Last Name</th>
				<th scope="col">Date of Birth</th>
				<th scope="col">Gender</th>
				<th scope="col">Occupation</th>
				<th scope="col">Salary</th>
				<th scope="col">Marital Status</th>
				<th scope="col">Number of Children</th>
				<th scope="col">SIN</th>
			</tr>
		</thead>
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
	
	<form class="text-center" action="Controller" method="POST">
		<input type="submit" name="claimAction" value="Confirm">
		<input type="submit" name="claimAction" value="Reject">
		<input type="submit" name="claimAction" value="Go back">
		<input type="hidden" name="hidden" value="Claim">
	</form>
</c:set>

<t:genericpage>
	${ body }
</t:genericpage>