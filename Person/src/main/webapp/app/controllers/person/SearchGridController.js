Ext.define('MyApp.controllers.person.SearchGridController', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.searchGridController',
	selectedCellValue : '',
	init:function(){
		this.listen({
			store: {
				'#searchGridStore' : {
					load : this.onSearchStoreLoad
				}
			}
		});
		this.control({
			// widgets event handlers
		});
	},
	
	onSearchStoreLoad : function(){
		var totalCount = Ext.getCmp('centerPanel').down('grid').getStore().getTotalCount();
		var title = '<span><b>'+mvcHelper.getNiceNumberFormat(totalCount)+" Matching Records</span></b>";
		this.getViewModel().setData({gridTitle : title});
	},
	
	onCellSelect : function(view,td,colIndex,record,tr,rowIndex,e){
		e.preventDefault();
		var selectedCellValue = mvcHelper.stripHTML(record.get(view.ownerCt.columns[colIndex].dataIndex));
		this.getViewModel().setData({cellValue : selectedCellValue});
	},
	getCellValue : function(){
		return selectedCellValue
	}
});