$(document).ready(function(){
	$("button[name='deliveryList']").click(function(){
		var self = $(this);
		PdSys.refresh('/warehouse/delivery/main/semipn?no=');
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
					"value":"3",
				},
				{
					"name":"no",
					"label":"出库单号",
					"type":"text",
					"value":"0-" + dateYYYYMMDD() + "-",
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
	        	$(location).attr('href', PdSys.url('/warehouse/delivery/main/semipn?no=' + data.delivery.no));
	        },
	        error: function(data) {
    			PdSys.alert(data.msg);
	        }
	    });
	});
	
	function getOrderPnTable(orderPn) {
		var t={};
		t.headers=[{"text":""}, {"text":"订购"}, {"text":"已出库"}, {"text":"未出库"}, {"text":"在库"}];
		t.rows=[
//			{
//				"cells":[{"text":"半成品"}, {"text":""}, {"text":""}, {"text":""}, {"text":orderPn.whpn.semiProducedNum}]
//			},
			{
				"cells":[{"text":"成品"}, {"text":orderPn.num}, {"text":orderPn.deliveredNum}, {"text":orderPn.num - orderPn.deliveredNum}, {"text":orderPn.whpn.producedNum}]
			}
		];
		return PdSysTable.buildTable(t);
	}
	
	$("button[name='addDeliveryPn']").click(function(){
		var self = $(this);

		var fields = [
		{
			"name":"wareHouseDelivery.id",
			"type":"hidden",
			"value":$('#delivery_id').val()
		},
		{
			"name":"pn.id",
			"label":"品目",
			"type":"select",
			"options":[],
			"required":"required",
			"ajax":true,
			"url":"/warehouse/list/semipn/json",
			"ajaxData":{},
			"convertAjaxData" : function(thisField, data) {
				thisField.options = [];
				
				var pnIdArr = [];
				data.semipns.forEach(function(semiPn, idx) {
					var pn = semiPn.pn;
					if(pnIdArr.indexOf(pn.id) == -1) {
						thisField.options.push({
							"value": pn.id,
							"caption": "{0} {1}".format(pn.pn, pn.name),
						});
						pnIdArr.push(pn.id);
					}
				});
			},
			"afterBuild": function() {
				var self = this;
				
				var thisElem = dlg.findFieldElem(self.name);
				
				//select选择以后品目在库情况
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					var val = -1;
					if(selIndex != -1) {
						val = self.options[selIndex].value;
					}
					var fieldCls = dlg.fieldByName("pnClsRel.pnCls.id");
					fieldCls.ajaxData={
						"pn":{"id":val}
					};
					dlg.rebuildField(fieldCls);
				});
				thisElem.trigger("change");
			}
		},
		{
			"name":"pnClsRel.pnCls.id",
			"label":"子类",
			"type":"select",
			"options":[],
			"ajax":true,
			"depend":true,
			"url":"/warehouse/list/semipn/json",
			"ajaxData":{},
			"convertAjaxData" : function(thisField, data) {
				thisField.options = [];
				data.semipns.forEach(function(semipn, idx) {
					var unit = semipn.pn.unit;
					var pnCls = semipn.pnClsRel.pnCls;
					thisField.options.push({
						"value": pnCls.id,
						"caption": pnCls.name,
						"data": {"num":semipn.num,"unitName":(unit.subName!=""?unit.subName:unit.name)}
					});
				});
			},
			"afterBuild": function() {
				var self = this;
				
				var thisElem = dlg.findFieldElem(self.name);

				//select选择以后品目在库情况
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					var fieldPnClsInfo = null;
					if(selIndex != -1) {
						fieldPnClsInfo = self.options[selIndex].data;
					}
					
					dlg.rebuildFieldWithValue("pnInfo", fieldPnClsInfo == null?"":fieldPnClsInfo.num);
					dlg.rebuildFieldWithValue("unitName", fieldPnClsInfo == null?"":fieldPnClsInfo.unitName);
				});
				thisElem.trigger("change");
			}
		},
		{
			"name":"pnInfo",
			"label":"在库数量",
			"type":"label",
			"value":"",
		},
		{
			"name":"num",
			"label":"出库数量",
			"type":"number",
			"value":"0",
			"min":"0",
			"max":10000000,
		},
		{
			"name":"unitName",
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
			"url":"/warehouse/delivery/add/semipn",
			"success": function(data) {
				dlg.hide();
				PdSys.success({
					"ok":PdSys.refresh(),
				});
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});
	
	$("button[name='deleteDeliveryPn']").click(function(){
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
			"caption":"删除",
			"type":"yesno",
			"msg":"确定删除选择的出库项目?",
			"yes": function() {
				PdSys.ajax({
					"url":"/warehouse/delivery/delete/semipn",
					"data":ajaxDatas,
					"success": function(data) {
						dlg.hide();
						PdSys.success({"ok": PdSys.refresh()});
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
						PdSys.success({"ok": PdSys.refresh()});
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
});
