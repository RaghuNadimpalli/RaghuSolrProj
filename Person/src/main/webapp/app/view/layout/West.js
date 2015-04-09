Ext.define('MyApp.view.layout.West', {
	extend: 'Ext.panel.Panel',
	//alias allows us to define a custom xtype for this component, which we can use as a shortcut
	//for adding this component as a child of another
	alias: 'widget.layout.west',
	/*bind: {
	 	title : '{name}'
	 }*/
	title : this.title,
	id : 'westPanel',
	collapseMode: 'mini',
	region: 'west',
	collapsible : true,
	width: 250,
	layout: 'accordin',
	listeners: this.listeners,
	
	/*tbar: [{
	 text: 'Button',
	 handler: 'onClickButton'
	 }],*/
	layout: 'accordion',
	items: this.items,
	initComponent: function(){
		var me = this;
		Ext.applyIf(me,{
			items : me.items,
			id : me.id,
			layout : me.layout,
			title : me.title,
			collapsible : me.collapsible,
			width : me.width,
			listeners : me.listeners
		});
		me.callParent( arguments );
	}
});