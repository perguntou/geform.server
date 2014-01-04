/**
 * 
 */
define( [
	'jquery',
	'underscore',
	'backbone',
	'form',
	'report',
], function( $, _, Backbone, Form, Report ) {
	var initialize = function() {
		Form.init();
		Report.init();
	};

	return {
		initialize: initialize
	};

} );
