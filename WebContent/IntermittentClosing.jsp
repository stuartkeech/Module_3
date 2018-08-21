<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericpage>
	<!-- This is where to put your main content. -->
	<div id="main-body">
		<div align="center">
			<form action="Controller" method="post">
				<button name="info" value="Pending">View Pending Claims</button>
				<button name="info" value="Approved">View Approved Claims</button>
				<button name="info" value="Rejected">View Rejected Claims</button>
			</form>
		</div>
	</div>
</t:genericpage>