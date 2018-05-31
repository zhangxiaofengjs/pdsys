$(document).ready(function(){
	$("#addMachine").click(function(){
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
					data.units.forEach(function(unit, idx) {
						thisField.options.push({
							"value": unit.id,
							"caption":M.unitName(unit),
						});
					});
				}
			},
			{
				"name":"maitenacePeriod",
				"label":"维护周期",
				"type":"number",
				"value":"0",
				"groupButtons":[{
					"name":"mp",
					"text":"天",
				}]
			},
			{
				"name":"supplier.id",
				"label":"供应商",
				"type":"select",
				"required":"required",
				"groupButtons": createSupplierGroupButtons({
					"target":"supplier_dlg_div",
					"success": function(action, data) {
						if(action == "add") {
							dlg.rebuildFieldWithValue("supplier.id", data.supplier.id);
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
			"caption":"添加机器品种",
			"fields":fields,
			"url":"/machine/add",
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

	$("#editMachine").click(function(){
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
					data.units.forEach(function(unit, idx) {
						thisField.options.push({
							"value": unit.id,
							"caption":M.unitName(unit),
						});
					});
				}
			},
			{
				"name":"maitenacePeriod",
				"label":"维护周期",
				"type":"number",
				"value":"0",
				"groupButtons":[{
					"name":"mp",
					"text":"天",
				}]
			},
			{
				"name":"supplier.id",
				"label":"供应商",
				"type":"select",
				"groupButtons": createSupplierGroupButtons({
					"target":"supplier_dlg_div",
					"success": function(action, data) {
						if(action == "add") {
							dlg.rebuildFieldWithValue("supplier.id", data.supplier.id);
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
			"caption":"编辑机器",
			"fields":fields,
			"ajax": {
				"url":"/machine/get",
				"data":{
					"id":id
				},
				"convertAjaxData":function(data) {
					dlg.rebuildFieldWithValue("pn", data.machine.pn);
					dlg.rebuildFieldWithValue("name", data.machine.name);
					dlg.rebuildFieldWithValue("unit.id", data.machine.unit.id);
					dlg.rebuildFieldWithValue("maitenacePeriod", data.machine.maitenacePeriod);
					dlg.rebuildFieldWithValue("supplier.id", data.machine.supplier.id);
				}
			},
			"url":"/machine/update",
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
	
	$("#addMachinePart").click(function(){
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
				"name":"machineMachinePartRels[0].machinePart.id",
				"label": "维护零件",
				"type":"select",
				"value":"",
				"ajax": true,
				"ajaxData":{},
				"url":"/machinepart/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.machineparts.forEach(function(mp, idx) {
						thisField.options.push({
							"value": mp.id,
							"caption":mp.pn + " " + mp.name,
							"data":M.unitName(mp.unit)
						});
					});
				},
				"afterBuild":function() {
					var self = this;
					var fieldElm = dlg.findFieldElem(self);
					fieldElm.change(function() {
						var selIndex = fieldElm[0].selectedIndex;
						var val = self.options[selIndex].data;
						dlg.rebuildFieldWithValue("machineMachinePartRels[0].machinePart.unit.name", val);
					});
					
					fieldElm.trigger("change");
				},
				"groupButtons": createMachinePartGroupButtons({
					"target":"mp_dlg_div",
					"success": function(action, data) {
						if(action == "add") {
							dlg.rebuildFieldWithValue("machineMachinePartRels[0].machinePart.id", data.machinepart.id);
						}
					}
				}),
			},
			{
				"name":"machineMachinePartRels[0].maitenacePartNum",
				"label":"维护所需",
				"type":"number",
				"value":"0"
			},
			{
				"name":"machineMachinePartRels[0].machinePart.unit.name",
				"label":"单位",
				"type":"label",
				"value":""
			},
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加 / 修改维护零件",
			"fields":fields,
			"url":"/machine/addMachinePart",
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
