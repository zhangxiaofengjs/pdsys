$(document).ready(function(){
	$("button[name='entryList']").click(function(){
		var self = $(this);
		PdSys.refresh('/warehouse/entry/main/semipn?no=&content=list');
	});
	
	//新建入库单
	$("button[name='addEntry']").click(function(){
		var self = $(this);
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"选择入库提交人",
			"fields":[
				{
					"name":"type",
					"type":"hidden",
					"value":"3"
				},
				{
					"name":"no",
					"label":"入库单号",
					"type":"text",
					"value":"3-" + dateYYYYMMDD() + "-",
					"required":"required"
				},
				{
					"name":"user.id",
					"label":"提交人",
					"type":"select",
					"options":[],
					"ajax":true,
					"url":"/user/list/json",
					"convertAjaxData" : function(thisField, data) {
						//将返回的值转化为Field规格数据,以供重新渲染
						//做成选择分支
						data.forEach(function(user, idx) {
							thisField.options.push({
								"value": user.id,
								"caption":user.name,
							});
						});
					}
				},
				{
					"name":"comment",
					"label":"备注",
					"type":"text",
					"value":""
				}
			],
	    	url : "/warehouse/entry/add/entry",
	        success : function(data) {
	        	$(location).attr('href', PdSys.url('/warehouse/entry/main/semipn?no=' + data.entry.no+'&content=one'));
	        },
	        error: function(data) {
	        	PdSys.alert(data.msg);
	        }
	    });
	});
	
	$("button[name='addEntryPn']").click(function(){
		var self = $(this);

		var fields = [
		{
			"name":"wareHouseEntry.id",
			"type":"hidden",
			"value":$('#entry_id').val()
		},
		{
			"name":"pn.id",
			"label":"品目",
			"type":"select",
			"options":[],
			"ajax":true,
			"url":"/pn/list/json",
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options = [];
				data.pns.forEach(function(pn, idx) {
					var unit = pn.unit;
					thisField.options.push({
						"value": pn.id,
						"caption": "{0} {1}".format(pn.pn, pn.name),
						"data": unit.subName==""?unit.name:unit.subName
					});
				});
			},
			"afterBuild": function() {
				var self = this;
				
				var thisElem = dlg.findFieldElem(self);
				
				//select选择以后刷新品目单位
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					var pnId = self.options[selIndex].value;
					var unitname = self.options[selIndex].data;
					dlg.rebuildFieldWithValue("unit.name", unitname);
					
					var pnClsField = dlg.fieldByName("pnClsRel.pnCls.id");
					pnClsField.ajaxData = {"id":pnId};
					dlg.rebuildField(pnClsField);
				});
				thisElem.trigger("change");
			},
		},
		{
			"name":"pnClsRel.pnCls.id",
			"label":"子类",
			"type":"select",
			"options":[],
			"ajax":true,
			"depend":true,
			"url":"/pn/get",
			"ajaxData":{},
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options = [];
				data.pn.pnClsRels.forEach(function(pnClsRel, idx) {
					var pnCls = pnClsRel.pnCls;
					thisField.options.push({
						"value": pnCls.id,
						"caption": pnCls.name,
						"data": pnCls.unit.name,
					});
				});
			},
			"afterBuild": function() {
				var self = this;
				
				var thisElem = dlg.findFieldElem(self);
				
				//select选择以后刷新品目单位
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					var pnId = self.options[selIndex].value;
					var unitname = self.options[selIndex].data;
					dlg.rebuildFieldWithValue("unit.name", unitname);
				});
				thisElem.trigger("change");
			},
		},
		{
			"name":"num",
			"label":"入库数量",
			"type":"number",
			"value":"0",
			"min":"1",
		},
		{
			"name":"unit.name",
			"label":"单位",
			"type":"label",
			"value":"",
		}];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加到入库单",
			"fields":fields,
			"url":"/warehouse/entry/update/semipn",
			"valid":function() {
				return true;
			},
			"success": function(data) {
				dlg.hide();
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":"成功添加到入库单!",
					"ok":function(){
						PdSys.refresh();
					}});
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});
	
	$("button[name='deleteEntryPn']").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId();
		if(selIds.length == 0) {
			return;
		}
		
		var ajaxDatas = [];
		$.each(selIds, function() {
			ajaxDatas.push({"id":this});
		});
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除入库单",
			"type":"yesno",
			"msg":"确定删除选择的入库项目?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/entry/delete/semipn",
					"data":ajaxDatas,
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"删除成功!",
							"ok":function(){
								PdSys.refresh();
							}});
					},
					"error": function(data) {
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
	
	$("button[name='doEntry']").click(function(){
		var self = $(this);
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"确定入库单",
			"type":"yesno",
			"msg":"确定执行入库?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/entry/entry/" + $("#entry_id").val(),
					"success": function(data) {
						dlg.hide();
						
						if(data.success) {
							var msgDlg = new CommonDlg();
							msgDlg.showMsgDlg({
								"target":"msg_div",
								"type":"ok",
								"msg":"入库成功!",
								"ok": function(){
									PdSys.refresh();
								}});
						} else {
							PdSys.alert(data.msg);
						}
					},
					"error": function(data) {
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
	
	$("button[name='deleteEntry']").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true, "showMsg":true});
		if(selIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除入库单",
			"type":"yesno",
			"msg":"确定删除入库单?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/entry/delete/entry/" + selIds[0],
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"入库单已删除!",
							"ok": function(){
								PdSys.refresh();
							}});
					},
					"error": function(data) {
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
});
