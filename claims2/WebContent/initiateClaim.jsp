<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Module 3</title>

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <!-- Our Custom CSS -->
    <link rel="stylesheet" href="template.css">
    <link rel="stylesheet" href="initiateClaim.css">
    <!-- Scrollbar Custom CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">

    <!-- Font Awesome JS -->
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
</head>

<body>
    <div class="wrapper">
        <!-- Sidebar  -->
        <nav id="sidebar">
            <div id="dismiss">
                <i class="fas fa-arrow-left"></i>
            </div>

            <div class="sidebar-header">
                <h1>FT</h1>
            </div>
			
			<!-- Added by Manpreet on August 17, 2018 -->
            <ul class="list-unstyled components">
                <li class="active">
                    <a href="#" >Initiate Claim</a>
                </li>
            </ul>
        </nav>

        <!-- Page Content  -->
        <div id="content">

            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <button type="button" id="sidebarCollapse" class="btn btn-info">
                        <i class="fas fa-align-left"></i>
                        <span>Menu</span>
                    </button>
					
					<h2 id="title">Initiate Claim</h2>

                    <div id="title-info">
						<p id="username">User Name</p>
						<p id="role">Role</p>
                    </div>
                </div>
            </nav>
			
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
        </div>
    </div>

    <div class="overlay"></div>

    <!-- jQuery CDN - Slim version (=without AJAX) -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <!-- Popper.JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    <!-- jQuery Custom Scroller CDN -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#sidebar").mCustomScrollbar({
                theme: "minimal"
            });

            $('#dismiss, .overlay').on('click', function () {
                $('#sidebar').removeClass('active');
                $('.overlay').removeClass('active');
            });

            $('#sidebarCollapse').on('click', function () {
                $('#sidebar').addClass('active');
                $('.overlay').addClass('active');
                $('.collapse.in').toggleClass('in');
                $('a[aria-expanded=true]').attr('aria-expanded', 'false');
            });
        });
    </script>
</body>

</html>