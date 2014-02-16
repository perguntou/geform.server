/**
 * 
 */
define( [
	'jquery',
	'underscore',
	'backbone',
	'form',
	'createForm',
	'util'
], function( $, _, Backbone, Form ) {
	try {
		var ENTER_KEY_CODE = 13;

		var searchInput = $('.searchInput');
		var searchButton = $('.searchButton');

		function clickHandler( event ) {
			try {
				var value = searchInput.val();
				var id = parseInt( value );

				if( isNaN( id ) ) {
					showDialog( 'Enter a valid numeric id.' );
					return false;
				}

				var success = function( data, status, xhr ) {
					try {
						Form.show( data );
					} catch( exception ) {
						showError( "app.clickHandler.success", exception );
					}
				};
				var error = function( jqxhr, textStatus, error ) {
					try {
						var $content = $('[id=content]');
						$content.html("");
						showDialog( "Could not get the form (id = " + id + ")" );
					} catch( exception ) {
						showError( "app.clickHandler.error", exception );
					}
				};

				var url = "rest/forms/"+id;
				$.getJSON( url )
				.done( success )
				.fail( error );
			} catch( exception ) {
				showError( "app.clickHandler", exception );
			}
		}

		var initialize = function() {
			try {
				searchInput.attr( 'placeholder', 'Insert the form id' );
				searchButton.click( clickHandler );
				searchButton.attr( 'title', 'Find form' );
				searchInput.keypress( function( event ) {
					if( event.which == ENTER_KEY_CODE || event.keyCode == ENTER_KEY_CODE ) {
						searchButton.click();
					}
				} );
			} catch( exception ) {
				showError( "app.initialize", exception );
			}
		};

		return {
			initialize: initialize
		};
	} catch( exception ) {
		showError( "app", exception );
	}
} );
