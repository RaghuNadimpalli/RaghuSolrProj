Ext.define('MyApp.view.common.plugins.PanelHeaderTool', {
	extend: 'Ext.AbstractPlugin',
	alias: 'plugin.headericons',
	alternateClassName: 'Ext.ux.PanelHeaderExtraIcons',
	iconCls: '',
	headerButtons: [],
	init: function(panel) {
		this.panel = panel;
		this.callParent();
		panel.on('render', this.onAddIcons, this);
	},
	onAddIcons:function() {
		this.header = this.panel.getHeader();
		for(i=0;i<this.headerComponents.length;i++){
			this.headerComponents[i].componentPosition ?
				this.header.insert(this.headerComponents[i].componentPosition,this.headerComponents[i]):
				this.header.add(this.headerComponents[i]);	
		}
		
		/*this.headerComponents[0].componentPosition ?
			this.header.insert(this.headerComponents[0].componentPosition,this.headerComponents) :
				this.header.add(this.headerComponents);*/
	}
	
});