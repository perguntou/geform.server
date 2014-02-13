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
		{
			try {
				var view = {};
				
				var formTitleInput = $('.formTitle');
				
				var creatorInput = $('.creator');
				
				var itensElement = $('.questions');
				
				
				//########## Parei aqui ######################
				
				var itens = $(view).find('.item');
				
				var complete = true;
				var inputCollector = $('[id=content]').find('.collector');
				var collector = inputCollector.val().trim();
				if( collector.length == 0 ) {
					showDialog( "Identify yourself to commit this collection." );
					return;
				}
				var data = {
					form: currentForm.id,
					collector: collector,
					item: []
				};
				$.each( itens, function( index, item ) {
					var model = $(item).data( 'model' );
					var $input = $(item).find('.input');
					var answer;
					if( model.type === 'TEXT' ) {
						answer = $input.val().trim();
						if( answer.length != 0 ) {
							data.item.push( {
								answer: [answer]
							} );
						} else {
							complete = false;
						}
					} else {
						var $checkedList = $(item).find(':checked');
						if( $checkedList.length != 0 ) {
							var values = [];
							$.each( $checkedList, function( idx, checked ) {
								var value = $input.index( checked );
								values.push( value );
							} );
							data.item.push( {
								answer: values
							} );
						} else {
							complete = false;
						}
					}
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
							url: '/GeForm/rest/forms/' + currentForm.id,
							type: 'POST',
							contentType: 'application/json; charset=UTF-8',
							data : JSON.stringify( [ data ] ),
							success: function( result ) {
								showDialog('Collection sent with success.');
								itensElement.reset();
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
			} finally {
			    event.preventDefault();
			}
		};
	});
});
