Ext.define('MyApp.view.layout.West', {
	extend: 'Ext.panel.Panel',
	//alias allows us to define a custom xtype for this component, which we can use as a shortcut
	//for adding this component as a child of another
	alias: 'widget.layout.west',
	/*bind: {
	 	title : '{name}'
	 }*/
	title : 'TRAQs',
	id : 'westPanel',
	collapseMode: 'mini',
	region: 'west',
	collapsible : true,
	width: 250,
	layout: 'accordin',
	listeners:{
		collapse : function(panel, direction) {
			var tabElement = panel.getPlaceholder().getComponent(1);
			if(tabElement.text === "TRAQs"){
				panel.getPlaceholder().remove(1);
			}
		},
		beforecollapse: function(panel, direction){
			if(!panel.placeholder){
				panel.getPlaceholder(direction).insert(3, {
					xtype: 'button',
					icon: 'resources/images/glyphicons/icons/png/glyphicons-35-old-man.png',
					scale: 'medium',
					margin: {
						bottom: 10
					},
					listeners: {
						click:function(button, e){
							e.stopEvent();
							Ext.getCmp('westPanel').expand();
							e.stopEvent();
						}
					}
				});
				
				panel.getPlaceholder(direction).insert(4, {
					xtype: 'button',
					icon: 'resources/images/glyphicons/icons/png/glyphicons-341-globe.png',
					scale: 'medium',
					margin: {
						bottom: 10
					},
					listeners: {
						click:function(button, e){
							Ext.getCmp('westPanel').expand();
							e.stopEvent();
						}
					}
				});
			}
		}
	},
	
	/*tbar: [{
	 text: 'Button',
	 handler: 'onClickButton'
	 }],*/
	layout: 'accordion',
	items: [{
		title: 'Filter 1',
		cls : 'traq-accordian-header',
		iconCls : 'accordianAlignement person',
		html: 'Empty',
		tbar: [{
			text: 'Button 1',
			handler: 'onClickButton'
		},{
			text: 'Button 2',
			handler: 'onClickButton2'
		}]
	},{
		title: 'Filter 2',
		cls : 'traq-accordian-header',
		iconCls : 'accordianAlignement bullets',
		html: 'Empty'
	}],
	initComponent: function(){
		var me = this;
		Ext.applyIf(me,{
			
		});
		me.callParent( arguments );
	}
});