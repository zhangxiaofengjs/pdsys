function PdSys() {
};

//取得UrlContext路径
PdSys.contextPath = function() {
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    
    return projectName;
}

//取得拼接后Url路径
PdSys.url = function(subUrl) {
    return PdSys.contextPath() + subUrl;
}

PdSys.ajax = function(option) {
	var args = {
    	url : PdSys.url(option.url),
        type : 'post',
        dataType : 'json',//接受服务端数据类型
        contentType:"application/json",
        processData: false,
        cache: false,
        data: JSON.stringify(option.data),
        success : function(data) {
        	if(option.success) {
        		option.success(data);
        	}
        },
        error: function(data) {
        	if(option.error) {
        		option.error(data);
        	}
        }
     };
    
	//提交
	$.ajax(args);
};

PdSys.refresh = function() {
	document.location.reload();
}

PdSys.success = function(option) {
	var dlg = new CommonDlg();
	dlg.showMsgDlg({
		"target":"msg_div",
		"type":"ok",
		"msg":"操作成功。",
		"ok": function() {
			if(option.ok) {
				(option.ok)();
			}
		}});
}

PdSys.sysError = function() {
	var dlg = new CommonDlg();
	dlg.showMsgDlg({
		"target":"msg_div",
		"type":"ok",
		"msg":"发生系统错误,请联系管理员。"});
}