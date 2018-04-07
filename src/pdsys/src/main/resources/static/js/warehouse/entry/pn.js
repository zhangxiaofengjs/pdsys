$(document).ready(function(){
	$("button[name='refresh']").click(function(){
		PdSys.refresh();
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
				}
			],
	    	url : "/warehouse/entry/add/entry",
	        success : function(data) {
	        	if(data.success)
	        	{
	        		$(location).attr('href', PdSys.url('/warehouse/entry/main/pn/' + data.id));
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
	        	var dlg = new CommonDlg();
    			dlg.showMsgDlg({
    				"target":"msg_div",
    				"type":"ok",
    				"msg":"发生错误。"});
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
			"name":"orderPn.order.id",
			"label":"订单",
			"type":"select",
			"options":[],
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
						"order":{
							"id": val
						}
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
			"ajax":true,
			"depend":true,//不立即执行，等订单项目的刷新
			"url":"/order/pn/list/json",
			"ajaxData":{
				"order":{
					"id": -1
				}
			},
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options = [];
				thisField.options.push({
					"value": -1,
					"caption":"请选择品目...",
				});
				data.forEach(function(orderPn, idx) {
					thisField.options.push({
						"value": orderPn.id,
						"caption": "{0} {1} / {2}".format(orderPn.pn.pn, orderPn.pn.name, orderPn.pn.pnCls.name)
					});
				});
			},
		},
		{
			"name":"type",
			"label":"产品种类",
			"type":"select",
			"options":[],
			"ajax":false,
			"options":[
				{
					"value":0,
					"caption":'半成品'
				},
				{
					"value":1,
					"caption":'成品'
				}
			]
		},
		{
			"name":"num",
			"label":"数量",
			"type":"number",
			"value":"1",
		}];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加到入库单",
			"fields":fields,
			"url":"/warehouse/entry/add/pn",
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
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":"添加到入库单失败,请联系管理员!"});
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
					"url":"/warehouse/entry/delete/pn",
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
							"msg":"发生错误,请联系管理员!"});
					}
				});
			}
		});
	});
	
	$("#deleteEntry").click(function(){
		var self = $(this);
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除入库单",
			"type":"yesno",
			"msg":"确定删除入库单?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/entry/delete/entry/" + $("#entry_id").val(),
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
