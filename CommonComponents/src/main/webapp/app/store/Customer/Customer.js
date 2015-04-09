Ext.define('MyApp.store.Customer.Customer', 
{
	extend: 'Ext.data.Store',
	alias: 'store.custStore',
	requires: [
	           'MyApp.model.Customer.Customer'
	],
	model:'MyApp.model.Customer.Customer',
	autoLoad : {start: 0, limit: 5},
	autoSync : false,
	storeId : 'Student',
	pageSize : 5
});