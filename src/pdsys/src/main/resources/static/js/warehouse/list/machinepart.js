$(document).ready(function(){
	//出库处理
	$("button[name^='checkout']").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId();

		if(!selIds || selIds.length == 0) {
			var dlg = new CommonDlg();
			dlg.showMsgDlg({
				"target":"msg_div",
				"type":"ok",
				"msg":"请选择要添加到出库的单的对象。"});
			return;
		}
		
		//显示选择收件人
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"checkout_dlg_div",
			"caption":"添加到出库单",
			"fields":[
				{
					"name":"wareHouseMachinePartIds",
					"type":"hidden",
					"value":selIds.join(",")
				},
				{
					"name":"wareHouseDeliveryMachinePart.wareHouseDelivery.user.id",
					"label":"领收人",
					"type":"select",
					"options":[],
					"ajax":true,
					"url":"/user/list",
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
					"name":"wareHouseDeliveryMachinePart.num",
					"label":"数量",
					"type":"number",
					"value":"1",
				}
			],
			"url":"/warehouse/add/delivery/machinepart",
			"success": function(data) {
				dlg.hide();
				
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":"成功添加到出库单!"});
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
});
