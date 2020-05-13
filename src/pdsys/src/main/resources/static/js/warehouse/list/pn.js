$(document).ready(function(){
	//修改成品库存
	$("#updateWhPn").click(function(){
		var self = $(this);
		var caption = "修改成品库存";
		var option = {
			"showMsg":true,
			"checkOne":true
		};
		var selIds = getSelectedRowId(option);
		if(selIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"update_div",
			"caption":caption,
			"fields":[
				{
					"name":"id",
					"type":"hidden",
					"value":selIds[0]
				},
				{
					"name":"info-warning",
					"type":"label",
					"label":'***',
					"value":'<span class="red-font">此操作将更新库存状态，导致入出库单数据统计和现在库存不匹配，谨慎操作。</span>',
				},
				{
					"name":"pn.pn",
					"label":"品目",
					"type":"label",
				},
				{
					"name":"pn.name",
					"label":"名称",
					"type":"label",
				},
				{
					"name":"producedNum",
					"type":"number",
					"label":"数量",
					"min":0,
				},
				{
					"name":"unitName",
					"label":"单位",
					"type":"label",
				}],
			"ajax":{
				"url":"/warehouse/list/pn/json",
				"data": {"id":selIds[0]},
				"convertAjaxData" : function(data) {
					var whpn = data.pns[0];
					dlg.rebuildFieldWithValue("pn.pn", whpn.pn.pn);
					dlg.rebuildFieldWithValue("pn.name", whpn.pn.name);
					dlg.rebuildFieldWithValue("producedNum", whpn.producedNum);
					dlg.rebuildFieldWithValue("unitName", whpn.pn.unit.name);
				}
			},
	    	"url":"/warehouse/update/pn",
	        "success":function(data) {
	        	PdSys.refresh();
	        },
	        "error":function(data) {
	        	PdSys.alert(data.msg);
	        }
	    });
	});
	
	$("#importExcel").click(function(){
		var dlg = new CommonDlg();
		var infoDlg = new CommonDlg();
		var myDate = new Date(); 
		dlg.showFormDlg({
			"target":"importExcel_div",
			"caption":"导入成品库存Excel",
			"enctype":"multipart/form-data",
			"method":"post",
			"fields":[
				{
					"name":"info-warning",
					"type":"label",
					"label":'***',
					"value":'<span class="red-font">此操作将更新库存到Excel文件中库存状态，导致入出库单数据统计和现在库存不匹配，谨慎操作。</span>',
				},
				{
					"name":"file",
					"type":"file",
					"label":'选择导入文件',
				}],
			"url":"/warehouse/import/pn",
			"afterSubmit": function() {
				infoDlg.showMsgDlg({
					"target":"msg_info_div",
					"type":"none",
					"msg": "<img src='{0}' height='24px' /><span class='red-font'>正在执行中，请稍候...</span>"
						.format(PdSys.url("/icons/loading.gif"))});
			},
			"success": function(data) {
				infoDlg.hide();
				dlg.hide();
				PdSys.success({
					"ok":function(){
						PdSys.refresh();
					}});
			},
			"error": function(data) {
				infoDlg.hide();
				PdSys.alert(data.msg);
			}
		});
	});
});
