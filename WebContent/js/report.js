/**
 * 
 */
var init = function() {
	try {
		var $input = $('[id=form]');
		var $button = $('[id=report]');
		var callback = function() {
			try{
				var value = $input.val();
				var id = parseInt( value );
				if( isNaN( id ) ) {
					$dialog.text('Enter a valid numeric id.');
					$dialog.dialog('open');
					return;
				}
				var url = "/GeForm/rest/forms/"+id+"/report";
				var done = function( result, status ) {
					try {
						var $content = $('[id=content]');

						$content.html('');
						if( status != 'success' )
							return;

						var total = result.total;
						var $total = $(document.createElement('h1'));
						$total.text( 'Total: ' + total );
						$content.append( $total );

						var categories = [];
						var values = [];
						var collectors = result.collectors;
						for( var key in collectors ) {
							categories.push( key );
							values.push( collectors[key] );
						}
						var $collectors = $(document.createElement('div'));
						$collectors.highcharts( {
							chart: {
								type: 'column',
							},
							plotOptions: {
								column: {
									showInLegend: false,
									dataLabels: {
										enabled: true
									}
								},
							},
							xAxis: {
								categories: categories,
							},
							tooltip: {
								enabled: false
							},
							series: [{
								data: values,
							}],
							title: {
								text: 'Collectors',
							},
							credits: {
								enabled: false
							}
						} );
						$content.append( $collectors );

						$.each( result.items, function( index, element ) {
							var $itemDiv = $(document.createElement('div'));
							if( element === null ) {
								var $itemQuestion = $(document.createElement('h1'));
								$itemQuestion.text( index );
								$itemDiv.append( $itemQuestion );
								var $itemQuestion = $(document.createElement('span'));
								$itemQuestion.text( 'This item could not generate data.' );
								$itemDiv.append( $itemQuestion );
							} else {
								var data = [];
								for( var key in element ) {
									data.push( { name: key, y: element[key] } );
								}
								$itemDiv.highcharts( {
									chart: {
										type: 'pie'
									},
									plotOptions: {
										pie: {
											allowPointSelect: true,
											showInLegend: true,
											dataLabels: {
												distance: -30,
												color: 'white'
											},
											tooltip: {
												pointFormat: '<b>{point.y}</b> ({point.percentage:.0f}%)<br/>'
											}
										}
									},
						            series: [{
										data: data
									}],
									title: {
										text: index
									},
									credits: {
										enabled: false
									}
								} );
							}
							$content.append( $itemDiv );
						} );
					} catch( exception ) {
						var msg = "There was an error on report.init.callback.done.\n\nError description: " + exception.message + "\n\nClick OK to continue.\n\n";
						alert( msg );
					}
				};
				$.getJSON( url, done );
			} catch ( exception ) {
				var msg = "There was an error on report.init.callback.\n\nError description: " + exception.message + "\n\nClick OK to continue.\n\n";
				alert( msg );
			}
		};
		$button.click( callback );
	} catch( exception ) {
		var msg = "There was an error on report.init.\n\nError description: " + exception.message + "\n\nClick OK to continue.\n\n";
		alert( msg );
	}
};

$( init );