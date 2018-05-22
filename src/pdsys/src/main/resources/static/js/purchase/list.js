$(function () {
	
	//删除采购单
	$("#delPurchase").click(function(){
		var self = $(this);
		var purchaseIds = getSelectedRowId();
		if(purchaseIds.length == 0) {
			return;
		}
		
		var ajaxDatas = [];
		$.each(purchaseIds, function() {
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
	
	//采购单入库
	$("#entryPurchase").click(function(){
		var self = $(this);
		var purchaseIds = getSelectedRowId({"checkOne":true, "showMsg":true});
		if(purchaseIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"entry_alert_div",
			"caption":"采购单入库-选择入库提交人",
			"fields":[
				{
					"name":"id",
					"type":"hidden",
					"value":purchaseIds[0]
				},
				{
					"name":"wareHouseEntry.type",
					"type":"hidden",
					"value":"1"
				},
				{
					"name":"wareHouseEntry.no",
					"label":"入库单号",
					"type":"text",
					"value":"1-" + dateYYYYMMDD() + "-",
					"required":"required"
				},
				{
					"name":"wareHouseEntry.user.id",
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
				},
				{
					"name":"wareHouseEntry.comment",
					"label":"备注",
					"type":"text",
					"value":""
				}
			],
	    	url : "/purchase/entry",
	        success : function(data) {
	        	PdSys.refresh();
	        },
	        error: function(data) {
	        	PdSys.alert(data.msg);
	        }
	    });
	});
});