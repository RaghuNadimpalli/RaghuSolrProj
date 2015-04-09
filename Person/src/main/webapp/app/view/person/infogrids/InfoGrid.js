Ext.define('MyApp.view.person.infogrids.InfoGrid', {
	extend: 'Ext.grid.property.Grid',
	xtype: 'cc_info',
	autoLoad: true,
	autoShow: true,
	title: 'Details',
	loadMask: true,
	
	listeners:
	{
		render: function(grid)
		{
			grid.columns[0].flex = 1;
			grid.columns[1].flex = 2;
			grid.findPlugin('cellediting').disable();
		},
		viewready: mvcHelper.enableCopyPaste
	},
	initComponent: function(){
		var me = this;
		if(!mvcHelper.isGoodObject(me.url)){
			Ext.MessageBox.alert('Error','URL is required to render InfoGrid');
			return;
		}
		console.log("me.sortableColumns",me.sortableColumns);
		Ext.apply(me,{
			source : me.source,
			header : me.header,
			sortableColumns : false || me.sortColumns,
			loader: {
				url : me.url,
				method : 'POST',
				renderer : 'component',
				autoLoad : true,
				params : me.requestParams,
				listeners: {
					load: function (loader, response, options, eOpts ){
						me.getStore().setSortOnLoad(false);
						me.setSource(Ext.decode(response.responseText).dataobj[0]);
					}
				}
			},
		});
		me.callParent(arguments);
	}
})
