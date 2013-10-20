<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>GeForm</title>
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="js/form.js"></script>
</head>
<body>
	<div id="header">
		<h1>GeForm Web Application</h1>
		<hr/>
	</div>
	<div id="menu">
		<input id='form' placeholder='Insert the form id' />
		<button id='search'>Search</button>
	</div>
	<div id="content"></div>

	<div id="dialog" title="Warning"></div>
<%@ include file="includes/template.jsp" %>
</body>
</html>