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
				},
				{
					"name":"createDate",
					"value":dateFormat(),
					"type":"date",
					"label":"生成时间",
				}],
			"url":"/purchase/save",
			"success": function(data) {
				dlg.hide();
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":data.msg,
					"ok":function(){
						//采购单详细的保存，显示
						var url = '/purchase/saveDetail?purchaseId=' + data.id +'&orderNo='+ orderNo +'&bomIds='+ bomIds;
						$(location).attr('href', PdSys.url(url ));
					}
				});
			},
			"error": function(data) {
	        	var dlg = new CommonDlg();
    			dlg.showMsgDlg({
    				"target":"msg_div",
    				"type":"ok",
    				"msg":data.msg});
			}
		});
	});
	

});