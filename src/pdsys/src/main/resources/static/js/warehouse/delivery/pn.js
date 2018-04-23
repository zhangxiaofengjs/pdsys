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
					"value":"0",
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
	        	if(data.success)
	        	{
	        		$(location).attr('href', PdSys.url('/warehouse/delivery/main/pn?id=' + data.id));
	        	}
	        	else
	        	{
	        		var dlg = new CommonDlg();
	    			dlg.showMsgDlg({
	    				"target":"msg_div",
	    				"type":"ok",
	    				"msg":"新建出库单号发生错误。"});
	        	}
	        },
	        error: function(data) {
	        	var dlg = new CommonDlg();
    			dlg.showMsgDlg({
    				"target":"msg_div",
    				"type":"ok",
    				"msg":"发生错误。"});
	        }
	    });
	});
	
	$("button[name='addDeliveryPn']").click(function(){
		var self = $(this);

		var fields = [
		{
			"name":"wareHouseDelivery.id",
			"type":"hidden",
			"value":$('#delivery_id').val()
		},
		{
			"name":"orderPn.order.id",
			"label":"订单",
			"type":"select",
			"options":[],
			"min":1,
			"ajax":true,
			"url":"/order/list/json",
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options.push({
					"value": -1,
					"caption":"请选择订单...",
				});
				data.forEach(function(order, idx) {
					thisField.options.push({
						"value": order.id,
						"caption":order.no,
					});
				});
			},
			"afterBuild": function() {
				var self = this;
				
				var thisElem = dlg.fieldElem(self.type, self.name);
				
				//select选择以后刷新品目
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					if(selIndex == 0) {
						//第一项是[请选择]，无视
						return;
					}
					var val = self.options[selIndex].value;
					
					var orderPnField = fields[2];
					orderPnField.ajaxData = {
							"id": val
					};
					
					dlg.buildAjaxField(orderPnField);
				});
			}
		},
		{
			"name":"orderPn.id",
			"label":"品目",
			"type":"select",
			"options":[],
			"min":1,
			"ajax":true,
			"depend":true,//不立即执行，等订单项目的刷新
			"url":"/orderPn/list/json",
			"ajaxData":{
				"id": -1
			},
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options = [];
				thisField.options.push({
					"value": -1,
					"caption":"请选择品目...",
				});
				data.orderPns.forEach(function(orderPn, idx) {
					thisField.options.push({
						"value": orderPn.id,
						"caption": "{0} {1} / {2}".format(orderPn.pn.pn, orderPn.pn.name, orderPn.pn.pnCls.name)
					});
				});
			},
		},
		{
			"name":"semiProducedNum",
			"label":"半成品数",
			"type":"number",
			"value":"0",
		},
		{
			"name":"producedNum",
			"label":"成品数",
			"type":"number",
			"value":"0",
		},
		{
			"name":"defectiveNum",
			"label":"不良品数",
			"type":"number",
			"value":"0",
		}];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加到出库单",
			"fields":fields,
			"valid":function() {
				if(dlg.fieldVal("semiProducedNum") == 0 &&
				   dlg.fieldVal("producedNum") == 0 &&
				   dlg.fieldVal("defectiveNum") == 0) {
					dlg.setError("semiProducedNum", "半成品/成品/不良品数量都未输入");
					dlg.setError("producedNum", "半成品/成品/不良品数量都未输入");
					dlg.setError("defectiveNum", "半成品/成品/不良品数量都未输入");
					return false;
				}
				return true;
			},
			"url":"/warehouse/delivery/add/pn",
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
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":"添加到出库单失败,请联系管理员!"});
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
