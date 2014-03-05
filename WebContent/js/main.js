/**
 * 
 */
requirejs.config( {
	paths: {
		jquery: '../libs/jquery/jquery-1.10.2.min',
		jquery_ui: '../libs/jquery/ui/jquery-ui.min',
		backbone: '../libs/backbone/backbone.min',
		underscore: '../libs/underscore/underscore.min',
		text: '../libs/require/text',
		templates: '../templates',
		highcharts: '../libs/highcharts',
	},
	shim: {
		'jquery_ui': {
			deps: ['jquery']
		},
		'backbone': {
			deps: ['underscore', 'jquery'],
			exports: 'Backbone'
		},
		'underscore': {
			exports: '_'
		},
		'highcharts/modules/exporting': {
			deps: ['highcharts/highcharts']
		},
	},
} );

require( [
	'app',
], function( app ) {
	App = app;
	app.initialize();
} );
