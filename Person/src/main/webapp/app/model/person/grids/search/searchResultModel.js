Ext.define('MyApp.model.person.grids.search.searchResultModel', {
	extend: 'MyApp.model.abstract.BaseModel',
	requires: ['MyApp.model.abstract.BaseModel'
	],
	
	fields: [
		     'id','type','title','firstName','middleName','lastName','suffix','emailPromotion','additionalInfo',
		     'modifiedDateText'	     
		],
		idProperty: 'userid'
});