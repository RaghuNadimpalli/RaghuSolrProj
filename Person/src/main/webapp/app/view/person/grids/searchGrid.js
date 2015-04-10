Ext.define('MyApp.view.person.grids.searcgGrid', {
	extend: 'Ext.grid.Panel',
	requires: [
	          'MyApp.model.abstract.BaseModel',
	          'MyApp.store.person.grids.search.searchCriteriaStore',
	          'MyApp.view.person.infogrids.InfoGrid',
	          'Ext.toolbar.Paging',
	          'MyApp.controllers.person.SearchGridController'
	],
	xtype: 'ccbuffered',
	
	controller: 'searchGridController',
	
	bind:{
		title: '{gridTitle}'
	},
	
	listeners: {
		viewready: mvcHelper.enableCopyPaste
	},
	
	loadMask: true,
	//header : false,
	bodyPadding: '15 0 0',
	
	selModel:{
		pruneRemoved: false
	},
	multiSelect: true,
	
	/*features: [{
		ftype: 'grouping',
		hideGroupedHeader: true
	}],*/
	//plugins: 'gridfilters',
	
	columns:[{
		xtype: 'rownumberer',
		width: 50,
		sortable: false
	},{
		tdCls: 'x-grid-cell-topic',
		text: "Person Id",
		dataIndex: 'id',
		autoSizeColumn: true,
		renderer: mvcHelper.hyperLinkRenderer,
		sortable: true
	},{
		text: "Type",
		dataIndex: 'type',
		autoSizeColumn: true,
		hidden: false,
		sortable: true,
		groupable: false
	},{
		text: "Title",
		dataIndex: 'title',
		autoSizeColumn: true,
		hidden: false,
		sortable: true,
		groupable: false
	},{
		text: "First Name",
		dataIndex: 'firstName',
		autoSizeColumn: true,
		hidden: false,
		sortable: true,
		groupable: false
	},{
		text: "Middle Name",
		dataIndex: 'middleName',
		autoSizeColumn: true,
		hidden: false,
		sortable: true,
		groupable: false
	},{
		text: "Last Name",
		dataIndex: 'lastName',
		autoSizeColumn: true,
		hidden: false,
		sortable: true,
		groupable: false
	},{
		text: "Suffix",
		dataIndex: 'suffix',
		autoSizeColumn: true,
		hidden: false,
		sortable: true,
		groupable: false
	},{
		text: "Email Promotion",
		dataIndex: 'emailPromotion',
		autoSizeColumn: true,
		hidden: false,
		sortable: true,
		groupable: false
	},{
		text: "Additional Info",
		dataIndex: 'additionalInfo',
		autoSizeColumn: true,
		hidden: false,
		sortable: true,
		groupable: false
	},{
		text: "Modified Date",
		dataIndex: 'modifiedDateText',
		autoSizeColumn: true,
		hidden: false,
		sortable: true,
		groupable: false
	}],
	dockedItems: this.dockedItems,
	
	initComponent: function(){
		var me = this;
		var store = Ext.create('MyApp.store.person.grids.search.searchCriteriaStore',{
			payload : me.payload
		});
		
		gridFunc = {
				self: this,
				grid: function(){
					return Ext.getCmp('myPanel')
				},
				contextMenu: Ext.create('Ext.menu.Menu', {
					items: me.rightClickMenu
				})
		},
		
		viewConfig = {
				stripeRows: true,
				forceFit: true,
				listeners: {
					itemcontextmenu: function(view, rec, node,index, e){
						e.stopEvent();
						gridFunc.contextMenu.showAt(e.getXY());
						return false;
					},
					refresh : function (dataview) {
						Ext.each(dataview.panel.columns, function (column){
							if(column.autoSizeColumn === true)
								column.autoSize();
						})
					},
					cellcontextmenu: 'onCellSelect'
				}
		},
		Ext.applyIf(me,{
			payload : me.payload,
			tools : me.tools,
			id : me.id,
			viewConfig : viewConfig,
			store: store
		});
		
		me.callParent( arguments );
	}
	
});