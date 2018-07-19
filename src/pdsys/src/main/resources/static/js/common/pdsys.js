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
        	if(data.success) {
        		if(option.success) {
        			option.success(data);
        		}
        	} else if(option.error) {
        		option.error(data);
        	} else {
        		alert("未定义处理：" + data);
        	}
        },
        error: function(data) {
        	if(!option.disableSysError) {
        		PdSys.sysError();
        	}
        }
     };
    
	//提交
	$.ajax(args);
};

PdSys.refresh = function(url) {
	if(url == undefined || url == "" || url == null) {
		document.location.reload();
	} else {
		document.location = PdSys.url(url);
	}
}

PdSys.alert = function(msg) {
	var dlg = new CommonDlg();
	dlg.showMsgDlg({
		"target":"msg_div",
		"type":"ok",
		"msg": msg});
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

PdSys.print = function(option) {
	var target = option;
	if(typeof(option) != "string") {
		target = option.target;
	}
	
	$("#" + target).print({
		title: '南通创通日用品有限公司',
	    //Use Global styles
	    globalStyles : true,
	    //Add link with attrbute media=print
	    mediaPrint : false,
	    //Custom stylesheet
	    //stylesheet : "http://fonts.googleapis.com/css?family=Inconsolata",
	    //Print in a hidden iframe
	    iframe : true,
	    //Don't print this
	    noPrintSelector : ".no-print",
	    //Add this at top
	    prepend : "",
	    //Add this on bottom
	    append : '<div style="text-align:right;color:#9c9a9a;">© 2018-2028 ZWORKS  版权所有,仅供南通创通日用品有限公司使用</div>',
	    //Log to console when printing is done via a deffered callback
	    deferred: $.Deferred().done(function() { console.log('Printing done', arguments); })
	});
}