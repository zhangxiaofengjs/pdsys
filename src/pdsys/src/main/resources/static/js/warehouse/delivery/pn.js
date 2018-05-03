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
					"name":"no",
					"label":"出库单号",
					"type":"text",
					"value":"0-" + dateYYYYMMDD() + "-",
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
	        	$(location).attr('href', PdSys.url('/warehouse/delivery/main/pn?no=' + data.delivery.no));
	        },
	        error: function(data) {
    			PdSys.alert(data.msg);
	        }
	    });
	});
	
	function getOrderPnTable(orderPn) {
		var t={};
		t.headers=[{"text":""}, {"text":"订购"}, {"text":"已出库"}, {"text":"未出库"}, {"text":"在库"}];
		t.rows=[
			{
				"cells":[{"text":"半成品"}, {"text":""}, {"text":""}, {"text":""}, {"text":orderPn.whpn.semiProducedNum}]
			},
			{
				"cells":[{"text":"成品"}, {"text":orderPn.num}, {"text":orderPn.deliveredNum}, {"text":orderPn.num - orderPn.deliveredNum}, {"text":orderPn.whpn.producedNum}]
			}
		];
		return PdSysTable.buildTable(t);
	}
	
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
			"ajax":true,
			"url":"/order/list/json",
			"ajaxData" :{
				"state": 0
			},
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				data.orders.forEach(function(order, idx) {
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
					var val = self.options[selIndex].value;
					
					var orderPnField = fields[2];
					orderPnField.ajaxData = {
							"id": val,
							"filterCond":{
								"notDelivered": true,
								"inWareHouse": true,
							}
					};

					dlg.buildAjaxField(orderPnField);
				});
				thisElem.trigger("change");
			}
		},
		{
			"name":"orderPn.id",
			"label":"品目",
			"type":"select",
			"options":[],
			"required":"required",
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
				data.orderPns.forEach(function(orderPn, idx) {
					thisField.options.push({
						"value": orderPn.id,
						"caption": "{0} {1}".format(orderPn.pn.pn, orderPn.pn.name),
					});
				});
			},
			"afterBuild": function() {
				var self = this;
				
				var thisElem = dlg.fieldElem(self.type, self.name);
				
				//select选择以后品目在库情况
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					var val = -1;
					if(selIndex != -1) {
						val = self.options[selIndex].value;
					}
					var fieldPnInfo = dlg.fieldByName("pnInfo");
					fieldPnInfo.ajaxData = { "id":val };
					dlg.buildAjaxField(fieldPnInfo);
				});
				thisElem.trigger("change");
			}
		},
		{
			"name":"pnInfo",
			"label":"状态",
			"type":"label",
			"value":"",
			"ajax":true,
			"url":"/orderPn/get",
			"depend":true,
			"ajaxData":{"id":-1},
			"convertAjaxData" : function(thisField, data) {
				var orderPn = data.orderPn;
				var whPn = orderPn.whpn;
				
				thisField.value = getOrderPnTable(orderPn);
				
				dlg.fieldByName("semiProducedNum").max = whPn.semiProducedNum;
				dlg.fieldByName("producedNum").max = whPn.producedNum;
			},
		},
		{
			"name":"semiProducedNum",
			"label":"出库(半成品数)",
			"type":"number",
			"value":"0",
			"min":"0",
			"max":10000000,
		},
		{
			"name":"producedNum",
			"label":"出库(成品数)",
			"type":"number",
			"value":"0",
			"min":"0",
			"max":10000000,
		}];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加到出库单",
			"fields":fields,
			"valid":function() {
				if(dlg.fieldVal("semiProducedNum") == 0 &&
				   dlg.fieldVal("producedNum") == 0) {
					dlg.setError("semiProducedNum", "半成品/成品数量都未输入");
					dlg.setError("producedNum", "半成品/成品数量都未输入");
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
