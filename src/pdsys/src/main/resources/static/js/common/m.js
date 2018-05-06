function M() {
};

M.bomName = function(bom) {
	if(bom == null || bom == undefined) {
		return "";
	}
	
	var str = "{0} {1}".format(bom.pn, bom.name);
	if(bom.comment != null && bom.comment != null && bom.comment != "") {
		str += "(" + bom.comment + ")";
	}
	
	return str;
}