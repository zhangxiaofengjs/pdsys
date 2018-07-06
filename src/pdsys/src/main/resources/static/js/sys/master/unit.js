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
						"required":"required",
					},
					{
						"name":"ratio",
						"label":"换算为",
						"type":"number",
						"value":"0",
						"required":"required",
					},
					{
						"name":"subName",
						"label":"小单位",
						"type":"text",
						"value":"",
						"required":"required",
					},
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