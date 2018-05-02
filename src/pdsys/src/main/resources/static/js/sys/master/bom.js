$(document).ready(function(){
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
				"name":"price",
				"label":"单价",
				"type":"number",
				"requried":"requried",
			},
			{
				"name":"unit.id",
				"label":"规格单位",
				"type":"select",
				"groupButtons": createUnitGroupButtons({
					"target":"unit_dlg_div",
					"success" :function(action, data){
						if(action == "add") {
							dlg.rebuildFieldWithValue("unit.id", data.unit.id);
						} else if(action == "delete") {
							dlg.rebuildFieldWithValue("unit.id", -1);
						}
					}
				}),
				"ajax":true,
				"url":"/unit/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.units.forEach(function(unit, idx) {
						thisField.options.push({
							"value": unit.id,
							"caption":"{0}({1}{2})".format(unit.name, unit.ratio, unit.subName),
						});
					});
				}
			},
			{
				"name":"comment",
				"label":"备注",
				"type":"text",
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
				"name":"price",
				"label":"单价",
				"type":"number",
				"requried":"requried",
			},
			{
				"name":"unit.id",
				"label":"规格单位",
				"type":"select",
				"value":"",
				"groupButtons": createUnitGroupButtons({
					"target":"unit_dlg_div",
					"success" :function(action, data){
						if(action == "add") {
							dlg.rebuildFieldWithValue("unit.id", data.unit.id);
						} else if(action == "delete") {
							dlg.rebuildFieldWithValue("unit.id", -1);
						}
					}
				}),
				"ajax": true,
				"depend":true,
				"url":"/unit/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.units.forEach(function(unit, idx) {
						thisField.options.push({
							"value": unit.id,
							"caption":"{0}({1}{2})".format(unit.name, unit.ratio, unit.subName),
						});
					});
				}
			},
			{
				"name":"comment",
				"label":"备注",
				"type":"text",
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
					dlg.rebuildFieldWithValue("price", data.bom.price);
					dlg.rebuildFieldWithValue("comment", data.bom.comment);
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
	
	$("#addBOMSupplier").click(function(){
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
				"name":"suppliers[0].id",
				"label":"供应商",
				"type":"select",
				"groupButtons": createSupplierGroupButtons({
					"target": "supplier_dlg_div",
					"success": function(action, data) {
						if(action == "add") {
							dlg.rebuildFieldWithValue("suppliers[0].id", data.supplier.id);
						}
					}
				}),
				"ajax":true,
				"url":"/supplier/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.suppliers.forEach(function(supplier, idx) {
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
			"caption":"追加供应商",
			"fields":fields,
			"url":"/bom/addsupplier",
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
	
	$("#deleteBOMSupplier").click(function(){
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
				"name":"suppliers[0].id",
				"label":"供应商",
				"type":"select",
				"ajax":true,
				"url":"/bom/get",
				"ajaxData" : {
					"id": selIds[0]
				},
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					if(data.bom.suppliers != null) {
						data.bom.suppliers.forEach(function(supplier, idx) {
							thisField.options.push({
								"value": supplier.id,
								"caption":supplier.name,
							});
						});
					}
				}
			},
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"删除供应商",
			"fields":fields,
			"url":"/bom/deletesupplier",
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
