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
	   'Ext.window.MessageBox',
	   'MyApp.view.common.daterange.DateRange',
	   'MyApp.controllers.person.SearchGridController'
	],
	
	alias: 'controller.main',
	
	me : this,
	
	init: function() {
		console.log('Application initialized successfully !!');
		var me = this;
		Ext.Ajax.request({
			url: 'data.html?view=true&compId=mvc.landingInfo',
			method : 'POST',
			success: function(response) {
				var lastUpdatedValue = 'Last Updated  '+Ext.decode(response.responseText).dataobj[0].lastUpdated;
				me.getViewModel().setData({lastUpdated : lastUpdatedValue});
			},
			failure: function(response) {
				Ext.messageBox.alert('Error','Problem while fetching initial page data');
			}
		});
	},
	
	onSearchWithString : function(view, rec, node, index, e){
		var selectedCellValue = this.getViewModel().getData().cellValue;
		Ext.getCmp("searchCriteria").setValue(selectedCellValue);
		var searchButton = Ext.getCmp("searchBtn");
		searchButton.fireEvent('click', searchButton);
	},
	
	onCopyCellValue: function (view, rec, node, index, e) {
		// alert("called 2");		
	},

	onDateRangeChange: function (combo,selection) {
		if(combo.getValue() == 0){
			Ext.create('MyApp.view.common.daterange.DateRange').show();
		}
		else{
			this.getViewModel().setData({dateRangeString : ''});
		}
	},
	
	jumpToRow : function() {
		var grid = Ext.getCmp("search_grid");
		grid.view.bufferedRenderer.scrollTo(Ext.getcmp('gotoLine').getValue() - 1,true);
	}
});