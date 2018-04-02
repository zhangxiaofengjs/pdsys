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