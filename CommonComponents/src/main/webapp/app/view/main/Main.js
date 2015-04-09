/**
* This class is the main view for the application. It is specified in app.js as the
* "autoCreateViewport" property. That setting automatically applies the "viewport"
* plugin to promote that instance of this class to the body element.
* 
* TODO - Replace the content of this view to suite the needs of your application.
*/
Ext.define('MyApp.view.main.Main', {
	extend: 'Ext.container.Container',
	required: [
	           'MyApp.view.main.MainController',
	           'MyApp.view.main.MainModel',
	           'MyApp.view.layout.North',
	           'MyApp.view.layout.Center',
	           'MyApp.view.layout.West',
	           'MyApp.view.layout.South',
	           'MyApp.view.customer.CustomerView'
	 ],
	 
	 xtype: 'app-main',
	 id: 'parentContainer',
	 controller: 'main',
	 viewModel: {
		 type: 'main'
	 },
	 
	 layout: {
		type: 'border' 
	 },
	 
	 items: [
	         {
	        	 xtype: 'layout.west'
	         },
	         {
	        	 xtype: 'layout.north',
	        	 collapsible : false,
	        	 items : [{
	        		 xtype: 'panel',
	        		 bodyPadding: 5,
	        		 html: '<img src="resources/images/header/barclays_logo.gif"/><h1> | TRAQs</h1>',
	        		 cls: 'header',
	        		 border: false,
	        	 }]
	         },
	         {
	        	 xtype: 'layout.center',
	        	 id : 'centerContainer',
	        	 items : [{
	        		 
	        		 layout:  'border',
        			 items : [{
        				 xtype: 'layout.north',
        				 collapsible : true,
        				 title : "Global Search",
        				 items : [{
        					 height: 100,
        					 minHeight: 75,
        					 maxHeight: 150,
        					 html: '<p>Header content</p>'
        				 }]
        			 },{
        				 xtype: 'layout.center',
        	        	 id : 'centerPanel',
	        	  }]
	         }]		 
	         },
	         { xtype: 'layout.south' }
	         ]
});