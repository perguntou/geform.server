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

	var initialize = function() {
		try {
			var searchInput = $('.searchInput');
			var searchButton = $('.searchButton');

			searchInput.attr( 'placeholder', 'Insert the form id' );
			searchButton.click( function() {
				var value = searchInput.val();
				var id = parseInt( value );
				if( isNaN( id ) ) {
					showDialog( 'Enter a valid numeric id.' );
				} else {
					var url = "/GeForm/rest/forms/"+id;
					$.getJSON( url, function( result, status ) {
						if( result == null ) {
							var $content = $('[id=content]');
							$content.html("");
							showDialog( "Could not get the form (id = " + id + ")" );
						} else {
							Form.show( result );
						}
					} );
				}
			} );
		} catch( exception ) {
			showError( "app.initialize", exception );
		}
	};

	return {
		initialize: initialize
	};

} );
