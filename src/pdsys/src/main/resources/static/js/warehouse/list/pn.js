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
});
