<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:genericpage>
	<h1>Clarification</h1>

	<form action="Controller" method="POST">
		<textarea name="clarification" placeholder="Please enter clarification here" rows="5" cols="50" required></textarea>
		<br>
		<input type="submit" value="Submit">
		<input type="hidden" name="hidden" value="submitClarification">
	</form>
</t:genericpage>