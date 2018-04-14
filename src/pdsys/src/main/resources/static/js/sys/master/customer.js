$(document).ready(function(){
	$("#addCustomer").click(function(){
		var self = $(this);

		var fields = [
			{
				"name":"name",
				"label":"姓名",
				"type":"text",
				"requried":"requried"
			},
			{
				"name":"phone",
				"label":"手机",
				"type":"text",
			},
			{
				"name":"address",
				"label":"地址",
				"type":"text",
			},
		];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加客户",
			"fields":fields,
			"url":"/customer/add",
			"success": function(data) {
				dlg.hide();
				PdSys.success({
					"ok" : function() {
						PdSys.refresh();
					}
				});
			},
			"error": function(data) {
				PdSys.sysError();
			}
		});
	});

	$("#editCustomer").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true});
		if(selIds.length != 1) {
			return;
		}
		
		var id = selIds[0];
		var fields = [
			{
				"name":"id",
				"type":"hidden",
				"value":selIds[0]
			},
			{
				"name":"name",
				"label":"姓名",
				"type":"text",
				"value":"",
				"requried":"requried",
				"depend": true,
			},
			{
				"name":"phone",
				"label":"手机",
				"value":"",
				"depend": true
			},
			{
				"name":"address",
				"label":"地址",
				"value":"",
				"depend": true
			},
		];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"编辑客户",
			"fields":fields,
			"ajax": {
				"url":"/customer/get",
				"data":{
					"id":id
				},
				"convertAjaxData":function(data) {
					dlg.rebuildFieldWithValue("name", data.customer.name);
					dlg.rebuildFieldWithValue("phone", data.customer.phone);
					dlg.rebuildFieldWithValue("address", data.customer.address);
				}
			},
			"url":"/customer/update",
			"success": function(data) {
				dlg.hide();
				PdSys.success({
					"ok" : function() {
						PdSys.refresh();
					}
				});
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});
});
