$(document).ready(function(){
	$("button[name='deliveryList']").click(function(){
		var self = $(this);
		PdSys.refresh('/warehouse/delivery/main/bom?no=&content=list');
	});
	
	//新建出库单
	$("button[name='addDelivery']").click(function(){
		var self = $(this);
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"选择出库领收人",
			"fields":[
				{
					"name":"type",
					"type":"hidden",
					"value":"1",
				},
				{
					"name":"no",
					"label":"出库单号",
					"type":"text",
					"value":"1-" + dateYYYYMMDD() + "-",
					"required":"required"
				},
				{
					"name":"user.id",
					"label":"领收人",
					"type":"select",
					"options":[],
					"ajax":true,
					"url":"/user/list/json",
					"convertAjaxData" : function(thisField, data) {
						//将返回的值转化为Field规格数据,以供重新渲染
						//做成选择分支
						data.forEach(function(user, idx) {
							thisField.options.push({
								"value": user.id,
								"caption":user.name,
							});
						});
					}
				},
				{
					"name":"comment",
					"label":"备注",
					"type":"text",
					"value":"",
				},
			],
	    	url : "/warehouse/delivery/add/delivery",
	        success : function(data) {
	        	$(location).attr('href', PdSys.url('/warehouse/delivery/main/bom?no=' + data.delivery.no+'&content=one'));
	        },
	        error: function(data) {
    			PdSys.alert(data.msg);
	        }
	    });
	});
	
	$("button[name='addDeliveryBOM']").click(function(){
		var self = $(this);

		var fields = [
		{
			"name":"wareHouseDelivery.id",
			"type":"hidden",
			"value":$('#delivery_id').val()
		},
		{
			"name":"bom.id",
			"label":"品番",
			"type":"select",
			"options":[],
			"min":1,
			"ajax":true,
			"url":"/warehouse/list/bom/json",
			"ajaxData":{
				"filterCond":{"minCount": 1}
			},
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options = [];
				data.warehouseboms.forEach(function(whbom, idx) {
					var bom = whbom.bom; 
					thisField.options.push({
						"value": bom.id,
						"caption": M.bomName(bom),
						"data": {"unitName":bom.unit.name,"num":whbom.num}
					});
				});
			},
			"afterBuild": function() {
				var self = this;
				
				var thisElem = dlg.findFieldElem(self);
				
				//select选择以后刷新品目单位
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					var val = null;
					if(selIndex != -1) {
						val = self.options[selIndex].data;
					}
					dlg.rebuildFieldWithValue("wareHouseBomNum", val==null?"":val.num);
					dlg.rebuildFieldWithValue("unit.name", val==null?"":val.unitName);
				});
				thisElem.trigger("change");
			},
		},
		{
			"name":"wareHouseBomNum",
			"label":"在库数量",
			"type":"label",
			"value":"",
		},
		{
			"name":"num",
			"label":"出库数量",
			"type":"number",
			"min":1,
			"value":"",
		},
		{
			"name":"unit.name",
			"label":"单位",
			"type":"label",
			"value":"",
		}];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"dlg_div",
			"caption":"添加到出库单",
			"fields":fields,
			"valid":function() {
				return true;
			},
			"url":"/warehouse/delivery/add/bom",
			"success": function(data) {
				dlg.hide();
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":"成功添加到出库单!",
					"ok": function() {
						PdSys.refresh();
					}});
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});
	
	$("button[name='deleteDeliveryBOM']").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId();
		if(selIds.length == 0) {
			return;
		}
		
		var ajaxDatas = [];
		$.each(selIds, function() {
			ajaxDatas.push({"id":this});
		});
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除出库单",
			"type":"yesno",
			"msg":"确定删除选择的出库项目?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/delivery/delete/bom",
					"data":ajaxDatas,
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"删除成功!",
							"ok": function() {
								PdSys.refresh();
							}});
						
					},
					"error": function(data) {
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
	
	$("button[name='doDelivery']").click(function(){
		var self = $(this);
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"确定出库单",
			"type":"yesno",
			"msg":"确定执行出库?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/delivery/delivery/" + $("#delivery_id").val(),
					"success": function(data) {
						dlg.hide();
						
						if(data.success) {
							var msgDlg = new CommonDlg();
							msgDlg.showMsgDlg({
								"target":"msg_div",
								"type":"ok",
								"msg":"出库成功!",
								"ok": function(){
									PdSys.refresh();
								}});
						} else {
							var msgDlg = new CommonDlg();
							msgDlg.showMsgDlg({
								"target":"msg_div",
								"type":"ok",
								"msg":data.msg});
						}
					},
					"error": function(data) {
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
	
	$("button[name='deleteDelivery']").click(function(){
		var self = $(this);
		var selIds = getSelectedRowId({"checkOne":true, "showMsg":true});
		if(selIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"dlg_div",
			"caption":"删除出库单",
			"type":"yesno",
			"msg":"确定删除出库单?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/delivery/delete/delivery/" + selIds[0],
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"出库单已删除!",
							"ok": function(){
								PdSys.refresh();
							}});
					},
					"error": function(data) {
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
	
	$("#printBtn").click(function(){
		PdSys.print("detailDiv");
	});
});
