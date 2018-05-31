$(document).ready(function(){
	$("#addUser").click(function(){
		var self = $(this);

		var fields = [
			{
				"name":"no",
				"label":"工号",
				"type":"text",
				"required":"required",
			},
			{
				"name":"name",
				"label":"姓名",
				"type":"text",
				"required":"required",
			},
			{
				"name":"password",
				"label":"初始密码",
				"type":"label",
				"value":"123",
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
				PdSys.alert(data.msg);
			}
		});
	});

	$("#editUser").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true, "showMsg":true});
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
				"type":"label",
				"value": "",
				"required":"required",
				"readonly":"readonly",
				"depend": true
			},
			{
				"name":"name",
				"label":"姓名",
				"type":"text",
				"value":"",
				"required":"required",
				"depend": true
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
			"caption":"编辑用户",
			"fields":fields,
			"ajax": {
				"url":"/user/get",
				"data":{
					"id":id
				},
				"convertAjaxData":function(data) {
					dlg.rebuildFieldWithValue("no", data.user.no);
					dlg.rebuildFieldWithValue("name", data.user.name);
					dlg.rebuildFieldWithValue("phone", data.user.phone);
					dlg.rebuildFieldWithValue("address", data.user.address);
				}
			},
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
	
	$("#initPwd").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true, "showMsg":true});
		if(selIds.length != 1) {
			return;
		}
		
		var id = selIds[0];
		
		PdSys.ajax({
			"url":"/user/initpwd",
			"data": {
				"id": id
			},
			"success": function() {
				PdSys.alert("重置为[123]成功，请用户及时修改密码确保安全。");
			},
			"error": function() {
				PdSys.alert("重置密码失败");
			}
		});
	});
	
	$("#setAuth").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true, "showMsg":true});
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
				"name":"auth",
				"label":"权限设定",
				"type":"label",
				"value": '<table class="table table-bordered table-condensed">\
							<tr>\
								<td colspan=3><input type="checkbox" name="admin"><span style="color:red;"><b> 管理员</b></span></td>\
							</tr>\
							<tr>\
								<td colspan=3><input type="checkbox" name="e_order"> 订单编辑</td>\
							</tr>\
							<tr>\
								<td colspan=3><input type="checkbox" name="e_purchase"> 采购编辑</td>\
							</tr>\
							<tr>\
								<td><input type="checkbox" name="e_warehouse_entry_pn"> 生产入库</td>\
								<td><input type="checkbox" name="e_warehouse_entry_bom"> 原包材入库</td>\
								<td><input type="checkbox" name="e_warehouse_entry_device"> 设备零件入库</td>\
							</tr>\
							<tr>\
								<td><input type="checkbox" name="e_warehouse_delivery_pn"> 生产出库</td>\
								<td><input type="checkbox" name="e_warehouse_delivery_bom"> 原包材出库</td>\
								<td><input type="checkbox" name="e_warehouse_delivery_device"> 设备零件出库</td>\
							</tr>\
							<tr>\
								<td colspan=3><input type="checkbox" name="e_device"> 设备编辑</td>\
							</tr>\
							<tr>\
								<td><input type="checkbox" name="e_user"> 用户编辑</td>\
								<td colspan=2><input type="checkbox" name="e_master"> 定义表编辑</td>\
							</tr>\
						</table>',
			},
		];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"设定用户权限",
			"fields":fields,
			"url":"/user/setauth",
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
