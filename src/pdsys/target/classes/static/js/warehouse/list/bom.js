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
			"fields":[
				{
					"name":"whBomIds",
					"type":"hidden",
					"value":selIds.join(",")
				},
				{
					"name":"userName",
					"label":"领收人",
					"type":"select",
					"value":0,
					"url":"",
				}
			],
			"url":"/warehouse/addcheckout",
			"success": function(data) {
				alert(11);
			},
			"error": function(data) {
				alert(22);
			}
		});
	});
});
