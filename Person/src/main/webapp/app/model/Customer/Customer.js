Ext.define('MyApp.model.Customer.Customer', {
	extend: 'Ext.data.Model',
	idProperty:'Id',
	fields: [
	   {  name: 'Id', type:'int', defaultValue: 0},
	   {  name: 'firstName', type:'string'},
	   {  name: 'middleName', type:'string'},
	   {  name: 'lastName', type:'string'},
	   {  name: 'birthDate', type:'date'},
	   {  name: 'address1', type:'string'},
	   {  name: 'address2', type:'string'},
	   {  name: 'city', type:'string'},
	   {  name: 'state', type:'string'}
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