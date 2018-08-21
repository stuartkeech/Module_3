<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericpage>
<div id="main-body">
	<form action="Controller" method="POST">
		<textarea name="clarification" placeholder="Please enter clarification here" rows="25" cols="100"></textarea>
		<input type="submit" value="Submit">
		<input type="hidden" name="hidden" value="submitClarification">
	</form>
</div>
</t:genericpage>