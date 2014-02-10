<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>GeForm</title>

<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="libs/jquery/ui/themes/jquery-ui.min.css" />

<script data-main="js/main" src="libs/require/require.min.js"></script>
<script src="libs/jquery/jquery-1.10.2.min.js"></script>
<script src="libs/underscore/underscore.min.js"></script>
<script src="libs/backbone/backbone.min.js"></script>
<script src="js/createQuestionView.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<style>
  /* IE has layout issues when sorting (see #5413) */
  .group { zoom: 1 }
</style>
<script>
  $(function() {
    $( "#accordion" )
      .accordion({
        heightStyle: "content",
        header: "> div > h3"
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
  });
</script>
</head>
<body>
	<div id="header">
		<h1>GeForm Web Application</h1>
		<hr/>
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