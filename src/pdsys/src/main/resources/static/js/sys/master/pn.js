$(document).ready(function(){
	$("#addPn").click(function(){
		var self = $(this);
		var dlg = new CommonDlg();
		
		var fields = [
			{
				"name":"pn",
				"label":"品目",
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
							"caption":"{0}({1}{2})".format(unit.name, unit.ratio, unit.subName),
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
		var id = selIds[0].split("_")[0];
		var fields = [
			{
				"name":"id",
				"type":"hidden",
				"value":id
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
							"caption":"{0}({1}{2})".format(unit.name, unit.ratio, unit.subName),
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
		var selIdArr = selIds[0].split("_");
		var pnId = selIdArr[0];
		var dlg = new CommonDlg();
		var fields = [
			{
				"name":"id",
				"type":"hidden",
				"value":pnId
			},
			{
				"name":"pnClsRels[0].pnCls.name",
				"label":"子类",
				"type":"text",
				"value":"",
				"required": true,
			},
			{
				"name":"pnClsRels[0].num",
				"label":"单位配比",
				"type":"number",
				"value":"1",
				"min":"1",
			},
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"追加子类",
			"fields":fields,
			"url":"/pn/addpncls",
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
	
	$("#deletePnCls").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true,"showMsg":true});
		if(selIds.length != 1) {
			return;
		}
		var selIdArr = selIds[0].split("_");
		if(selIdArr.length < 2) {
			PdSys.alert("无可删除子类");
			return;
		}
		var pnId = selIdArr[0];
		var clsId = selIdArr[1];
		var dlg = new CommonDlg();
		
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除子类",
			"msg": "确定删除该子类?",
			"type": "yesno",
			"yes": function() {
				PdSys.ajax({
					"url":"/pn/deletepncls",
					"data": {
						"id":pnId,
						"pnClsRels":[
							{
								"pnCls": {"id":clsId}
							}
						]
					},
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
			}
		});
	});
	
	$("button[id^='addBOM'").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true,"showMsg":true});
		if(selIds.length != 1) {
			return;
		}
		
		var selIdArr = selIds[0].split("_");
		if(selIdArr.length < 2) {
			PdSys.alert("请先添加子类");
			return;
		}
		var pnId = selIdArr[0];
		var clsId = selIdArr[1];
		
		var type = 0;
		if(self.attr("id") == "addBOM1") {
			type = 1;
		}
		
		var dlg = new CommonDlg();
		var fields = [
			{
				"name":"id",
				"type":"hidden",
				"value":pnId
			},
			{
				"name":"pnClsRels[0].pnCls.id",
				"type":"hidden",
				"value":clsId
			},
			{
				"name":"pnClsRels[0].pnCls.pnClsBOMRels[0].bom.id",
				"label": type == 0 ? "使用原材":"使用包材",
				"type":"select",
				"value":"",
				"ajax": true,
				"ajaxData":{"type":type},
				"url":"/bom/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.boms.forEach(function(bom, idx) {
						thisField.options.push({
							"value": bom.id,
							"caption":bom.pn + " " + bom.name + "(" + bom.comment + ")",
							"data":bom.unit.name
						});
					});
				},
				"afterBuild":function() {
					var self = this;
					var fieldElm = dlg.findFieldElem(self);
					fieldElm.change(function() {
						var selIndex = fieldElm[0].selectedIndex;
						var val = self.options[selIndex].data;
						dlg.rebuildFieldWithValue("pnClsRels[0].pnCls.pnClsBOMRels[0].bom.unit.name", val);
					});
					
					fieldElm.trigger("change");
				},
			},
			{
				"name":"pnClsRels[0].pnCls.pnClsBOMRels[0].useNum",
				"label":"使用数量",
				"type":"number",
				"value":"0",
				"min":"0.00000000000000001"
			},
			{
				"name":"pnClsRels[0].pnCls.pnClsBOMRels[0].bom.unit.name",
				"label":"单位",
				"type":"label",
				"value":""
			},
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加使用原包材",
			"fields":fields,
			"url":"/pn/addBOM",
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
	
	$("#deleteBOM").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true,"showMsg":true});
		if(selIds.length != 1) {
			return;
		}
		var selIdArr = selIds[0].split("_");
		if(selIdArr.length < 3) {
			PdSys.alert("无可删除的原包材");
			return;
		}
		var pnId = selIdArr[0];
		var clsId = selIdArr[1];
		var bomId = selIdArr[2];
		var dlg = new CommonDlg();
		
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除原包材",
			"msg": "确定删除该原包材?",
			"type": "yesno",
			"yes": function() {
				PdSys.ajax({
					"url":"/pn/deleteBOM",
					"data": {
						"id":pnId,
						"pnClsRels":[ 
							{
								"pnCls": {
									"id":clsId,
									"pnClsBOMRels":[
										{
											"bom":{
												"id":bomId
											}
										}
									]
								}
							}
						]
					},
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
			}
		});
	});
});
