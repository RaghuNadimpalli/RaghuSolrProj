Ext.define('MyApp.controllers.person.SearchActionController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.searchActionController',
	required: [
	           'MyApp.view.person.grids.searchGrid'
	         ],
	performSearch : function(){
		Ext.getCmp("centerPanel").removeAll();
		var searchCriteria = Ext.getCmp("searchCriteria").getValue();
		if(!searchCriteria){
			Ext.MessageBox.alert("Status","Please enter Search Criteria");
			return;
		}
		
		var dateRange = Ext.getCmp("dateSelector").getValue();
		if(dateRange === 0){
			dateRange = this.getViewModel().getData().dateRangeString;
		}
		var params = {
			searchParam : searchCriteria,
			dateRange : dateRange
		};
		
		var me = this;
		Ext.getCmp("centerPanel").add({
			xtype : 'ccbuffered',
			title : "Search Results",
			id : 'search_grid',
			rightClickMenu : me.getRightClickMenus(),
			tools:[
			       {
			    	   type:'excel_export',
			    	   iconCls : 'pdf',
			    	   tooltip: 'Export to Excel',
			    	   handler: function(event, toolEl, panel){
			    		   mvcHelper.submitExportRequest("mvc.persondetails","action.personResultExcelExport","excel",params);
			    	   }
			       }],
			       payload : {
			    	   dateRange : dateRange,
			    	   searchString : searchCriteria
			       }
		});
		Ext.getCmp("centerPanel").doLayout();
	},
	
	getRightClickMenus:function(){
		var copyAction = Ext.create('Ext.Action', {
			icon : 'ext-js/build/packages/ext-theme-crisp/build/resources/images/grid/drop-yes.png',
			text: 'Copy Cell Value',
			disabled: false,
			handler: 'OnCopyCellValue'
		});
		var searchAction = Ext.create('Ext.Action', {
			//icon : 'ext-js/build/packages/ext-theme-crisp/build/resources/images/grid/refresh.png',
			icon : 'ext-js/build/packages/ext-theme-crisp/build/resources/images/grid/drop-yes.png',
			text: 'Search With This Value',
			disabled: false,
			handler: 'OnSearchWithString'
		});
		return [searchAction]
	}
});