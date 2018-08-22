<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
			<!-- This is where to put your main content. -->
			<div id="main-body" align="center">			
				<h2>Login Page</h2>
				<form action="Controller" method="post">
					<input type="submit" class="btn btn-outline-primary" name="loginButton" value="Login as Policy Holder">
					<input type="submit" class="btn btn-outline-primary" name="loginButton" value="Login as Manager">
					<input type="submit" class="btn btn-outline-primary" name="loginButton" value="Login as Admin">
				</form>
			</div>
</t:genericpage>