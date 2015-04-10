/**
* This class is the main view for the application. It is specified in app.js as the
* "autoCreateViewport" property. That setting automatically applies the "viewport"
* plugin to promote that instance of this class to the body element.
* 
* TODO - Replace this content of this view to suite the needs of your application.
*/
Ext.define('MyApp.view.main.Main', {
	extend: 'Ext.container.Container',
	requires: [
	           'MyApp.view.main.MainController',
	           'MyApp.view.main.MainModel',
	           'MyApp.view.layout.North',
	           'MyApp.view.layout.Center',
	           'MyApp.view.layout.West',
	           'MyApp.view.layout.South',
	           'MyApp.view.customer.CustomerView',
	           'MyApp.view.person.forms.searchForm',
	           'MyApp.view.common.plugins.PanelHeaderTool',
	           'MyApp.store.person.forms.searchCriteriaStore',
	 ],
	 
	 xtype: 'app-main',
	 id: 'parentContainer',
	 controller: 'main',
	 border: false,
	 style: {
		 padding: '0px 10px 0px 10px'
	 },
	 viewModel: {
		 type: 'main'
	 },
	 
	 layout: {
		type: 'border' 
	 },
	 
	 items: [
			{
				 xtype: 'layout.north',
				 collapsible : false,
				 items : [{
					 xtype: 'panel',
					 id : 'header_panel',
					 height: 62,
					 minHeight: 62,
					 maxHeight: 62,
					 bodyPadding: 3,
					 bind: {
						 html:'{headerHTML}'
					 },
					 //html: this.headerHtml,
					 cls: 'header',
					 border: false,
				 }]
			},
			{
	        	 xtype: 'layout.center',
	        	 id : 'centerContainer',
	        	 border: false,
	        	 items : [{
	        		 
	        		 layout : 'border',
	        		 border: false,
	        		 items : [{
	        			 xtype: 'layout.north',
	        			 collapsible : true,
	        			 
	        			 plugins: [{
	        				 ptype: "headericons",
	        				 headerComponents : [{
	        					 xtype: 'label',
	        					 componentPosition: 1,
	        					 bind: {
	        						 text: '{lastUpdated}'
	        					 },
	        					 margin : '0 0 0 -21',
	        					 padding : '0 0 0 20',
	        					 cls: 'last_updates'
	        				 },
	        				 {
	        					 xtype: 'combobox',
	        					 id: 'dateSelector',
	        					 componentPosition : 2,
	        					 reference: 'dateselector',
	        					 publishes: 'value',
	        					 displayField: 'dateSelector',
	        					 valueField: 'id',
	        					 anchor: '-5',
	        					 store: {
	        						 type: 'searchCriteriaStore'
	        					 },
	        					 minChars: 0,
	        					 queryMode: 'local',
	        					 listeners: {
	        						 select: 'onDateRangeChange',
	        						 boxready: function(){
	        							 this.setValue(this.getStore().getAt(0).get(this.valueField),true);
	        							 this.fireEvent('select',this);
	        						 }
	        					 },
	        					 typeAhead: false,
	        					 editable: false
	        				 },{
	        					 xtype: 'label',
	        					 componentPosition: 3,
	        					 bind: {
	        						 text:'{dateRangeString}'
	        					 },
	        					 margins: '0 0 0 20',
	        					 padding: '0 0 0 20'
	        				 }
	        				]
	        			 }],
	        			 //border : false,
	        			 items: [{
	        				 height: 125,
	        				 minHeight: 100,
	        				 maxHeight: 170,
	        				 xtype: 'searchForm',
	        				 border: false
	        			 }]
	        		 },{
	        			 xtype: 'layout.center',
	        			 itemId : 'centerPanel'
	        	  }]
	         }]		 
            },
            { 
            	xtype: 'layout.south',
            	plugins: [{
            		ptype: "headericons",
            		headerComponents : [{
            			labelWidth: 80,
            			fieldLabel: 'Jump to row',
            			xtype: 'numberfield',
            			minValue: 1,
            			//maxValue: max,
            			allowDecimals: false,
            			id: 'gotoLine',
            			enableKeyEvents: true,
            			listeners: {
            				specialkey: function(field, e){
            					if(e.getKey() === e.ENTER) {
            						var goToBtn = Ext.getCmp('gotoBtn');
            						goToBtn.fireEvent('click',goToBtn);
            					}
            				}
            			}
            		},{
            			text: 'Go',
            			id: 'gotoBtn',
            			handler: 'jumpToRow',
            			hidden : true,
            			listeners: {
            				click: 'jumpToRow'
            			}
            			
            		}]
            	}]
            }]
});