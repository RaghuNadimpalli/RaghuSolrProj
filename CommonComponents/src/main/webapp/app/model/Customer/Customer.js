Ext.define('MyApp.model.Customer.Customer', {
	extend: 'Ext.data.Model',
	idProperty:'Id',
	fields: [
	   {  name: 'Id', type:'int', defaultValue: 0},
	   {  name: 'firstName', type:'String'},
	   {  name: 'middleName', type:'String'},
	   {  name: 'lastName', type:'String'},
	   {  name: 'birthDate', type:'date'},
	   {  name: 'address1', type:'String'},
	   {  name: 'address2', type:'String'},
	   {  name: 'city', type:'String'},
	   {  name: 'state', type:'String'}
	],
	
	validations: [{
		type: 'presence',
		field: 'firstName'
	}],
	
	proxy :
	{
		type : 'ajax',
		url : 'data.html?view=true&compId=mvc.custinfo',
		enablePaging: true,
		reader: {
			type : 'json',
			rootProperty : 'dataObj',
			totalProperty : 'totalCount'
		}
	}
});