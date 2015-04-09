/*
 * This file is generated and updated by Sencha Cmd. You can edit this file as
 * needed for your application, but these edits will have to be merged by
 * Sencha Cmd when upgrading.
 */
Ext.application({
	name: 'MyApp',
	
	extend: 'MyApp.Application',
	
	autoCreateViewport: 'MyApp.view.main.Main',
	
 //-------------------------------------------------------------------------
    // Most customizations should be made to PersonWebApp.Application. If you need to
    // customize this file, doing so below this section reduces the likelihood
    // of merge conflicts when upgrading to new versions of Sencha Cmd.
    //-------------------------------------------------------------------------

	launch: function () {
		Ext.util.Format.thousandSeperator = ',';
		console.log("alert from app");
//		Ext.create('Ext.container.Viewport', {
//          layout: 'fit',
//          items: [
//              {
//                  title: 'Hello Ext',
//                  html : 'Hello! Welcome to Ext JS.'
//              }
//          ]
//      });
	}
});