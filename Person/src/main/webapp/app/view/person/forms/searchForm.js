Ext.define('MyApp.view.person.forms.searchForm', {
	extend: 'Ext.form.Panel',
	xtype: 'searchForm',
	requires: [
	           'MyApp.model.abstract.BaseModel',
	           'MyApp.controllers.person.SearchActionController'
	],
	bodyPadding: '5 5 0',
	controller: 'searchActionController',
	fieldDefaults: {
		labelAlign: 'left',
		msgTarget: 'side'
	},
	
	layout: 'hbox',
	
	items: [{
		xtype: 'container',
		layout: 'hbox',
		margin: '50 0 5 350',
		items: [{
			xtype: 'textfield',
			id: 'searchCriteria',
			name: 'searchString',
			anchor: '100%',
			allowBlank: false,
			minWidth: 450,
			enableKeyEvents: true,
			listeners: {
				specialkey: function(f,e){
					if(e.getKey() == e.ENTER){
						var searchBtn = Ext.getCmp('searchBtn');
						searchBtn.fireEvent('click',searchBtn);
					}
				}
			}
		},{
			xtype: 'button',
			id: 'searchBtn',
			text: 'Search',
			margin: '0 0 0 20',
			iconCls: 'icon-search',
			listeners: {
				click: 'performSearch'
			}
			
		}]
	}]
});