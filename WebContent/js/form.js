/**
 * 
 */
define([
	'jquery',
	'text!templates/form.html',
	'report',
], function( $, template, Report ) {

var INPUT_TYPE = {
	TEXT : 'text',
	MULTIPLE_CHOICE : 'checkbox',
	SINGLE_CHOICE : 'radio',
};

var view = {};
var currentForm = {};
var wait = false;

function submit( event )
{
	try {
		var thisElement = this;
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
				showDialog( "Wait! Collection is being saved on the server." );
			} else {
				wait = true;
				$.ajax( {
					url: 'rest/forms/' + currentForm.id,
					type: 'POST',
					contentType: 'application/json; charset=UTF-8',
					data : JSON.stringify( [ data ] ),
					success: function( result ) {
						showDialog('Collection sent with success.');
						thisElement.reset();
						wait = false;
					},
					error: function( result ) {
						showDialog( "Error sending the collection.\nTry again." );
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

request = function( id )
{
	try {
		if( isNaN( id ) ) {
			showDialog( 'Enter a valid numeric id.' );
			return false;
		}

		var success = function( data, status, xhr ) {
			try {
				show( data );
			} catch( exception ) {
				showError( "form.request.success", exception );
			}
		};
		var error = function( jqxhr, textStatus, error ) {
			try {
				showDialog( "Could not get the form (id = " + id + ")" );
			} catch( exception ) {
				showError( "form.request.error", exception );
			}
		};

		App.reset();
		var url = "rest/forms/"+id;
		$.getJSON( url )
		.done( success )
		.fail( error );
	} catch( exception ) {
		showError( "form.request", exception );
	}
};

/**
 * 
 * @param form
 */
show = function( form )
{
	try {
		currentForm = form;
		var $content = $('[id=content]');
		view = $(template).find( '.form' ).clone();

		insertInfo( form );
		$.each( form.item, addItemToShow );

		var htmlForm = view.find('.htmlForm');
		htmlForm.submit( submit );

		var collectorInfo = view.find( '.infoCollector' );
		collectorInfo.children( 'input' ).attr( 'placeholder', 'Insert your name' );
		var collectModeLabel = view.find( '.collectModeLabel' );
		collectModeLabel.text( "Collect Mode: " );
		var collectMode = view.find( '[id=collectModeSwitch]' );
		collectMode.change( function() {
			var checked = this.checked;
			htmlForm.find( ':input' ).attr( 'disabled', !checked );
			htmlForm.trigger( 'reset' );
			collectorInfo.attr( 'hidden', !checked );
			htmlForm.find( '.htmlFormButtons' ).attr( 'hidden', !checked );
		} );
		collectMode.trigger('change');

		var reportBtn = view.find( '.reportButton' );
		reportBtn.attr( 'title', 'View Report' );
		reportBtn.change( function( event ) {
			if( this.checked ) {
				Report.request( form.id );
				$('.formContent').hide();
				this.title = "View Items";
				$('.onoffswitch').attr('hidden', true );
				collectModeLabel.fadeTo( 0, 0 );
			} else {
				$('.reportContent').html('');
				$('.formContent').show();
				this.title = "View Report";
				$('.onoffswitch').attr('hidden', false );
				collectModeLabel.fadeTo( 0, 1 );
			}
		} );

		var exportBtn = view.find( '.exportButton' );
		exportBtn.attr( 'href', 'rest/forms/' + currentForm.id + '/export' );
		exportBtn.attr( 'title', "Export Collections" );

		$content.html( view );
	} catch ( exception ) {
		showError( "form.show", exception );
	}
};

function insertInfo( form ) {
	insertFieldContent( view.find('.formId'), form.id );
	insertFieldContent( view.find('.title'), form.title );
	insertFieldContent( view.find('.creator'), form.creator );
	insertFieldContent( view.find('.creationDate'), form.timestamp );
	insertFieldContent( view.find('.description'), form.description );
}

function addItemToShow( itemIndex, item ) {
	try {
		var $itemView = $(template).find('.item').clone();
		$itemView.data( 'model', item );
		insertFieldContent( $itemView.find('.question'), item.question );
		var attr = $(item).attr('type');
		if( attr != 'TEXT' ) {
			var $options = $(document.createElement('span'));
			var insertOption = function( optionIndex, option ) {
				var $input = $('<input>' + option.value + '</option>');
				$input.addClass('input');
				$input.attr( 'type', INPUT_TYPE[attr] );
				$input.attr( 'value', option.id );
				$input.attr( 'name', itemIndex );
				$options.append( $input );
			};
			$.each( item.options, insertOption );
			$itemView.append( $options );
		} else {
			var $textArea = $(document.createElement('textArea') );
			$textArea.addClass('input');
			$textArea.attr( 'name', itemIndex );
			$itemView.append( $textArea );
		}
		view.find('.items').append( $itemView );
	} catch( exception ) {
		showError( "form.addItemToShow", exception );
	}
};

function insertFieldContent( field, value ) {
	try {
		var $field = $(field);
		if( value == null || value == "" ) {
			value = EMPTY_STRING;
		}
		$field.text( value );
	} catch( exception ) {
		showError( "form.insertFieldContent", exception );
	}
};

return { show: show,
		 request: request };

} );
