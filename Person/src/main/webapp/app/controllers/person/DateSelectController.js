Ext.define('MyApp.controllers.person.DateSelectController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.dateselect',
	
	onDateSelect : function(button) {
		var dateRange = button.up('form').getValues();
		var startDate = Ext.Date.format(new Date(dateRange.startdt),"j-M-Y");
		var endDate = Ext.Date.format(new Date(dateRange.enddt),"j-M-Y");
		this.getViewModel().setData({dateRangeString : startDate + " to " + endDate});
		button.up('window').destroy();
	},
	onCancel : function(button){
		Ext.getCmp("dateSelector").fireEvent("boxready");
		button.up('window').destroy();
	}
});