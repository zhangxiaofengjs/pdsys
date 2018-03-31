$(document).ready(function(){
	//出库处理
	$("button[name^='checkout']").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId();
		if(selIds.length == 0) {
			showMsgDlg({
				"target":"#msg_div",
				"msg":"请选择要添加到出库的单的对象。"});
			return;
		}
		
		//显示选择收件人
		showFormDlg({
			"target":"#checkout_dlg",
			"msg":"请选择要添加到出库的单的对象。"});
		
		//添加到出库单
	});
});
