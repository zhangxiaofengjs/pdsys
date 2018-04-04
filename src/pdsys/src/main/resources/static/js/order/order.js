$(function () {
	$("#addOrder").click(function(){
		var dlg = new CommonDlg();
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
					"ajax":true,
					"url":"/customer/list",
					"options":[],
					"convertAjaxData" : function(field, data) {
						console.log(data);
						//将返回的值转化为Field规格数据,以供重新渲染
						//做成选择分支
						data.forEach(function(customer, idx) {
							field.options.push({
								"value": customer.id,
								"caption":customer.name,
							});
						});
					}
				}],
			"url":"/order/save",
			"success": function(data) {
				document.location.reload();
			},
			"error": function(data) {
				alert(22);
			}
		});
	});
	
	$("#delOrder").click(function(){
		var orderIds = getSelectedRowId();
		var dlg = new CommonDlg();
		if(orderIds.length == 0) {
			dlg.showMsgDlg({
				"target":"msg_div",
				"msg":"请选择要删除的订单！！！"});
			return;
		}
		
		$.ajax({
			type: "POST",
		    url: PdSys.url("/order/delete"),
            contentType: "application/json",
            data: JSON.stringify(orderIds),
		    success: function(r){
				if(r.success){
					alert('操作成功', function(){
                        alert(r.msg);
                        document.location.reload();
					});
				}else{
					alert(r.msg);
				}
			}
		});
	});

});