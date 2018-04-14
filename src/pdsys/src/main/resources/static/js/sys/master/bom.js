$(document).ready(function(){
	function createUnitGroupButton() {
		return {
			"name":"addUnit",
			"text":"+",
			"click": function(dlg) {
				var dlgUnit = new CommonDlg();
				dlgUnit.showFormDlg({
					"target":"unit_dlg_div",
					"caption":"添加单位",
					"fields":[
						{
							"name":"name",
							"label":"单位",
							"type":"text",
							"value":"",
						}
					],
					"url":"/unit/add",
					"success" : function(data) {
						dlgUnit.hide();
						dlg.rebuildFieldWithValue("unit.id", data.unit.id);
					},
					"error": function(data) {
						PdSys.alert(data.msg);
					}
				});
			}
		}
	}

	function createSupplierGroupButton() {
		return {
			"name":"addSupplier",
			"text":"+",
			"click": function(dlg) {
				var dlgSupplier = new CommonDlg();
				dlgSupplier.showFormDlg({
					"target":"supplier_dlg_div",
					"caption":"添加供应商",
					"fields":[
						{
							"name":"name",
							"label":"供应商名",
							"type":"text",
							"value":"",
						},
						{
							"name":"phone",
							"label":"电话",
							"type":"text",
							"value":"",
						},
						{
							"name":"address",
							"label":"地址",
							"type":"text",
							"value":"",
						}
					],
					"url":"/supplier/add",
					"success" : function(data) {
						dlgSupplier.hide();
						dlg.rebuildFieldWithValue("supplier.id", data.unit.id);
					},
					"error": function(data) {
						PdSys.alert(data.msg);
					}
				});
			}
		}
	}
	
	$("#addBOM").click(function(){
		var self = $(this);

		var dlg = new CommonDlg();
		var fields = [
			{
				"name":"type",
				"label":"种类",
				"type":"select",
				"options":[{
					"caption":"原材",
					"value":"0",
					},
					{
						"caption":"包材",
						"value":"1",
					}],
			},
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
				"groupButton": createUnitGroupButton(dlg),
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
			{
				"name":"supplier.id",
				"label":"供应商",
				"type":"select",
				"groupButton": createSupplierGroupButton(dlg),
				"ajax":true,
				"url":"/supplier/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.forEach(function(supplier, idx) {
						thisField.options.push({
							"value": supplier.id,
							"caption":supplier.name,
						});
					});
				}
			},
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加原包材",
			"fields":fields,
			"url":"/bom/add",
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

	$("#editBOM").click(function(){
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
				"name":"type",
				"label":"种类",
				"type":"select",
				"value":"",
				"disabled":"disabled",
				"depend":true,
				"options":[{
					"caption":"原材",
					"value":"0",
					},
					{
						"caption":"包材",
						"value":"1",
					}],
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
				"groupButton": createUnitGroupButton(dlg),
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
			},
			{
				"name":"supplier.id",
				"label":"供应商",
				"type":"select",
				"groupButton": createSupplierGroupButton(dlg),
				"value":"",
				"ajax": true,
				"depend":true,
				"url":"/supplier/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.forEach(function(supplier, idx) {
						thisField.options.push({
							"value": supplier.id,
							"caption":supplier.name,
						});
					});
				}
			},
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"编辑原包材",
			"fields":fields,
			"ajax": {
				"url":"/bom/get",
				"data":{
					"id":id
				},
				"convertAjaxData":function(data) {
					dlg.rebuildFieldWithValue("type", data.bom.type);
					dlg.rebuildFieldWithValue("pn", data.bom.pn);
					dlg.rebuildFieldWithValue("name", data.bom.name);
					dlg.rebuildFieldWithValue("unit.id", data.bom.unit.id);
					dlg.rebuildFieldWithValue("supplier.id", data.bom.supplier.id);
				}
			},
			"url":"/bom/update",
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
