Ext.define('MyApp.view.common.daterange.DateRange',{
	extend: 'Ext.window.Window',
	xtype: 'daterange',
	controller: 'dateselect',
	requires: [
	           'MyApp.validators.daterange',
	           'MyApp.controllers.hornet.DateSelectController'
	],
	height: 160,
	width: 400,
	title: 'Select Custom Date Range',
	itemId: 'dateRangeWindow',
	scrollable: true,
	bodyPadding: 10,
	modal: true,
	constrain: true,
	closable: true,
	items : [{
		xtype: 'form',
		itemId: 'dateRangeForm',
		header: false,
		frame: true,
		bodyPadding: '5 5 0',
		
		style: {
			'margin-bottom': '40px'
		},
		
		fieldDefaults: {
			msgTarget: 'side',
			autoFitErrors: false
		},
		
		layout: 'form',
		
		defaultType: 'datefield',
		
		items: [{
			fieldLabel: 'Start Date',
			name: 'startdt',
			itemId: 'startdt',
			vtype: 'daterange',
			allowBlank: false,
			endDateField: 'enddt' //id of the end date field
		},{
			fieldLabel: 'End Date',
			name: 'enddt',
			itemId: 'enddt',
			vtype: 'daterange',
			allowBlank: false,
			startDateField: 'startdt' //id of the start date field
		}],
		buttons: [
		         {text:'Select', handler:'onDateSelect'},
		         {text:'Cancel', handler:'onCancel'},
		        ]
	}]
});