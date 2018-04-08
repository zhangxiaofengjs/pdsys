$(document).ready(function(){
	$("#changeState").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true});
		if(selIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"选择设备状态",
			"fields":[
				{
					"name":"id",
					"type":"hidden",
					"value":selIds[0]
				},
				{
					"name":"state",
					"label":"设备状态",
					"type":"select",
					"options":[
						{ 'value': 0, "caption" : "运行中"},
						{ 'value': 1, "caption" : "维护中"},
						{ 'value': 2, "caption" : "故障"},
					],
				}
			],
	    	url : "/device/update",
	        success : function(data) {
	        	PdSys.refresh();
	        },
	        error: function(data) {
	        	PdSys.sysError();
	        }
	    });
	});
	
	$("#addDevice").click(function(){
		var self = $(this);

		var fields = [
		{
			"name":"no",
			"label":"编号",
			"type":"text",
			"value":''
		},
		{
			"name":"machine.id",
			"label":"机器设备",
			"type":"select",
			"options":[],
			"ajax":true,
			"url":"/machine/list/json",
			"convertAjaxData" : function(thisField, data) {
				data.forEach(function(machine, idx) {
					thisField.options.push({
						"value": machine.id,
						"caption":"{0} {1}".format(machine.pn, machine.name),
					});
				});
			},
		},
		{
			"name":"place.id",
			"label":"使用地点",
			"type":"select",
			"options":[],
			"ajax":true,
			"url":"/place/list/json",
			"convertAjaxData" : function(thisField, data) {
				data.forEach(function(place, idx) {
					thisField.options.push({
						"value": place.id,
						"caption": place.name,
					});
				});
			},
		},
		{
			"name":"user.id",
			"label":"负责人",
			"type":"select",
			"options":[],
			"ajax":true,
			"url":"/user/list/json",
			"convertAjaxData" : function(thisField, data) {
				data.forEach(function(user, idx) {
					thisField.options.push({
						"value": user.id,
						"caption": user.name,
					});
				});
			},
		},
		];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"追加新设备",
			"fields":fields,
			"url":"/device/add",
			"success": function(data) {
				dlg.hide();
				PdSys.success({
					"ok" : function() {
						PdSys.refresh();
					}
				});
			},
			"error": function(data) {
				PdSys.sysError();
			}
		});
	});
});
