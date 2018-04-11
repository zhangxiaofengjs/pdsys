$(function () {
	
	//返回上一页
	$("a[name='showBomDetail']").click(function(){
		var msgDlg = new CommonDlg();
		msgDlg.showMsgDlg({
			"target":"msg_div",
			"type":"ok",
			"msg":"删除成功!",
			"ok":function(){
				PdSys.refresh();
			}});
	
	});
});