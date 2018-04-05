$(document).ready(function(){
	//新建入库单
	$("button[name='addEntry']").click(function(){
		var self = $(this);
		
		//显示选择收件人
		$.ajax({
	    	url : PdSys.url("/warehouse/entry/add"),
	        type : 'post',
	        dataType : 'json',//接受服务端数据类型
	        contentType:"application/json",
	        processData: false,
	        cache: false,
	        success : function(data) {
	        	console.log(data);
	        	if(data.success)
	        	{
	        		console.log(data);
	        	}
	        	else
	        	{
	        	}
	        },
	        error: function(data) {
	        	console.log(data);
	        }
	    });
	});
	$("button[name='add']").click(function(){
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
					"name":"wareHousePnIds",
					"type":"hidden",
					"value":selIds.join(",")
				},
				{
					"name":"wareHouseDeliveryPn.wareHouseDelivery.user.id",
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
					"name":"wareHouseDeliveryPn.num",
					"label":"数量",
					"type":"number",
					"value":"1",
				}
				],
				"url":"/warehouse/add/delivery/pn",
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
