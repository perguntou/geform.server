<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>GeForm</title>

<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="libs/jquery/ui/themes/jquery-ui.min.css" />
<link rel="stylesheet" href="libs/jquery/ui/themes/jquery-ui.css">

<script data-main="js/main" src="libs/require/require.min.js"></script>
<script src="libs/jquery/jquery-1.10.2.min.js"></script>
<script src="libs/jquery/jquery-1.10.2.js"></script>
<script src="libs/jquery/ui/jquery-ui.js"></script>
<script src="libs/underscore/underscore.min.js"></script>
<script src="libs/backbone/backbone.min.js"></script>
<script src="js/createQuestionView.js"></script>

<style>
  #sortable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
  #sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1em; font-size: 1.4em; height: 18px; }
  #sortable li span { position: absolute; margin-left: -1.3em; }
</style>
<script>
  $(function() {
    $( "#accordion" )
      .accordion({
        heightStyle: "content",
        header: "> div > h3",
        collapsible: true
      })
      .click( function() {
        var textCreateView = new TextCreateView();
        textCreateView.render();
        $('.group').append(textCreateView.el)
        $('.group').accordion("refresh");
      })
      .sortable({
        axis: "y",
        handle: "h3",
        stop: function( event, ui ) {
          // IE doesn't register the blur when sorting
          // so trigger focusout handlers to remove .ui-state-focus
          ui.item.children( "h3" ).triggerHandler( "focusout" );
        }
      });
    <!--
    $( "#draggable" ).draggable({
        connectToSortable: "#accordion",
        helper: "clone",
        revert: "invalid"
      })
      .accordion({
    	heightStyle: "content",
        header: "> div > h3"  
      });
    $( "h3" ).disableSelection();
    
    -->
    $('#addText').click( function() {
        var textCreateView = new TextCreateView();
        textCreateView.render();
        $('.questions').append(textCreateView.el)
        $('.questions').accordion("refresh");        
    });
    $('#addSingle').click( function() {
        var singleCreateView = new SingleCreateView();
        singleCreateView.render();
        $('.questions').append(singleCreateView.el)
        $('.questions').accordion("refresh");        
    });
    $('#addMultiple').click( function() {
        var multipleCreateView = new MultipleCreateView();
        multipleCreateView.render();
        $('.questions').append(multipleCreateView.el)
        $('.questions').accordion("refresh");    
    });
    $('#submitForm').click( function() {
    	window.alert("Form saved in server.");  
    });
  });
</script>
</head>
<body>
	<div id="header">
		<h1>GeForm Web Application</h1>
		<hr/>
	</div>

	<div>
		<p><textarea class="formTitle" rows="2" cols="80" placeholder="Insert the form title"></textarea></p>
		<p><textarea class="creator" rows="1" cols="80" placeholder="Insert your name"></textarea></p>
		<p><button id="submitForm">Save</button></p>
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