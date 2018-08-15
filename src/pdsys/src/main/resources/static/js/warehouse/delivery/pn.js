$(document).ready(function(){
	$("button[name='deliveryList']").click(function(){
		var self = $(this);
		PdSys.refresh('/warehouse/delivery/main/pn?no=&content=list');
	});
	
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
	        	$(location).attr('href', PdSys.url('/warehouse/delivery/main/pn?no=' + data.delivery.no+'&content=one'));
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
//			{
//				"cells":[{"text":"半成品"}, {"text":""}, {"text":""}, {"text":""}, {"text":orderPn.whpn.semiProducedNum}]
//			},
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
			"name":"pn.id",
			"label":"品目",
			"type":"select",
			"options":[],
			"required":"required",
			"ajax":true,
			"url":"/orderPn/list/json",
			"ajaxData":{
				"filterCond":{
					"notDelivered": true,
					"inWareHouse": true,
				}
			},
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options = [];
				var pnIdArr=[];
				data.orderPns.forEach(function(orderPn, idx) {
					var pn = orderPn.pn;
					
					if(pnIdArr.indexOf(pn.id) == -1) {
						thisField.options.push({
							"value": pn.id,
							"caption": "{0} {1}".format(pn.pn, pn.name),
							"data":pn.unit.name,
						});
						pnIdArr.push(pn.id);
					}
				});
			},
			"afterBuild": function(type) {
				var self = this;
				
				var thisElem = dlg.fieldElem(self);
				
				//select选择以后品目在库情况
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					var val = -1;
					var unitName = "";
					if(selIndex != -1) {
						val = self.options[selIndex].value;
						unitName = self.options[selIndex].data;
					}
					var fieldOrder = dlg.fieldByName("order.id");
					fieldOrder.ajaxData={
						"state": 0,//未出库
						"orderPns":[{"pn":{"id":val}}]//关联该订单
					};
					dlg.buildAjaxField(fieldOrder);
					
					dlg.rebuildFieldWithValue("unitName", unitName);
				});
				thisElem.trigger("change");
			}
		},
		{
			"name":"order.id",
			"label":"订单",
			"type":"select",
			"options":[],
			"ajax":true,
			"depend":true,
			"url":"/order/list/json",
			"ajaxData" :{
				"state": 0,
				"orderPns":[{"pn":{"id":-1}}]
			},
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options = [];
				data.orders.forEach(function(order, idx) {
					thisField.options.push({
						"value": order.id,
						"caption":order.no,
					});
				});
			},
			"afterBuild": function(type) {
				if(type != "ajax")
					return;
				
				var self = this;
				
				var orderElem = dlg.fieldElem(self);
				var pnElem = dlg.findFieldElem("pn.id");
				var pnField = dlg.fieldByName("pn.id");
				var orderField = dlg.fieldByName("order.id");
				
				//select选择以后品目在库情况
				orderElem.change(function() {
					var pnId = -1, selIndex = pnElem[0].selectedIndex;
					if(selIndex != -1) {
						pnId = pnField.options[selIndex].value;
					}
					
					selIndex = orderElem[0].selectedIndex;
					var orderId = -1;
					if(selIndex != -1) {
						orderId = orderField.options[selIndex].value;
					}
					
					var fieldPnInfo = dlg.fieldByName("pnInfo");
					fieldPnInfo.ajaxData={
						"pn":{"id":pnId},
						"order":{"id":orderId}
					};
					dlg.buildAjaxField(fieldPnInfo);
				});
				orderElem.trigger("change");
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
				
				//dlg.fieldByName("semiProducedNum").max = whPn.semiProducedNum;
				dlg.fieldByName("producedNum").max = whPn.producedNum;
			},
		},
		{
			"name":"producedNum",
			"label":"出库数量",
			"type":"number",
			"value":"1",
			"min":"0",
			"max":10000000,
		},
		{
			"name":"unitName",
			"label":"单位",
			"type":"label",
			"value":"",
		}];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加到出库单",
			"fields":fields,
			"valid":function() {
//				if(dlg.fieldVal("semiProducedNum") == 0 &&
//				   dlg.fieldVal("producedNum") == 0) {
//					dlg.setError("semiProducedNum", "半成品/成品数量都未输入");
//					dlg.setError("producedNum", "半成品/成品数量都未输入");
//					return false;
//				}
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
	
	$("#printBtn").click(function(){
		PdSys.print("detailDiv");
	});
});
