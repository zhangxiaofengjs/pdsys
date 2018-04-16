function createUnitGroupButtons(option) {
	return [{
		"name":"addUnit",
		"text":"+",
		"click": function(dlg) {
			var dlgUnit = new CommonDlg();
			dlgUnit.showFormDlg({
				"target":option.target,
				"caption":"添加单位",
				"fields":[
					{
						"name":"name",
						"label":"单位",
						"type":"text",
						"value":"",
					}
				],
				"url":"/unit/add",
				"success" : function(data) {
					dlgUnit.hide();
					option.success("add", data);
				},
				"error": function(data) {
					PdSys.alert(data.msg);
				}
			});
		}
	}]
}