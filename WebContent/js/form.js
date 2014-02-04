/**
 * 
 */
define([
	'jquery',
	'text!templates/form.html',
	'report',
	'jquery_ui'
], function( $, template, Report ) {

var INPUT_TYPE = {
	TEXT : 'text',
	MULTIPLE_CHOICE : 'checkbox',
	SINGLE_CHOICE : 'radio',
};

var view = {};
var currentForm = {};

function submit( event )
{
	try {
		var itens = $(view).find('.item');
		var complete = true;
		var inputCollector = $('[id=content]').find('[id=collector]');
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
			$.ajax( {
				url: '/GeForm/rest/forms/' + currentForm.id,
				type: 'POST',
				contentType: 'application/json; charset=UTF-8',
				data : JSON.stringify( [ data ] ),
				success: function( json ) {
					showDialog('Collection sent with success.');
					$(view).trigger('reset');
				}
			} );
		}
	} catch ( exception ) {
		showError( "form.submit", exception );
	} finally {
	    event.preventDefault();
	}
};

show = function( form )
{
	try {
		currentForm = form;
		var $content = $('[id=content]');
		view = $(template).find( '.form' ).clone();
		view.submit( submit );
		$('#report').click( function() {
			Report.request( form.id );
		} );
		insertFieldContent( view.find('.id'), form.id );
		insertFieldContent( view.find('.title'), form.title );
		insertFieldContent( view.find('.creator'), form.creator );
		insertFieldContent( view.find('.creationDate'), form.timestamp );
		insertFieldContent( view.find('.description'), form.description );
		$.each( form.item, addItemToShow );
//		view.find(':input').attr( 'disabled',true );
		$content.html( view );
		var inputCollector = "<label for='collector'>Collector:</label><input type='text' name='collector' id='collector'>"; 
		$content.prepend( inputCollector );
	} catch ( exception ) {
		showError( "form.show", exception );
	}
};

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

return { show: show };

} );
