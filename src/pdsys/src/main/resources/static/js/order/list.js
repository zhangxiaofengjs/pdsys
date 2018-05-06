$(function () {
	$("#addOrder").click(function(){
		var dlg = new CommonDlg();
		var myDate = new Date(); 
		dlg.showFormDlg({
			"target":"addOrder_div",
			"caption":"新增订单",
			"fields":[
				{
					"name":"no",
					"type":"text",
					"label":"订单编号"
				},
				{
					"name":"customer.id",
					"label":"顾客",
					"type":"select",
					"options":[],
					"ajax":true,
					"url":"/customer/list",
					"convertAjaxData" : function(field, data) {
						//将返回的值转化为Field规格数据,以供重新渲染
						//做成选择分支
						data.forEach(function(customer, idx) {
							field.options.push({
								"value": customer.id,
								"caption":customer.name
							});
						});
					}
				},
				{
					"name":"orderDate",
					"value":dateFormat(),
					"type":"date",
					"label":"下单时间",
				},
				{
					"name":"shipDeadDate",
					"value":dateFormat(),
					"type":"date",
					"label":"交货期限"
				},
				{
					"name":"user.id",
					"label":"责任者",
					"type":"select",
					"options":[],
					"ajax":true,
					"url":"/user/list/json",
					"convertAjaxData" : function(field, data) {
						data.forEach(function(user, idx) {
							field.options.push({
								"value": user.id,
								"caption":user.name
							});
						});
					}
				},
				{
					"name":"comment",
					"type":"text",
					"label":"备注"
				}],
			"url":"/order/save",
			"success": function(data) {
				dlg.hide();
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":data.msg,
					"ok":function(){
						PdSys.refresh();
					}});
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});
	
	$("#delOrder").click(function(){
		var self = $(this);
		var orderIds = getSelectedRowId({"checkOne":true, "showMsg":true});
		if(orderIds.length == 0) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"msg_div",
			"caption":"删除订单",
			"type":"yesno",
			"msg":"确定删除已选订单?",
			"yes": function() {
				PdSys.ajax({
					"url":"/order/delete",
					"data":{"id":orderIds[0]},
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":data.msg,
							"ok":function(){
								PdSys.refresh();
							}});
					},
					"error": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":data.msg});
					}
				});
			}
		});
		
	});

});