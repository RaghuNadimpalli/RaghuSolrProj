Ext.define('MyApp.view.customer.CustomerView', {
	extend: 'Ext.view.View',
	alias: 'widget.CustomerDataView',
	requires: [
	           'Ext.window.MessageBox',
	           'MyApp.store.Customer.Customer'
	    ],
	    
	    //store: ['MyApp.store.Customer.Customer'],
	    constructor: function (config) {
	    	this.initConfig(config);
	    	return this.callParent(arguments);
	    },
	    config: {
	    	tpl: new Ext.XTemplate(
	    			'<div class="borderless"> <div class="panel-body">',
	    			'<tpl for=".">',
	    			'<tpl if="xindex === 1">',
	    			'<br>',
	    			'</tpl>',
	    			'<tpl if="this.isNewRow() || xindex === 1">',
	    			'<div class="row">',
	    			'</tpl>',
	    			'<div class="col-lg-4">',
	    			'<div class="panel panel-default">',
	    			'<div class="panel-heading">',
	    			'<h3 class="panel-title">Search Result</h3>',
	    			'</div>',
	    			'<div class="panel-body">',
	    				'First Name : {firstName}</br>',
	    				'Last Name : {lastName}',
	    			'</div>',
	    			'</div>',
	    			'</div>',
	    			'<tpl if="xindex % 3 === 0">',
	    			'</div>',
	    			'</tpl>',
	    			'</tpl>','</div></div>',{
	    				isNewRow : function(index){
	    					if(this.localIndex >= 2){
	    						this.localIndex = 0;
	    						console.log("returning true "+this.localIndex);
	    						return true;
	    					}
	    					this.localIndex ++;
	    					console.log("returning false "+this.localIndex);
	    					return false;
	    				},
	    				localIndex : 0
	    				})
	    },
	    initComponent:function () {
	    	Ext.apply(this,
	    	{
	    		id: 'CustomerMasterId',
	    		title: 'Customer Info',
	    		autoScroll: true,
	    		bodyPadding: '5 5 5 5',
	    		store: Ext.create('MyApp.store.Customer.Customer'),
	    		itemSelector: 'div.studentInfo',
	    		emptyText: 'No images available',
	    		listeners:
	    		{
	    			itemclick:
	    			{
	    				fn:function (me,record,item,index,e,eOpts){
	    					Ext.Msg.alert('Item clicked','You clicked on : '+record.get('firstName'));
	    				}
	    			}
	    		}
	    	});
	    	this.callParent(arguments);
	    }
});