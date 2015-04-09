Ext.define('MyApp.view.layout.North', {
	extend: 'Ext.panel.Panel',
	//alias allows us to define a custom xtype for this component, which we can use as a shortcut
	//for adding this component as a child of another
	alias: 'widget.layout.north',
	region: 'north',
	layout: 'fit',
	border: false,
	margin: '0 0 5 0',
	collapsible : true,
	initComponent:function(){
		var me = this;
		Ext.applyIf(me,{
			items : me.items,
			height : me.height,
			minHeight : me.minHeight,
			maxHeight : me.maxHeight,
			html : me.html,
			collapsible : me.collapsible,
			title : me.title
		});
		me.callParent( arguments );
	}
});