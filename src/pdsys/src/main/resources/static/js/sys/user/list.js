$(document).ready(function(){
	$("#addUser").click(function(){
		var self = $(this);

		var fields = [
			{
				"name":"no",
				"label":"工号",
				"type":"text",
			},
			{
				"name":"name",
				"label":"姓名",
				"type":"text",
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
			"caption":"添加用户",
			"fields":fields,
			"url":"/user/add",
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

	$("#editUser").click(function(){
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
				"name":"no",
				"label":"工号",
				"type":"text",
				"value": $('#no_' + id).html()
			},
			{
				"name":"name",
				"label":"姓名",
				"type":"text",
				"value":$('#name_' + id).html()
			},
			{
				"name":"phone",
				"label":"手机",
				"type":"text",
				"value":$('#phone_' + id).html()
			},
			{
				"name":"address",
				"label":"地址",
				"type":"text",
				"value":$('#addr_' + id).html()
			},
		];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"编辑用户",
			"fields":fields,
			"url":"/user/update",
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
