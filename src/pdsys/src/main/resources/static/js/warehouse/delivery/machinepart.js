$(document).ready(function(){
	//新建出库单
	$("button[name='addDelivery']").click(function(){
		var self = $(this);
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"选择出库领收人",
			"fields":[
				{
					"name":"type",
					"type":"hidden",
					"value":"2",
				},
				{
					"name":"no",
					"label":"出库单号",
					"type":"text",
					"value":"2-" + dateYYYYMMDD() + "-",
					"required":"required"
				},
				{
					"name":"user.id",
					"label":"领收人",
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
					"value":"",
				},
			],
	    	url : "/warehouse/delivery/add/delivery",
	        success : function(data) {
	        	if(data.success) {
	        		$(location).attr('href', PdSys.url('/warehouse/delivery/main/machinepart?id=' + data.id));
	        	} else {
	        		PdSys.alert(data.msg);
	        	}
	        },
	        error: function(data) {
	        	PdSys.alert(data.msg);
	        }
	    });
	});
	
	$("button[name='addDeliveryMachinePart']").click(function(){
		var self = $(this);

		var fields = [
		{
			"name":"wareHouseDelivery.id",
			"type":"hidden",
			"value":$('#delivery_id').val()
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
				data.machineparts.forEach(function(machinePart, idx) {
					thisField.options.push({
						"value": machinePart.id,
						"caption": "{0} {1}".format(machinePart.pn, machinePart.name)
					});
				});
			},
		},
		{
			"name":"num",
			"label":"数量",
			"type":"number",
			"min":1,
			"value":"",
		}];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加到出库单",
			"fields":fields,
			"valid":function() {
				return true;
			},
			"url":"/warehouse/delivery/add/machinepart",
			"success": function(data) {
				dlg.hide();
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":"成功添加到出库单!",
					"ok": function() {
						PdSys.refresh();
					}});
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});
	
	$("button[name='deleteDeliveryMachinePart']").click(function(){
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
					"url":"/warehouse/delivery/delete/machinepart",
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
						PdSys.alert(data.msg);
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
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
	
	$("button[name='deleteDelivery']").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true, "showMsg":true});
		if(selIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除出库单",
			"type":"yesno",
			"msg":"确定删除出库单?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/delivery/delete/delivery/" + selIds[0],
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
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
});
