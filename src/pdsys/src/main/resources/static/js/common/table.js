$(document).ready(function(){
	//选择处理
	$("input[name^='select']").click(function(){
		var self = $(this);
		var rowid = self.attr("rowid");
		var checked = self.prop("checked");

		if(self.attr("name")=="selectAll") {
			//全选
			var chkBoxes = $(":checkbox[name='select']");
			chkBoxes.each(function() {
				if(checked) {
					 $(this).prop('checked', true)
				}
				else {
					$(this).removeProp('checked')
				}
			});
			var rows = $("tr");
			rows.each(function() {
				if($(this).attr("rowid") != null) {
					if(checked) {
						$(this).addClass("info");
					} else {
						$(this).removeClass("info");
					}
				}
			});
		} else {
			if(checked) {
				$("tr[rowid='" + rowid + "']").addClass("info");
			} else {
				$("tr[rowid='" + rowid + "']").removeClass("info");
			}
		}
	});
});

//取到选择的Id
function getSelectedRowId(option) {
	var chkBoxes = $(":checkbox[name='select']");
	var selectIdArr = [];
	
	chkBoxes.each(function() {
		if($(this).prop('checked') ) {
			selectIdArr.push($(this).attr("rowid"));
		}
	});

	if(!selectIdArr || selectIdArr.length == 0) {
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"msg_div",
			"type":"ok",
			"msg":"请选择要操作的对象。"});
		return selectIdArr;
	}
	
	if(option && option.checkOne && selectIdArr.length != 1) {
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"msg_div",
			"type":"ok",
			"msg":"请只选择一个要操作的对象。"});
		return selectIdArr;
	}
	return selectIdArr;
}
