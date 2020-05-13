$(document).ready(function(){
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
			"url":"/warehouse/import/semipn",
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
