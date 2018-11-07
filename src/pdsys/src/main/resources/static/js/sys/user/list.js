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
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_initPwd_div",
			"caption":"重置密码",
			"msg": "确定重置该用户密码?",
			"type": "yesno",
			"yes": function() {
				PdSys.ajax({
					"url":"/user/initpwd",
					"data": {
						"id": id
					},
					"success": function() {
						dlg.hide();
						PdSys.alert("重置为[123]成功，请用户及时修改密码确保安全。");
					},
					"error": function() {
						dlg.hide();
						PdSys.alert("重置密码失败");
					}
				});
			}
		});
	});
	
	function isValidAuth(roles, role) {
		if(roles==null || roles.length==0) {
			return false;
		}
		
		for(var idx = 0; idx < roles.length; idx++) {
			if(roles[idx].role == role) {
				return true;
			}
		}
		return false;
	}
	
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
				"value":id
			},
			{
				"name":"auth",
				"label":"权限设定",
				"type":"label",
				"ajax":true,
				"ajaxData":{"id":id},
				"url":"/user/get",
				"value": "",
				"convertAjaxData" : function(thisField, data) {
					var user = data.user;
					var roles = user.roles;
					//以下name属性注意参考class ROLE
					var strHtml = 
						'<table class="table table-bordered table-condensed">\
							<tr>\
								<td colspan=3><input type="checkbox" name="admin" {0}><span style="color:red;"><b> 管理员</b></span></td>\
							</tr>\
							<tr>\
								<td colspan=3><input type="checkbox" name="e_order" {1}> 订单编辑</td>\
							</tr>\
							<tr>\
								<td><input type="checkbox" name="e_purchase" {2}> 采购编辑</td>\
								<td colspan=2><input type="checkbox" name="app_purchase" {3}> 采购单承认</td>\
							</tr>\
							<tr>\
								<td><input type="checkbox" name="e_warehouse_pn" {14}> <span style="color:red;"><b>成品库存编辑</b></span></td>\
								<td colspan=2><input type="checkbox" name="e_warehouse_bom" {13}> <span style="color:red;"><b>原包材库存编辑</b></span></td>\
							</tr>\
							<tr>\
								<td><input type="checkbox" name="e_warehouse_entry_pn" {4}> 生产入库</td>\
								<td><input type="checkbox" name="e_warehouse_entry_bom" {5}> 原包材入库</td>\
								<td><input type="checkbox" name="e_warehouse_entry_device" {6}> 设备零件入库</td>\
							</tr>\
							<tr>\
								<td><input type="checkbox" name="e_warehouse_delivery_pn" {7}> 生产出库</td>\
								<td><input type="checkbox" name="e_warehouse_delivery_bom" {8}> 原包材出库</td>\
								<td><input type="checkbox" name="e_warehouse_delivery_device" {9}> 设备零件出库</td>\
							</tr>\
							<tr>\
								<td colspan=3><input type="checkbox" name="e_device" {10}> 设备编辑</td>\
							</tr>\
							<tr>\
								<td><input type="checkbox" name="e_user" {11}><span style="color:red;"><b> 用户编辑</b></span></td>\
								<td colspan=2><input type="checkbox" name="e_master" {12}><span style="color:red;"><b> 定义表编辑</b></span></td>\
							</tr>\
						</table>'.format(
							isValidAuth(roles,"admin") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_order") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_purchase") ? 'checked="checked"' : '',
							isValidAuth(roles,"app_purchase") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_warehouse_entry_pn") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_warehouse_entry_bom") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_warehouse_entry_device") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_warehouse_delivery_pn") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_warehouse_delivery_bom") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_warehouse_delivery_device") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_device") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_user") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_master") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_warehouse_bom") ? 'checked="checked"' : '',
							isValidAuth(roles,"e_warehouse_pn") ? 'checked="checked"' : ''
						);
					thisField.ajax = false;//防止重复请求
					dlg.rebuildFieldWithValue("auth", strHtml);
				},
				
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
