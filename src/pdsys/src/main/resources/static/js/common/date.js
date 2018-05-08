var dateFormat = function() {
	var nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1)
	  : nowDate.getMonth() + 1;
	var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	var dateStr = year + "-" + month + "-" + day;
	return dateStr;
};

var dateYYYYMMDD = function(date) {
	var nowDate = date;
	if(date == undefined || date == null)
		nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1)
	  : nowDate.getMonth() + 1;
	var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	var dateStr = year + "" + month + "" + day;
	return dateStr;
};

var dateYYYYMMDDHHmmss = function() {
	var nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1)
	  : nowDate.getMonth() + 1;
	var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	var dateStr = year + "" + month + "" + day + "" + nowDate.getHours() + "" + nowDate.getMinutes() + "" + nowDate.getSeconds();
	return dateStr;
};