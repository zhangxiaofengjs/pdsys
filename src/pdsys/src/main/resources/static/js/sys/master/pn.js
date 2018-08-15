$(document).ready(function(){
	$("#addPn").click(function(){
		var self = $(this);
		var dlg = new CommonDlg();
		
		var fields = [
			{
				"name":"pn",
				"label":"品目",
				"type":"text",
				"required":"required",
			},
			{
				"name":"name",
				"label":"名称",
				"type":"text",
				"required":"required",
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
			{
				"name":"price",
				"label":"单价",
				"type":"number",
				"value":"1.0",
				"min":0.000000001,
			},
			{
				"name":"priceUnit.id",
				"label":"单价单位",
				"type":"select",
				"options":[],
				"required":"required",
				"ajax":true,
				"ajaxData": {
					"type":2, //货币单位
				},
				"url":"/unit/list/json",
				"convertAjaxData" : function(thisField, data) {
					data.units.forEach(function(unit, idx) {
						thisField.options.push({
						"value": unit.id,
						"caption": unit.name,
						});
					});
				},
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
				"required":"required",
			},
			{
				"name":"name",
				"label":"名称",
				"type":"text",
				"value":"",
				"depend":true,
				"required":"required",
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
			},
			{
				"name":"price",
				"label":"单价",
				"type":"number",
				"value":"1.0",
				"min":0.000000001,
				"required":"required",
			},
			{
				"name":"priceUnit.id",
				"label":"单价单位",
				"type":"select",
				"options":[],
				"required":"required",
				"ajax":true,
				"ajaxData": {
					"type":2, //货币单位
				},
				"url":"/unit/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.units.forEach(function(unit, idx) {
						thisField.options.push({
						"value": unit.id,
						"caption": unit.name,
						});
					});
				},
			},
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
					var pn = data.pn;
					dlg.rebuildFieldWithValue("pn", pn.pn);
					dlg.rebuildFieldWithValue("name", pn.name);
					dlg.rebuildFieldWithValue("unit.id", pn.unit.id);
					dlg.rebuildFieldWithValue("price", pn.price);
					dlg.rebuildFieldWithValue("priceUnit.id", pn.priceUnit == null ? 0 : pn.priceUnit.id);
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
				"name":"pnClsRels[0].pnCls.unit.id",
				"label":"单位",
				"type":"select",
				"value":"",
				"groupButtons": createUnitGroupButtons({
					"target":"unit_dlg_div",
					"success": function(action, data) {
						if(action == "add") {
							dlg.rebuildFieldWithValue("pnClsRels[0].pnCls.unit.id", data.unit.id);
						}
					}
				}),
				"ajax": true,
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
		if(clsId == -1) {
			PdSys.alert("共通的子类不可删除");
			return;
		}
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
						dlg.hide();
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
		var url = (clsId==-1?"/pn/addBOM":"/pncls/addBOM");
		var bomIdName = (clsId==-1?"pnBOMRels[0].bom.id":"pnClsBOMRels[0].bom.id");
		var useNumName = (clsId==-1?"pnBOMRels[0].useNum":"pnClsBOMRels[0].useNum");
		var dlg = new CommonDlg();
		var fields = [
			{
				"name":"id",
				"type":"hidden",
				"value":(clsId==-1?pnId:clsId)
			},
			{
				"name":"bomType",
				"label": "种类",
				"type":"select",
				"options":[{
					"value":0,
					"caption":"原材"
				},{
					"value":1,
					"caption":"包材"
				}],
				"afterBuild":function() {
					var self = this;
					var fieldElm = dlg.findFieldElem(self);
					fieldElm.change(function() {
						var selIndex = fieldElm[0].selectedIndex;
						var type = self.options[selIndex].value;
						var fieldBom = dlg.fieldByName(bomIdName);
						fieldBom.ajaxData = {"type":type};
						dlg.rebuildField(fieldBom);
					});
					
					fieldElm.trigger("change");
				},
			},
			{
				"name":bomIdName,
				"label": "使用原包材",
				"type":"select",
				"value":"",
				"ajax": true,
				"depend":true,
				"ajaxData":{"type":0},
				"url":"/bom/list/json",
				"convertAjaxData" : function(thisField, data) {
					thisField.options = [];
					data.boms.forEach(function(bom, idx) {
						thisField.options.push({
							"value": bom.id,
							"caption":bom.pn + " " + bom.name,
							"data":M.unitName(bom.unit)
						});
					});
				},
				"afterBuild":function(t) {
					if(t != "ajax") {
						return;
					}
					var self = this;
					var fieldElm = dlg.findFieldElem(self);
					fieldElm.change(function() {
						var selIndex = fieldElm[0].selectedIndex;
						var val = self.options[selIndex].data;
						dlg.rebuildFieldWithValue("unitName", val);
					});
					
					fieldElm.trigger("change");
				},
			},
			{
				"name":useNumName,
				"label":"使用数量",
				"type":"number",
				"value":"0",
				"min":"0.00000000000000001"
			},
			{
				"name":"unitName",
				"label":"单位",
				"type":"label",
				"value":""
			},
		];
		
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加使用原包材",
			"fields":fields,
			"url":url,
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
		var url = (clsId==-1?"/pn/deleteBOM":"/pncls/deleteBOM");
		var bomIdName = (clsId==-1?"pnBOMRels[0].bom.id":"pnClsBOMRels[0].bom.id");
		var useNumName = (clsId==-1?"pnBOMRels[0].useNum":"pnClsBOMRels[0].useNum");
		var delAjaxData;
		if(clsId==-1) {
			delAjaxData = {
				"id":pnId,
				"pnBOMRels":[{"bom":{"id":bomId}}]
			};
		} else {
			delAjaxData = {
					"id":clsId,
					"pnClsBOMRels":[{"bom":{"id":bomId}}]
				};	
		}
	
		var dlg = new CommonDlg();
		
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除原包材",
			"msg": "确定删除该原包材?",
			"type": "yesno",
			"yes": function() {
				PdSys.ajax({
					"url":url,
					"data": delAjaxData,
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
