<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:genericpage>
	<h1>Claim Rejection Reason</h1>

	<form action="Controller" method="post">
		<textarea name="Rejection" placeholder="Please enter reason here" rows="5" cols="50" required></textarea>
		<br>
		<input type="submit" value="Submit Rejection">
	</form>
</t:genericpage>