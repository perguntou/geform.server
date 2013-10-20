/**
 * 
 */
var INPUT_TYPE = {
	text : 'text',
	multiple : 'checkbox',
	single : 'radio',
};

var init = function() {
	try {
		var $input = $('[id=form]');
		var $button = $('[id=search]');
		var $dialog = $( "[id=dialog]" ).dialog(
		{
			height: 140,
			width: 345,
			autoOpen: false,
			modal: true
		} );
		var callback = function()
		{
			try {
				var value = $input.val();
				var id = parseInt( value );
				if( isNaN( id ) ) {
					$dialog.text('Enter a valid numeric id.');
					$dialog.dialog('open');
					return;
				}
				var url = "/GeForm/rest/forms/"+id;
//				window.location.replace( url );
				var done = function( result, status )
				{
					try {
						var $template = $('[id=template] .form').clone();
						var $content = $('[id=content]');
						if( result == null ) {
							$content.html("");
							$dialog.text('Could not get the form (id = ' + id + ')');
							$dialog.dialog('open');
							return;
						}
						insertFieldContent( $template.find('.id'), result.id );
						insertFieldContent( $template.find('.title'), result.title );
						insertFieldContent( $template.find('.creator'), result.creator );
						insertFieldContent( $template.find('.creationDate'), result.timestamp );
						insertFieldContent( $template.find('.description'), result.description );
						var insertItem = function( itemIndex, item ) {
							try {
								var $itemView = $('[id=template] .item').clone();
								insertFieldContent( $itemView.find('.question'), item.question );
								var attr = $(item).attr('@type');
								if( attr != 'text' ) {
									var $options = $(document.createElement('span'));
									var insertOption = function( optionIndex, option ) {
										var $input = $('<input type=' + INPUT_TYPE[attr] +' value=' + option.id + ' name=' + itemIndex + '>' + option.value +'</input>');
										$options.append( $input );
									};
									var optionArray = item.options.option.length > 1 ? item.options.option : [item.options.option];
									$.each( optionArray, insertOption );
									$itemView.append($options);
								} else {
									$itemView.append( $(document.createElement('textArea') ) );
								}
								$template.append( $itemView );
							} catch( exception ) {
								var msg = "There was an error on form.init.callback.done.insertItem.\n\nError description: "+ exception.message + "\n\nClick OK to continue.\n\n";
								alert( msg );
							}
						};
						var itemArray = result.item.length > 1 ? result.item : [result.item];
						$.each( itemArray, insertItem );
//						$template.find(':input').attr('disabled',true);
						$content.html( $template );
					} catch( exception ) {
						var msg = "There was an error on form.init.callback.done.\n\nError description: "+ exception.message + "\n\nClick OK to continue.\n\n";
						alert( msg );
					}
				};
				$.getJSON( url, done );
			} catch ( exception ) {
				var msg = "There was an error on form.init.callback.\n\nError description: "+ exception.message + "\n\nClick OK to continue.\n\n";
				alert( msg );
			}
		};
		$button.click( callback );
	} catch( exception ) {
		var msg = "There was an error on form.init.\n\nError description: "+ exception.message + "\n\nClick OK to continue.\n\n";
		alert( msg );
	}
};

function insertFieldContent( view, value ) {
	try {
		var $view = $(view);
		if( value == null || value == "" ) {
			value = "---";
		}
		$view.text( value );
	} catch( exception ) {
		var msg = "There was an error on form.insertFieldContent.\n\nError description: " + exception.message + "\n\nClick OK to continue.\n\n";
		alert( msg );
	}
};

$( init );
