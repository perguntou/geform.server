define([
	'jquery',
	'text!templates/creation.html',
	'createQuestionView',
	'form',
	'jquery_ui',
	'util'
], function( $, template, CreateQuestionView, Form ) {
	try{
		var content = $('[id=content]');
		var wait = false;

		function submit( event ) {
			try {
				var formTitle = $('.formTitle').val().trim();
				var description = $('.description').val().trim();
				var creator = $('.creator').val().trim();

				var items = $('.questions').find('.item');

				var complete = true;

				if( formTitle.length == 0 ) {
					showDialog( "The form title is missing." );
					return;
				}
				if( creator.length == 0 ) {
					showDialog( "Identify yourself to send this form." );
					return;
				}
				if( items.length == 0 ) {
					showDialog( "Insert at least one item to send this form." );
					return;
				}

				var data = {
					title: formTitle,
					description: description,
					creator: creator,
					item: []
				};

				$.each( items, function( index, item ) {
					$(item).find('.showQuestion').css("color" , "");
					var dataItem = {
							question: "",
							type: "",
							options: []
					};
					question = $(item).find('.question').val().trim();
					var options = $(item).find('.option');

					if( question.length != 0 ) {
						dataItem.question = question;
					} else {
						$(item).find('.showQuestion').css("color" , "red");
						var showQuestion = "Insert the question text" +
											"<a class=\"deleteItem\" title=\"Delete this item\"></a>";
						$(item).find('.showQuestion').html( showQuestion );
						complete = false;
					}
					if( item.attributes.type.value == "TEXT" ) {
						dataItem.type = "TEXT";
						dataItem.options = null;
					} else {
						if ( options.length < 2 ) {
							showDialog( "Need 2 or more options to send the form." );
							$(item).find('.showQuestion').css("color" , "red");
							complete = false;
						} else {
							if ( item.attributes.type.value == "SINGLE_CHOICE" ) {
								dataItem.type = "SINGLE_CHOICE";
							}
							if ( item.attributes.type.value == "MULTIPLE_CHOICE" ) {
								dataItem.type = "MULTIPLE_CHOICE";
							}
							$.each( options, function( index, option ) {
								var optionText = $(option).val().trim();
								if ( optionText.length != 0 ) {
									dataItem.options.push( { value: optionText } );
								} else {
									$(item).find('.showQuestion').css("color" , "red");
									complete = false;
								}
							} );
						}
					}
					data.item.push( dataItem );
				} );
				if( complete ) {
					if( wait ) {
						showDialog( "Wait! Form is being saved on the server." );
					} else {
						wait = true;
						$.ajax( {
							url: 'rest/forms/',
							type: 'POST',
							contentType: 'application/json; charset=UTF-8',
							data : JSON.stringify( data ),
							success: function( data, textStatus, jqXHR ) {
								showDialog( "Form created with success." );
								App.reset();
								Form.request( data );
							},
							error: function( jqXHR, textStatus, errorThrown ) {
								showDialog( "Error sending the form.\nTry again." );
							},
							complete: function( jqXHR, textStatus ) {
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
		};

		var show = function() {
			try {
				$('.appTitle').text( 'GeForm Web Application - New Form' );
				var view = $(template).find( '.creation' ).clone();
				var accordion = view.find( "#accordion" );
				accordion.accordion({
					heightStyle: "content",
					header: "> div > h3",
					collapsible: true
				});
				accordion.sortable({
					axis: "y",
					handle: "h3",
					stop: function( event, ui ) {
						// IE doesn't register the blur when sorting
						// so trigger focusout handlers to remove .ui-state-focus
						ui.item.children( "h3" ).triggerHandler( "focusout" );
					}
				});

				var questions = view.find('.questions');
				view.find('#addText').click( function() {
					var textCreateView = new CreateQuestionView.TextCreateView();
					textCreateView.render();
					questions.append( textCreateView.el );
					questions.accordion( "refresh" );
				});
				view.find('#addSingle').click( function() {
					var singleCreateView = new CreateQuestionView.SingleCreateView();
					singleCreateView.render();
					questions.append( singleCreateView.el );
					questions.accordion( "refresh" );
				});
				view.find('#addMultiple').click( function() {
					var multipleCreateView = new CreateQuestionView.MultipleCreateView();
					multipleCreateView.render();
					questions.append( multipleCreateView.el );
					questions.accordion( "refresh" );
				});

				view.find('#cancel').click( function() {
					App.reset();
				});

				wait = false;
				view.find('#submitForm').click( submit );
				content.html( view );
			} catch( exception ) {
			showError( "createForm.show", exception );
			}
		};

		return { show: show };

	} catch( exception ) {
		showError( "createForm", exception );
	}
});
