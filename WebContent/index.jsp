<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>GeForm</title>
<link rel="stylesheet" href="css/style.css" />

<!-- jQuery & jQuery UI -->
<link rel="stylesheet" href="libs/jquery/ui/themes/jquery-ui.min.css" />
<script src="libs/jquery/jquery-1.10.2.min.js"></script>
<script src="libs/jquery/ui/jquery-ui.min.js"></script>

<!-- Highcharts -->
<script src="libs/highcharts/highcharts.js"></script>
<script src="libs/highcharts/highcharts-more.js"></script>
<script src="libs/highcharts/modules/exporting.js"></script>
<script src="libs/highcharts/adapters/mootools-adapter.js"></script>
<script src="libs/highcharts/adapters/prototype-adapter.js"></script>

<script src="js/form.js"></script>
<script src="js/report.js"></script>
</head>
<body>
	<div id="header">
		<h1>GeForm Web Application</h1>
		<hr/>
	</div>
	<div id="menu">
		<input id='form' placeholder='Insert the form id' />
		<button id='search'>Search</button>
		<button id='report'>Report</button>
	</div>
	<div id="content"></div>

	<div id="dialog" title="Warning"></div>
<%@ include file="includes/template.jsp" %>
</body>
</html>