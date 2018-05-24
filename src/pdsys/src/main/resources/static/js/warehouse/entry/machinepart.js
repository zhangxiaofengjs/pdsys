$(document).ready(function(){
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
					"value":"2"
				},
				{
					"name":"no",
					"label":"入库单号",
					"type":"text",
					"value":"2-" + dateYYYYMMDD() + "-",
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
	        	if(data.success)
	        	{
	        		$(location).attr('href', PdSys.url('/warehouse/entry/main/machinepart?id=' + data.id));
	        	}
	        	else
	        	{
	        		var dlg = new CommonDlg();
	    			dlg.showMsgDlg({
	    				"target":"msg_div",
	    				"type":"ok",
	    				"msg":"新建入库单号发生错误。"});
	        	}
	        },
	        error: function(data) {
    			PdSys.alert(data.msg);
	        }
	    });
	});
	
	$("button[name='addEntryMachinePart']").click(function(){
		var self = $(this);

		var fields = [
		{
			"name":"wareHouseEntry.id",
			"type":"hidden",
			"value":$('#entry_id').val()
		},
		{
			"name":"machinePart.id",
			"label":"品番",
			"type":"select",
			"options":[],
			"min":1,
			"ajax":true,
			"url":"/machinepart/list/json",
			"ajaxData":{
				"id": -1
			},
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options = [];
				thisField.options.push({
					"value": -1,
					"caption":"请选择品番...",
				});
				data.machineparts.forEach(function(mp, idx) {
					thisField.options.push({
						"value": mp.id,
						"caption": "{0} {1}".format(mp.pn, mp.name),
						"data":mp.unit.name
					});
				});
			},
			"afterBuild": function() {
				var self = this;
				
				var thisElem = dlg.findFieldElem(self);
				
				//select选择以后刷新品目单位
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					var val = "";
					if(selIndex != 0) {
						//第一项是[请选择]，无视
						val = self.options[selIndex].data;
					}
					dlg.rebuildFieldWithValue("unit.name", val);
				});
			},
		},
		{
			"name":"num",
			"label":"数量",
			"type":"number",
			"value":"",
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
			"url":"/warehouse/entry/update/machinepart",
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
	
	$("button[name='deleteEntryMachinePart']").click(function(){
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
					"url":"/warehouse/entry/delete/machinepart",
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
