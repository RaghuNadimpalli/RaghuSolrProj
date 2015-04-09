/**
* This class is the main view for the application. It is specified in app.js as the
* "autoCreateViewport" property. That setting automatically applies the "viewport"
* plugin to promote that instance of this class to the body element.
* 
* TODO - Replace the content of this view to suite the needs of your application.
*/
Ext.define('MyApp.view.main.MainController', {
	extend: 'Ext.app.ViewController',
	
	requires: [
	   'Ext.window.MessageBox'
	],
	
	alias: 'controller.main',
	
	onClickButton2: function () {
		this.redirectTo("search");		
	},

	onClickButton: function () {
		this.redirectTo("home");		
	},
	
	routes : {
		'home' : 'onHome',
		'search' : 'onSearch',	
	},
	
	onHome : function() {
		Ext.getCmp("centerPanel").removeAll();
		Ext.getCmp("centerPanel").add({
			title : "My Panel",
			items : [{
				
			}]
		});
		Ext.getCmp("centerPanel").doLayout();
	},
	
	onSearch : function() {
		Ext.getCmp("centerPanel").removeAll();
		Ext.getCmp("centerPanel").add({
			xtype : 'panel',
			title : 'Search Results',
			collapsible : false,
			layout : 'fit',
			items : [{
				xtype : 'CustomerDataView',
			}],
			dockedItems : [{
				xtype : 'pagingtoolbar',
				dock : 'bottom',
				store: Ext.create('MyApp.store.Customer.Customer'),
				displayInfo : true,
				pageSize : 5
			}]
		});
		Ext.getCmp("centerPanel").doLayout();
	},
	onConfirm: function (choice) {
		if(choice === 'yes') {
			alert("Btn clicked handled");
		}
	}
});