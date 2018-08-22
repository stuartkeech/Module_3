<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	session.invalidate();
%>

<t:genericpage>
	<h1>ERROR</h1>
	<p>The page you tried to reach is unavailable. Sorry for the inconvenience.</p>
</t:genericpage>