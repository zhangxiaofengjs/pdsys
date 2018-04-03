$(document).ready(function(){
	//出库处理
	$("button[name^='checkout']").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId();

		var dlg = new CommonDlg();
		if(selIds.length == 0) {
			dlg.showMsgDlg({
				"target":"msg_div",
				"msg":"请选择要添加到出库的单的对象。"});
			return;
		}
		
		//显示选择收件人
		dlg.showFormDlg({
			"target":"checkout_dlg_div",
			"caption":"添加到出库单",
			"fields":[
				{
					"name":"bom.id",
					"type":"hidden",
					"value":selIds.join(",")
				},
				{
					"name":"wareHouseDelivery.user.id",
					"label":"领收人",
					"type":"select",
					"ajax":true,
					"url":"/user/list",
					"convertAjaxData" : function(data) {
						//将返回的值转化为Field规格数据,以供重新渲染
						var field = {
							"name":"wareHouseDelivery.user.id",
							"label":"领收人",
							"type":"select",
							"options":[]
						};
						//做成选择分支
						data.forEach(function(user, idx) {
							field.options.push({
								"value": user.id,
								"caption":user.name,
							});
						});
						
						return field;
					}
				},
				{
					"name":"num",
					"label":"数量",
					"type":"text",
					"value":"1",
				}
			],
			"url":"/warehouse/addcheckout",
			"success": function(data) {
				alert(data);
			},
			"error": function(data) {
				alert(22);
			}
		});
	});
});
