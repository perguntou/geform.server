define( [
	'jquery',
	'jquery_ui'
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
	showDialog = function( message, height, width, callback ) {
		try {
			var $dialog = $( "[id=dialog]" );
			$dialog.text( message );
			callback = callback || function() { $dialog.dialog('close'); };
			$dialog.dialog( {
				height: height || 200,
				width: width || 345,
				autoOpen: true,
				modal: true,
				buttons: { Ok: callback	}
			} );
		} catch( exception ) {
			showError( "util.showDialog", exception );
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
