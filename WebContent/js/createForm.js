define([
	'jquery',
	'createQuestionView',
	'jquery_ui'
], function( $, CreateQuestionView ) {

	$( "#accordion" )
	.accordion({
		heightStyle: "content",
		header: "> div > h3",
		collapsible: true
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

//	$( "#draggable" ).draggable({
//		connectToSortable: "#accordion",
//		helper: "clone",
//		revert: "invalid"
//	})
//	.accordion({
//		heightStyle: "content",
//		header: "> div > h3"  
//	});
//	$( "h3" ).disableSelection();

	$('#addText').click( function() {
		var textCreateView = new CreateQuestionView.TextCreateView();
		textCreateView.render();
		$('.questions').append(textCreateView.el);
		$('.questions').accordion("refresh");        
	});
	$('#addSingle').click( function() {
		var singleCreateView = new CreateQuestionView.SingleCreateView();
		singleCreateView.render();
		$('.questions').append(singleCreateView.el);
		$('.questions').accordion("refresh");        
	});
	$('#addMultiple').click( function() {
		var multipleCreateView = new CreateQuestionView.MultipleCreateView();
		multipleCreateView.render();
		$('.questions').append(multipleCreateView.el);
		$('.questions').accordion("refresh");    
	});
	$('#submitForm').click( function() {
		window.alert("Form saved in server.");  
	});
});
