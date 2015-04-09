Ext.define('MyApp.view.layout.Center', {
	extend: 'Ext.panel.Panel',
	//alias allows us to define a custom xtype for this component, which we can use as a shortcut
	//for adding this component as a child of another
	alias: 'widget.layout.center',
	region: 'center',
	id: 'centerPanel',
	layout: 'fit',
	border: false,
	initComponent:function(){
		var me = this;
		Ext.applyIf(me,{
			items : me.items,
			id : me.id,
			layout : me.layout,
			title : me.title
		});
		me.callParent( arguments );
	}
});