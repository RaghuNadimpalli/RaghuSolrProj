Ext.namespace('Common','mvcHelper');

mvcHelper.isGoodObject = function(obj){
	return !(obj === null || obj === undefined || obj === "");
}

mvcHelper.getNiceNumberFormat = function(x){
	return Ext.util.Format.number(x,'0,000');
}

mvcHelper.stripHTML = function(x){
	return Ext.util.Format.stripTags(x);
}

mvcHelper.enableCopyPaste = function(grid){
	var me = this;
	var map = new Ext.KeyMap(grid.getEl(),
			[{
				key: "c",
				ctrl:true,
				fn: function(keyCode, e) {
					var recs = grid.getSelectionModel().getSelection();
					if(recs && recs.length != 0) {
						var clipText = getCsvDataFromRecs(recs,grid.getStore(),grid.columns);
						var ta = document.createElement('textarea');
						ta.id = 'cliparea';
						ta.style.position = 'absolute';
						ta.style.left = '-1000px';
						ta.style.top = '-1000px';
						ta.value = clipText;
						document.body.appendChild(ta);
						document.designMode = 'off';
						ta.focus();
						ta.select();
						setTimeout(function(){
							document.body.removeChild(ta);
						}, 100);
					}
				}
			}
	]);
	var getCsvDataFromRecs = function(records,store,columns) {
		var clipText = '';
		var currRow = 0;//countryStore.find('id',records[0].data.id);
		for(var i=0; i<records.length; i++) {
			var rec = records[i];
			for(var j=0; j < columns.length;j++) {
				var val = rec.data[columns[j].dataIndex];
				val = Ext.util.Format.stripTags(val);
				if(val== undefined){
					continue;
				}
				clipText = clipText.concat(val,"\t");
			}
			clipText = clipText.concat("\n");
		}
		return clipText;
	}
}

mvcHelper.submitExportRequest = function(compId,actnService,exportFormat,params){
	if(!mvcHelper.isGoodObject(exportFormat)){
		Ext.MessageBox.alert('Error',"exportFormat is a required field to perform export action");
		return;
	}
	var fileExt;
	if(exportFormat == "excel"){
		fileExt = 'xlsx';
	}else if(exportFormat == "pdf"){
		fileExt = 'pdf';
	}else{
		Ext.MessageBox.alert('Error',"Unrecognized export format");
		return;
	}
	var exportApiUrl = "data."+fileExt;
	var tabType = (mvcHelper.tab == undefined || mvcHelper.tab == null) ? "summary" : mvcHelper.tab;
	Ext.apply(params,{
		exports : true,
		action_service_identifier : actnService,
		compId : compId,
		tab : tabType
	});
	submitFORM(exportApiUrl,params,'GET');
}

function submitFORM(path, params, method) {
	method = method || "post";
	var form = Ext.create('Ext.form.Panel', {
		standardSubmit: true,
		url: path,
		method: method
	});
	
	// Call the submit to begin the file download.
	form.submit({
		target: '_blank', //Avoids leaving the page.
		params: params
	});
	
	//Clean-up the form after 100 milliseconds.
	//Once the submit is called, the browser does not care anymore with the form object.
	Ext.defer(function(){
		form.close();
	}, 100);
}
mvcHelper.record = {};
mvcHelper.hyperLinkRenderer = function(value, p, record){
	return Ext.String.format(
			'<a href="#" onClick="mvcHelper.hyperLinkClickHandler(\''+'{1}'+'\',\''+'{2}'+'\')">{0}</a>',
			value,
			record.get("id"),
			mvcHelper.stripHTML(record.get("id"))
	);
},

mvcHelper.tabChangeHandler = function(tabPanel, tab){
	mvcHelper.tab = tab.name;
},

mvcHelper.hyperLinkClickHandler = function(id,custID){
	if(Ext.getCmp("detailsPane")){
		Ext.getCmp("detailsPane").destroy();
	}
	
	var params = {
			searchParam : Ext.getCmp("searchCriteria").getValue(),
			dateRange : Ext.getCmp("dateSelector").getValue(),
			id : id,
			type : "export"
	};
	Ext.create('Ext.window.Window', {
		
		width: 770,
		height: 800,
		title: 'Details For Id '+custID,
		layout: 'fit',
		modal: true,
		id: 'detailsPane',
		scrollable: true,
		bodyPadding: 10,
		constrain: true,
		closable: true,
		tools:[{
					type:'excel_export',
					tooltip: 'Export to Excel',
					handler: function(event, toolEl, panel){
						mvcHelper.submitExportRequest("mvc.infogrid","action.personDetailsExcelExport","excel",params);
					}
		},{
			type: 'pdf_export',
			tooltip: 'Export to pdf',
			handler: function(event, toolEl, panel){
				mvcHelper.submitExportRequest("mvc.infogrid","action.personDetailsPdfExport","pdf",params);
			}
		}],
		
		items : [{
			xtype:'tabpanel',
			id:'detailsTabContainer',
			listeners: { 
				'tabchange' : mvcHelper.tabChangeHandler
			},
			items: [{
				title: 'Summary Page',
				name: 'summary',
				xtype : 'cc_info',
				header : false,
				url : 'data.html?view=true&compId=mvc.infogrid',
				requestParams : {
					name : id,
					searchParam : Ext.getCmp("searchCriteria").getValue(),
					dateRange : Ext.getCmp("dateSelector").getValue(),
					id : id
				}
			}/*,{
				title: 'Extended Page',
				name: 'detailed',
				xtype : 'cc_info',
				header : false,
				url : 'data.html?view=true&compId=mvc.extendedInfoGrid',
				requestParams : {
					name : id,
					searchParam : Ext.getCmp("searchCriteria").getValue(),
					dateRange : Ext.getCmp("dateSelector").getValue(),
					id : id
				}
			}*/],
		}]
	}).show();
}