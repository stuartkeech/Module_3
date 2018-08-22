<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://code.jquery.com/jquery-1.10.2.js"	type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript">
$(function(){
	$('#claimReason').change(function(){
		$('.reason').hide();
		$('#' + $(this).val()).show();	
	});
});

var a = null;
$(document).ready(function() 
{ 

if(String(session.getAttribute("managerPower")) != null){
	$('.cusIDinput').hide();
	$('#customerIDinput'.val()).show();
}
	
if(session.getAttribute("fail") == null){
		
}else{
	if(String(session.getAttribute("fail")) == "fail"){
		alert("Your Policy has not Matured yet");
		session.removeAttribute("fail");
	}
}	
	
$.ajax({
    type: "get",
    url:"Controller",
    dataType: "json",
    data:{"CustomerID":"True"},
    dataType: "json",
    success: function (data) {
    	$("#policyName").append('<option>Select policy from below</option>');
    	a = data.polData;
        $.each(data.polData,function(i,obj)
        {
         var div_data="<option value="+obj.PolicyMID+">"+obj.policyNM+"</option>";
        $(div_data).appendTo('#policyName'); 
        });  
        },
        error: function(xhr, status, error) {
            // check status && error
            alert(error);
         },
  });
});

$(function(){
    $('#policyName').change(function(){
    	var nom = "";
    	$.each(a,function(i,obj)
            {
    		if($('#policyName').val() == String(obj.PolicyMID)){
        		nom = nom +String(obj.Nominee)+ ", " ;
        		$('#matureDate').val(obj.MatureDate);
        	}
            });
    	$('#nominee').val(nom.slice(0,-1));
    	
    });
});
</script>

<link rel="stylesheet" href="initiateClaim.css">
</head>
<body>

<br><br><br><br><br>
<div id="main-body">
		<form action="Controller" id="initiateClaim" method="post" enctype="multipart/form-data">
			
			<div id="customerIDinput" class="cusIDinput" style="display:none">
				<label for="customerIDinput">Input CustomerID</label>
		    	<input type="text" name="customerIDinput">
		    </div>		
		
		
			<label for="policyName">
				Select Policy
			</label>
   			<select name="policyName" id="policyName">
   			</select>
   			
   			<!-- Use jQuery to make hidden divs visible by using ID -->
   			<div id="NoomineeInfoDiv">
    			<label for="nominee">
    				Nominee
    			</label>
   				<input type="text" id="nominee" value="" readonly>
   			</div>
   			<div id="maturedate">
    			<label for="matureDate">
    				Matured Date
    			</label>
    			<input type="text" id="matureDate" value="" readonly>
    		</div>
    		
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


</body>
</html>