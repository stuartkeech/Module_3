<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

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
   				<option value="Policy1">
   					Policy 1
   				</option>
   				<option value="Policy2">
   					Policy 2
   				</option>
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
    		
    		<div id="deathCertificateDiv">
    			<label for="deathCertificate">
    				Upload Death Certificate
    			</label>
    			<input type="file" id="deathCertificate" accept="image/*">
    		</div>
    		
    		<div id="checkDate">
    			
    		</div>
    		
    		<div id="claimExplanationDiv">
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