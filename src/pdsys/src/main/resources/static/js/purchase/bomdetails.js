$(function () {
	
	//生成采购单
	$("#addPurchase").click(function(){
		var self = $(this);
		var bomIds = getSelectedRowId();
		if(bomIds.length == 0) {
			return;
		}
		
		//
		var orderNo = $('#order_no').val();
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"addPurchase_div",
			"caption":"新增采购单",
			"fields":[
				{
					"name":"no",
					"label":"采购单号",
					"type":"text",
					"value":"D_" + dateYYYYMMDDHHmmss()
				},
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
				}],
			"url":'/purchase/save?orderNo='+ orderNo +'&bomIds='+ bomIds,
	        success : function(data) {
	        	var url = '/purchase/purchasebomlist?purchaseId=' + data.purchaseId;
	        	$(location).attr('href', PdSys.url(url));
	        },
	        error: function(data) {
    			PdSys.alert(data.msg);
	        }
		});
	});

});