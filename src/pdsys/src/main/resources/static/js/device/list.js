$(document).ready(function(){
	$("#changeState").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true,"showMsg":true});
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
			"value":'',
			"required":"required"
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
			"groupButtons":[{
				"name":"addPlace",
				"text":"+",
				"click": function(dlg) {
					var dlgPlace = new CommonDlg();
					dlgPlace.showFormDlg({
						"target":"place_dlg_div",
						"caption":"添加地点",
						"fields":[
							{
								"name":"name",
								"label":"地点",
								"type":"text",
								"value":"",
								"required":"required",
							}
						],
						"url":"/place/add",
						"success" : function(data) {
							dlgPlace.hide();
							dlg.rebuildFieldWithValue("place.id", data.place.id);
						},
						"error": function(data) {
							PdSys.alert(data.msg);
						}
					});
				}
			}],
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
	
	//选择后合计维护备件表
	$("input[name^='select']").click(function(){
		var selIds = getSelectedRowId({"showMsg":false});
		
		PdSys.ajax({
			"url":"/device/machineparts",
			"data":selIds,
			"success": function(data) {
				var bodyHtml = "";
				data.data.forEach(function(obj, idx) {
					var machineStr = "";
					obj.machines.forEach(function(machine, idx2) {
						machineStr += machine.pn + " " + machine.name + "</br>";
					});
					bodyHtml += "<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td></tr>".format(
						obj.machinePart.pn, obj.machinePart.name, machineStr, obj.machinePart.unit.name, obj.maitenaceNum, obj.wareHouseNum
					);
				});
				var body = $('#machineParts');
				body.children().remove();
				body.append(bodyHtml);
			},
			"error": function(data) {
				PdSys.alert("查询备件库发生错误，请刷新后重试。");
			}
		});
	});
});
