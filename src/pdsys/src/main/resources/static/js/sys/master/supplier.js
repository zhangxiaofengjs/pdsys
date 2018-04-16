function createSupplierGroupButtons(option) {
	return [{
		"name":"addSupplier",
		"text":"+",
		"click": function(dlg) {
			var dlgSupplier = new CommonDlg();
			dlgSupplier.showFormDlg({
				"target":option.target,
				"caption":"添加供应商",
				"fields":[
					{
						"name":"name",
						"label":"供应商名",
						"type":"text",
						"value":"",
					},
					{
						"name":"phone",
						"label":"电话",
						"type":"text",
						"value":"",
					},
					{
						"name":"address",
						"label":"地址",
						"type":"text",
						"value":"",
					}
				],
				"url":"/supplier/add",
				"success" : function(data) {
					dlgSupplier.hide();
					option.success("add", data);
				},
				"error": function(data) {
					PdSys.alert(data.msg);
				}
			});
		}
	}]
}