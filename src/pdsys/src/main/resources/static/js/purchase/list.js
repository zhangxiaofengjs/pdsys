$(function () {
	
	//删除采购单
	$("#delPurchase").click(function(){
		var self = $(this);
		var purchaseBomIds = getSelectedRowId();
		if(purchaseBomIds.length == 0) {
			return;
		}
		
		var ajaxDatas = [];
		$.each(purchaseBomIds, function() {
			ajaxDatas.push({"id":this});
		});
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"delete_alert_div",
			"caption":"删除采购单",
			"type":"yesno",
			"msg":"确定删除已选采购单?",
			"yes": function() {
				PdSys.ajax({
					"url":"/purchase/delete/purchase",
					"data":ajaxDatas,
					"success": function(data) {
						dlg.hide();
						PdSys.success({
							"ok":function(){
								PdSys.refresh();
							}
						});
					},
					"error": function(data) {
						dlg.hide();
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
});