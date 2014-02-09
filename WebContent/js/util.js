define( [
	'jquery'
], function( $ ) {

	EMPTY_STRING = "---";

	htmlEncode = function( value ) {
		return $( '<div/>' ).text( value ).html();
	};

	htmlDecode = function( value ) {
		return $( '<div/>' ).html( value ).text();
	};

	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each( a, function() {
			if( o[this.name] !== undefined ) {
				if( !o[this.name].push ) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		} );
		return o;
	};

	/**
	 * 
	 * @param message
	 * @param height
	 * @param width
	 */
	showDialog = function( message, height, width ) {
		try {
			var $dialog = $( "[id=dialog]" );
			$dialog.html( message );
			$dialog.dialog( {
				height: height | 140,
				width: width | 345,
				autoOpen: true,
				modal: true
			} );
		} catch( exception ) {
			showError( "", exception );
		}
	};

	/**
	 * 
	 * @param where
	 * @param exception
	 */
	showError = function( where, exception ) {
		var msg = "There was an error on " + where;
		msg += "\n\nError description:\n" + exception.message;
		msg +=  "\n\nClick OK to continue.";

		alert( msg );
	};

} );
