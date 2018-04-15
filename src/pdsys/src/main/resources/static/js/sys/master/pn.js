$(document).ready(function(){
	$("#addPn").click(function(){
		var self = $(this);
		var dlg = new CommonDlg();
		
		var fields = [
			{
				"name":"pn",
				"label":"品番",
				"type":"text",
				"requried":"requried",
			},
			{
				"name":"name",
				"label":"名称",
				"type":"text",
				"requried":"requried",
			},
			{
				"name":"unit.id",
				"label":"单位",
				"type":"select",
				"groupButtons": createUnitGroupButtons({
					"target":"unit_dlg_div",
					"success": function(action, data) {
						if(action == "add") {
							dlg.rebuildFieldWithValue("unit.id", data.unit.id);
						}
					}
				}),
				"ajax":true,
				"url":"/unit/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.forEach(function(unit, idx) {
						thisField.options.push({
							"value": unit.id,
							"caption":unit.name,
						});
					});
				}
			},
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加品番",
			"fields":fields,
			"url":"/pn/add",
			"success": function(data) {
				dlg.hide();
				PdSys.success({
					"ok" : function() {
						PdSys.refresh();
					}
				});
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});

	$("#editPn").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true,"showMsg":true});
		if(selIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		var id = selIds[0];
		var fields = [
			{
				"name":"id",
				"type":"hidden",
				"value":selIds[0]
			},
			{
				"name":"pn",
				"label":"品番",
				"type":"text",
				"value":"",
				"depend":true,
				"requried":"requried",
			},
			{
				"name":"name",
				"label":"名称",
				"type":"text",
				"value":"",
				"depend":true,
				"requried":"requried",
			},
			{
				"name":"unit.id",
				"label":"单位",
				"type":"select",
				"value":"",
				"groupButtons": createUnitGroupButtons({
					"target":"unit_dlg_div",
					"success": function(action, data) {
						if(action == "add") {
							dlg.rebuildFieldWithValue("unit.id", data.unit.id);
						}
					}
				}),
				"ajax": true,
				"depend":true,
				"url":"/unit/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.forEach(function(unit, idx) {
						thisField.options.push({
							"value": unit.id,
							"caption":unit.name,
						});
					});
				}
			}
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"编辑品番",
			"fields":fields,
			"ajax": {
				"url":"/pn/get",
				"data":{
					"id":id
				},
				"convertAjaxData":function(data) {
					dlg.rebuildFieldWithValue("pn", data.pn.pn);
					dlg.rebuildFieldWithValue("name", data.pn.name);
					dlg.rebuildFieldWithValue("unit.id", data.pn.unit.id);
				}
			},
			"url":"/pn/update",
			"success": function(data) {
				dlg.hide();
				PdSys.success({
					"ok" : function() {
						PdSys.refresh();
					}
				});
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});
	
	$("#addPnCls").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true,"showMsg":true});
		if(selIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		var id = selIds[0];
		var fields = [
			{
				"name":"id",
				"type":"hidden",
				"value":selIds[0]
			},
			{
				"name":"pnClss[0].id",
				"label":"子类",
				"type":"select",
				"value":"",
				"ajax": true,
				"url":"/pncls/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.forEach(function(pnCls, idx) {
						thisField.options.push({
							"value": pnCls.id,
							"caption":pnCls.name,
						});
					});
				},
				"groupButtons": [{
					"name":"addNewPnCls",
					"text":"+",
					"click": function(dlg) {
						var dlgPnCls = new CommonDlg();
						dlgPnCls.showFormDlg({
							"target":"pncls_dlg_div",
							"caption":"添加子类",
							"fields":[
								{
									"name":"name",
									"label":"子类名",
									"type":"text",
									"value":"",
								}
							],
							"url":"/pncls/add",
							"success" : function(data) {
								dlgPnCls.hide();
								dlg.rebuildFieldWithValue("pnCls.id", data.pnCls.id);
							},
							"error": function(data) {
								PdSys.alert(data.msg);
							}
						});
					}
				}],
			}
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"追加子类",
			"fields":fields,
			"url":"/pn/addPnCls",
			"success": function(data) {
				dlg.hide();
				PdSys.success({
					"ok" : function() {
						PdSys.refresh();
					}
				});
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});
});
