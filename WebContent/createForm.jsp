<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>GeForm</title>

<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="libs/jquery/ui/themes/jquery-ui.min.css" />

<script data-main="js/main" src="libs/require/require.min.js"></script>
</head>
<body>
	<div id="header">
		<h1>GeForm Web Application</h1>
		<hr/>
	</div>

	<div>
		<p><textarea class="formTitle" rows="2" cols="80" placeholder="Insert the form title"></textarea></p>
		<p><textarea class="description" rows="3" cols="80" placeholder="Insert the form description"></textarea></p>
		<p><textarea class="creator" rows="1" cols="40" placeholder="Insert your name"></textarea></p>
		<p><button id="submitForm">Save</button><button id="cancel">Cancel</button></p>
	</div>

	<div>
	    <button id="addText">Add Text Question</button>
	    <button id="addSingle">Add Single Choice Question</button>
	    <button id="addMultiple">Add Multiple Choice Question</button>
	</div>

<!--
<div id="draggable" >
  <div>
   <h3>Text Question</h3>
   <div>
     <p>
     Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer
     ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sit
     amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut
     odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.
     </p>
   </div>
  </div>
</div>
-->

<div id="accordion" class="questions">
   
</div>

</body>
</html>