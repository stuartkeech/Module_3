<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
request.setAttribute("policylist", (String[])session.getAttribute("policies"));
%>
<t:genericpage>
	<head>
		<link rel="stylesheet" href="initiateClaim.css">
	</head>
	<!-- This is where to put your main content. -->
	<div id="main-body">
		<form action="Controller" id="initiateClaim">
			<div id="customeridDiv" >
    			<label for="customerid">
    				Please enter Cusomter ID
    			</label>
    			<input type="text" id="customerid">
    		</div>
			<label for="policyName">
				Select Policy
			</label>
   			<select id="policyName">
   			<!-- Use ajax to make asynchronous request to populate option -->
   				<c:if test="${policylist}">
   					<option val="null">null</option>
   				</c:if>
   				<c:if test="${policylist}">
	   				<c:forEach var="pol" items="${policylist}">
	   					<option value=pol>${pol}</option>
	   				</c:forEach>   				
   				</c:if>
   			</select>
   			
   			<!-- Use jQuery to make hidden divs visible by using ID -->
   			<div id="policyInfoDiv">
    			<label for="nominee">
    				Nominee
    			</label>
   				<input type="text" id="nominee" value="" readonly>
    			<label for="matureDate">
    				Matured Date
    			</label>
    			<input type="text" id="matureDate" value="" readonly>
    			<label for="claimReason">
    				Reason for Claim
    			</label>
    			
    			<!-- To detect which one is clicked use jQuery -->
    			<select id="claimReason">
					<option value="policyholderDeath">Death of Policy Holder</option> <!-- Ask to upload death certificate -->
					<option value="maturedPolicy">Policy Matured or Expired</option> <!-- Check with System Date -->
					<option value="intermittentClaim">Intermittent Claim</option> <!-- Ask for reason in text box -->
    			</select>
    		</div>
    		
    		<!-- This part is added to make the reason for claim dynamic -->
    		<!-- Created by Chin Han Chen on 2018/08/20 -->
    		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    		<script>
			$(function() {
		        $('#claimReason').change(function(){
		            $('.reason').hide();
		            $('#' + $(this).val()).show();
		        });
		    });
			</script>
    		<div id="policyholderDeath" class="reason" style="display:none">
		    		<label for="deathCertificate">
		    			Upload Death Certificate
		    		</label>
		    	<input type="file" id="deathCertificate" accept="image/*">
		    </div>
		    		
		    <div id="maturedPolicy" class="reason" style="display:none">
		    			
		    </div>
		    		
		    <div id="intermittentClaim" class="reason" style="display:none">
		    		<label for="claimExplanation">
		    			Please enter the reason for Intermittent Claim
		    		</label> <br>
				<textarea id="claimExplanation"  rows=5></textarea>
		    </div>
		    		
		    <div id="initiateClaimSubmit">
		    	<input type="submit" id="submitClaim" value="Submit Claim">
		    </div>
	    </form>
	</div>
    <div class="overlay"></div>
</t:genericpage>		