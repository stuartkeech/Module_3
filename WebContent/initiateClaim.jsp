<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
request.setAttribute("policylist", (String[])session.getAttribute("policies"));
%>
<t:genericpage>
	<head>
		<link rel="stylesheet" href="initiateClaim.css">
		
		<!-- This part is added to make the reason for claim dynamic -->
    	<!-- Created by Chin Han Chen on 2018/08/20 -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    	<script>
			$(function(){
		        $('#claimReason').change(function(){
		            $('.reason').hide();
		            $('#' + $(this).val()).show();
		        });
		    });
		</script>
		
		<!-- This part is added to make the policy info dynamic -->
    	<!-- Created by Chin Han Chen on 2018/08/20 -->
    	<script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(document).ready(function() 
			{ 
			$('#id_trial').click(function() {
				$.ajax({
			        type: "POST",
			        url:"/Module_3/Controller",
			        data:{"CustomerID":"True"},
			        dataType: "json",
			        success: function (data) {
			            $.each(data.polData,function(i,obj)
			            {
			             var div_data="<option value="+obj.policyID+">"+obj.policyNM+"</option>";
			            $(div_data).appendTo('#policyName'); 
			            });  
			            },
			            error: function(xhr, status, error) {
			                // check status && error
			                alert(error);
			             },
			      });
			    });
			});
		</script>
	</head>
	
	<!-- This is where to put your main content. -->
	<div id="main-body">
		<form action="Controller" id="initiateClaim" method="post" enctype="multipart/form-data">
			<label for="policyName">
				Select Policy
			</label>
   			<select name="policyName" id="policyName">
   				<option>Please Choose From Below Options</option>
   			</select>
   			<input type="button" id="id_trial" name="btn_trial" value="Trial Button..">
   			
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
    			<select name="claimReason" id="claimReason">
    				<option value=null>Choose Options Below</option>
					<option value="policyholderDeath">Death of Policy Holder</option> <!-- Ask to upload death certificate -->
					<option value="maturedPolicy">Policy Matured or Expired</option> <!-- Check with System Date -->
					<option value="intermittentClaim">Intermittent Claim</option> <!-- Ask for reason in text box -->
    			</select>
    		</div>
    		
    		<div id="policyholderDeath" class="reason" style="display:none">
		    		<label for="deathCertificate">
		    			Upload Death Certificate
		    		</label>
		    	<input type="file" name="deathcert" id="deathCertificate" accept="image/*">
		    </div>
		    		
		    <div id="maturedPolicy" class="reason" style="display:none">
		    	<input type="hidden" name="maturedPolicy" value="checkdate">
		    	<b>This system will auto validate your Policy Date.</b><br>
		    </div>
		    		
		    <div id="intermittentClaim" class="reason" style="display:none">
		    		<label for="claimExplanation">
		    			Please enter the reason for Intermittent Claim
		    		</label> <br>
				<textarea name="interreason" id="claimExplanation" rows=5></textarea>
		    </div>
		    		
		    <div id="initiateClaimSubmit">
		    	<input type="submit" id="submitClaim" value="Submit Claim">
		    </div>
	    </form>
	</div>
    <div class="overlay"></div>
</t:genericpage>		