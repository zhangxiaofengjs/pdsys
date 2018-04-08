$(document).ready(function(){
	$("#changeState").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true});
		if(selIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"选择设备状态",
			"fields":[
				{
					"name":"id",
					"type":"hidden",
					"value":selIds[0]
				},
				{
					"name":"state",
					"label":"设备状态",
					"type":"select",
					"options":[
						{ 'value': 0, "caption" : "运行中"},
						{ 'value': 1, "caption" : "维护中"},
						{ 'value': 2, "caption" : "故障"},
					],
				}
			],
	    	url : "/device/update",
	        success : function(data) {
	        	PdSys.refresh();
	        },
	        error: function(data) {
	        	PdSys.sysError();
	        }
	    });
	});
	
	$("#addDevice").click(function(){
		var self = $(this);

		var fields = [
		{
			"name":"no",
			"label":"编号",
			"type":"text",
			"value":''
		},
		{
			"name":"machine.id",
			"label":"机器设备",
			"type":"select",
			"options":[],
			"ajax":true,
			"url":"/machine/list/json",
			"convertAjaxData" : function(thisField, data) {
				data.forEach(function(machine, idx) {
					thisField.options.push({
						"value": machine.id,
						"caption":"{0} {1}".format(machine.pn, machine.name),
					});
				});
			},
		},
		{
			"name":"place.id",
			"label":"使用地点",
			"type":"select",
			"options":[],
			"ajax":true,
			"url":"/place/list/json",
			"convertAjaxData" : function(thisField, data) {
				data.forEach(function(place, idx) {
					thisField.options.push({
						"value": place.id,
						"caption": place.name,
					});
				});
			},
		},
		{
			"name":"user.id",
			"label":"负责人",
			"type":"select",
			"options":[],
			"ajax":true,
			"url":"/user/list/json",
			"convertAjaxData" : function(thisField, data) {
				data.forEach(function(user, idx) {
					thisField.options.push({
						"value": user.id,
						"caption": user.name,
					});
				});
			},
		},
		];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"追加新设备",
			"fields":fields,
			"url":"/device/add",
			"success": function(data) {
				dlg.hide();
				PdSys.success({
					"ok" : function() {
						PdSys.refresh();
					}
				});
			},
			"error": function(data) {
				PdSys.sysError();
			}
		});
	});
	
	$("button[name='deleteDeliveryPn']").click(function(){
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
			"caption":"删除出库单",
			"type":"yesno",
			"msg":"确定删除选择的出库项目?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/delivery/delete/pn",
					"data":ajaxDatas,
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"删除成功!",
							"ok": function() {
								PdSys.refresh();
							}});
						
					},
					"error": function(data) {
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"删除失败,请联系管理员!"});
					}
				});
			}
		});
	});
	
	$("button[name='doDelivery']").click(function(){
		var self = $(this);
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"确定出库单",
			"type":"yesno",
			"msg":"确定执行出库?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/delivery/delivery/" + $("#delivery_id").val(),
					"success": function(data) {
						dlg.hide();
						
						if(data.success) {
							var msgDlg = new CommonDlg();
							msgDlg.showMsgDlg({
								"target":"msg_div",
								"type":"ok",
								"msg":"出库成功!",
								"ok": function(){
									PdSys.refresh();
								}});
						} else {
							var msgDlg = new CommonDlg();
							msgDlg.showMsgDlg({
								"target":"msg_div",
								"type":"ok",
								"msg":data.msg});
						}
					},
					"error": function(data) {
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"出库失败,请联系管理员!"});
					}
				});
			}
		});
	});
	
	$("#deleteDelivery").click(function(){
		var self = $(this);
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除出库单",
			"type":"yesno",
			"msg":"确定删除出库单?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/delivery/delete/delivery/" + $("#delivery_id").val(),
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"出库单已删除!",
							"ok": function(){
								PdSys.refresh();
							}});
					},
					"error": function(data) {
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"发生错误,请联系管理员!"});
					}
				});
			}
		});
	});
});
