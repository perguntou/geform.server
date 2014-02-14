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
	
	var wait = false;
	$('#submitForm').click( function() {
			try {
				
				var formTitle = $('.formTitle').val().trim();
				var description = $('.description').val().trim();
				var creator = $('.creator').val().trim();
				
				var itemsElement = $('.questions');

				var items = itemsElement.find('.item');
				
				var question = "";
				var type = "";

				var complete = true;

				if( formTitle.length == 0 ) {
					alert( "Insert the form title to commit." );
					return;
				}
				if( description.length == 0 ) {
					alert( "Insert the form description to commit." );
					return;
				}
				if( creator.length == 0 ) {
					alert( "Identify yourself to commit this form." );
					return;
				}
				
				var data = {
					title: formTitle,
					description: description,
					creator: creator,
					items: []
				};
				
				var dataItem = {
					question: question,
					type: type,
					options: []
				};
				
				
				$.each( items, function( index, item ) {
					question = $(item).find('.question').val().trim();
					var options = $(item).find('.option');
					
					if( question.length != 0 ) {
						dataItem.question = question;
					} else {
						complete = false;
					}
					if( options.length === 0 ) {
						dataItem.type = "text";
					} else {
						if ( options.length < 2 ) {
							alert("Need 2 or more options to send a form.");
							complete = false;
						} else {
							if( $(item).find('.singleChoiceOption').length !== 0 ) {
								dataItem.type = "single";
							} else {
								dataItem.type = "multiple";
							}
							$.each( options, function( index, option ) {
								var optionText = $(option).val().trim();
								if ( optionText.length != 0 ) {
									dataItem.options.push( { option: [optionText] } );
								} else {
									complete = false;
								}
							} );
						}
					}
					data.items.push( { items: dataItem } );
					return complete;
				} );
				if( !complete ) {
					showDialog( "All items must be answered before commit." );
				} else {
					if( wait ) {
						window.alert("Wait! Collection is being saved on the server.");
					} else {
						wait = true;
						$.ajax( {
							url: '/GeForm/rest/forms/',
							type: 'POST',
							contentType: 'application/json; charset=UTF-8',
							data : JSON.stringify( [ data ] ),
							success: function( result ) {
								showDialog('Collection sent with success.');
								$.each( items, function( index, item ) {
									$(item).remove();
								} );
								wait = false;
							},
							error: function( result ) {
								window.alert("Error sending the collection.\nTry again.");
								wait = false;
							}
						} );
					}
				}
			} catch ( exception ) {
				showError( "form.submit", exception );
			} 
			finally {
			    event.preventDefault();
			}
	});
});
