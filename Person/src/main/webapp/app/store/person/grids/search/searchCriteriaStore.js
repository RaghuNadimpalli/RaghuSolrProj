Ext.define('MyApp.store.person.grids.search.searchCriteriaStore',{
	extend: 'Ext.data.BufferedStore',
	require: ['MyApp.model.person.grids.search.searchResultModel'],
	alias: 'store.SearchGridStore',
	model: 'MyApp.model.person.grids.search.searchResultModel',
	remoteGroup: true,
	storeId: 'searchGridStore',
	leadingBufferZone: 300,
	pageSize: 100,
	extraParams: {},
	proxy: {
		// load using script tags for cross domain, if the data is on the same domain as
		// this page, an Ajax proxy would be better
		type: 'ajax',
		url: 'data.html?view=true&compid=mvc.persondetails',
		headers: {
			'Content-Type' : 'application/json;charset=utf-8'
		},
		reader: {
			rootProperty: 'dataobj',
			totalProperty: 'totalCount'
		},
		actionMethods: {
			read: 'POST'
		},
		//sends single sort as multi parameter
		simpleSortMode: true,
		//sends single group as multi parameter
		simpleGroupMode: true,
		
		//This particular service cannot sort on more than one field, so grouping === sorting.
		groupParam: 'sort',
		groupDirectionParam: 'dir',
	},
	sorters: [{
		property: 'modifiedDateText',
		direction: 'ASC'
	}],
	autoLoad: true,
	listeners: {
		beforeload: function(store){
			for(key in store.payload){
				store.getProxy().setExtraParam(key, store.payload[key]);
			}
		},
		
		//This particular service cannot spot on more than one field, so if grouped, disable sorting
		groupchange: function(store, groupers){
			var sortable = !store.isGrouped(),
				headers = grid.headerCt.getVisibleGridColumns(),
				i, len = headers.length;
			
			for(i = 0; i < len; i++) {
				headers[i].sortable = (headers[i].sortable !== undefined) ? headers[i].sortable : sortable;
			}
		},
		
		//This particular service cannot spot on more than one field, so if grouped, disable sorting
		beforeprefetch: function(store, operation) {
			if(operation.getGrouper()) {
				operation.setSorters(null);
			}
		}
	}
});