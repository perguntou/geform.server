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
], function( $, _, Backbone, Form, CreateForm ) {
	try {
		var ENTER_KEY_CODE = 13;

		var searchInput = $('.searchInput');
		var searchButton = $('.searchButton');
		var newFormButton = $('.newFormButton');

		function clickHandler( event ) {
			try {
				var value = searchInput.val();
				var id = parseInt( value );
				Form.request( id );
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

				newFormButton.attr( 'title', 'Create a form' );
				newFormButton.click( CreateForm.show );
			} catch( exception ) {
				showError( "app.initialize", exception );
			}
		};

		var reset = function() {
			$('[id=content]').html('');
			$('[id=header]').find('.appTitle').text( 'GeForm Web Application' );
		};

		return {
			initialize: initialize,
			reset: reset
		};
	} catch( exception ) {
		showError( "app", exception );
	}
} );
