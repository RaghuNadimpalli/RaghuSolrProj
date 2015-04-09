Ext.define('MyApp.store.person.forms.searchCriteriaStore',{
	extend: 'Ext.data.ArrayStore',
	
	alias: 'store.searchCriteriaStore',
	
	require: ['MyApp.model.person.forms.searchCriteriaModel'],
	
	model: 'MyApp.model.person.forms.searchCriteriaModel',
	
	storeId: 'searchCriteriaStore',
	
	data: [
	    [100, 'All'],
	    [3, 'Last Three Months'],
	    [6, 'Last Six Months'],
	    [12, 'Last Year'],
	    [0, 'Custom Range']
	]
});