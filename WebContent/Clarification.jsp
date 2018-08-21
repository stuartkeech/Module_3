<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clarification</title>
</head>
<body>
	<header>
		<h1>Clarification</h1>
	</header>
	<form action="Controller" method="POST">
		<textarea name="clarification" placeholder="Please enter clarification here" rows="25" cols="100"></textarea>
		<input type="submit" value="Submit">
		<input type="hidden" name="hidden" value="submitClarification">
	</form>
</body>
</html>