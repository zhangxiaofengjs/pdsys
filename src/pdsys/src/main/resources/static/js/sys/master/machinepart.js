function createMachinePartGroupButtons(option) {
	return [{
		"name":"addmp",
		"text":"+",
		"click": function(dlg) {
			var dlgMP = new CommonDlg();
			dlgMP.showFormDlg({
				"target":option.target,
				"caption":"添加机器维护零件",
				"fields":[
					{
						"name":"pn",
						"label":"品番",
						"type":"text",
						"value":"",
					},
					{
						"name":"name",
						"label":"名称",
						"type":"text",
						"value":"",
					},
					{
						"name":"unit.id",
						"label":"单位",
						"type":"select",
						"value":"",
						"ajax":true,
						"url":"/unit/list/json",
						"convertAjaxData" : function(thisField, data) {
							thisField.options = [];
							data.forEach(function(unit, idx) {
								thisField.options.push({
									"value": unit.id,
									"caption":unit.name,
								});
							});
						}
					},
					{
						"name":"supplier.id",
						"label":"供应商",
						"type":"select",
						"value":"",
						"ajax":true,
						"url":"/supplier/list/json",
						"convertAjaxData" : function(thisField, data) {
							thisField.options = [];
							data.forEach(function(supplier, idx) {
								thisField.options.push({
									"value": supplier.id,
									"caption":supplier.name,
								});
							});
						}
					}
				],
				"url":"/machinepart/add",
				"success" : function(data) {
					dlgMP.hide();
					option.success("add", data);
				},
				"error": function(data) {
					PdSys.alert(data.msg);
				}
			});
		}
	}]
}