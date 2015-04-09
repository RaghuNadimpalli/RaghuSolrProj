Ext.define('MyApp.view.layout.South', {
	extend: 'Ext.panel.Panel',
	//alias allows us to define a custom xtype for this component, which we can use as a shortcut
	//for adding this component as a child of another
	alias: 'widget.layout.south',
	region: 'south',
	title: '@Raghu Nadimpalli 2015',
	collapsible: false,
	split: true,
	initComponent:function(){
		var me = this;
		Ext.applyIf(me,{
		});
		me.callParent( arguments );
	}
});