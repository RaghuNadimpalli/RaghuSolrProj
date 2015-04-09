Ext.define('MyApp.view.layout.South', {
	extend: 'Ext.panel.Panel',
	//alias allows us to define a custom xtype for this component, which we can use as a shortcut
	//for adding this component as a child of another
	alias: 'widget.layout.south',
	region: 'south',
	title: '<span class="small_header_text footer_text">@Raghu Nadimpalli 2015 | <a class="small_header_text footer_text" target="_blank" href="">BSA</a></span>',
	collapsible: false,
	split: true,
	initComponent:function(){
		var me = this;
		Ext.applyIf(me,{
			plugins : me.plugins
		});
		me.callParent( arguments );
	}
});