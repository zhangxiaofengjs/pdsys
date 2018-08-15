$(function () {
	
	//返回上一页
	$("#reback").click(function(){
		PdSys.refresh("/order/list");
		//history.go(-1); 无品目的订单刷新几次后，不能正确退回
	});
	
	//添加品目
	$("#addPn").click(function(){
		var self = $(this);
		var caption = "添加品目";
		var fields = [
		{
			"name":"order.id",
			"type":"hidden",
			"value":$('#order_id').val()
		},
		{
			"name":"pn.id",
			"label":"品目名",
			"type":"select",
			"data":[],
			"options":[],
			"ajax":true,
			"url":"/pn/list/json",
			"convertAjaxData" : function(thisField, data) {
				var self = this;
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				data.pns.forEach(function(pn, idx) {
					thisField.options.push({
					"value": pn.id,
					"caption": "{0} {1}".format(pn.pn, pn.name)
					});
					self.data.push({
						"unitName":pn.unit.name,
						"price":pn.price,
						"priceUnitId":(pn.priceUnit == null ? 0 : pn.priceUnit.id),
					});
				});
			},
			"afterBuild": function() {
				var self = this;
				var thisElem = dlg.fieldElem(self);
				//select选择以后刷新品目
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					var data = self.data[selIndex];

					//单位
					dlg.rebuildFieldWithValue("price", data.price);
					dlg.rebuildFieldWithValue("priceUnit.id", data.priceUnitId);
					dlg.rebuildFieldWithValue("pn.unit.name", data.unitName);
				});
				
				thisElem.trigger("change");
			}
		},
		{
			"name":"price",
			"label":"单价",
			"type":"number",
			"value":"1.0",
			"min":0.000000001,
		},
		{
			"name":"priceUnit.id",
			"label":"单价单位",
			"type":"select",
			"options":[],
			"required":"required",
			"ajax":true,
			"ajaxData": {
				"type":2, //货币单位
			},
			"url":"/unit/list/json",
			"convertAjaxData" : function(thisField, data) {
				thisField.options = [];
				data.units.forEach(function(unit, idx) {
					thisField.options.push({
					"value": unit.id,
					"caption": unit.name,
					});
				});
			},
		},
		{
			"name":"num",
			"label":"数量",
			"type":"number",
			"min":1,
		},
		{
			"name":"pn.unit.name",
			"label":"数量单位",
			"type":"label",
		}];
		
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"addOrUpPn_div",
			"caption":caption,
			"fields":fields,
			"url":"/orderPn/add",
			"success": function(data) {
				dlg.hide();
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":"添加品目成功!",
					"ok":function(){
						PdSys.refresh();
					}});
			},
			"error": function(data) {
				var msgDlg = new CommonDlg();
				msgDlg.showMsgDlg({
					"target":"msg_div",
					"type":"ok",
					"msg":data.msg});
			}
		});
	});
	
	//删除品目
	$("#delPn").click(function(){
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
			"caption":"删除品目",
			"type":"yesno",
			"msg":"确定删除已选品目?",
			"yes": function() {
				PdSys.ajax({
					"url":"/orderPn/delete",
					"data":ajaxDatas,
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"删除成功!",
							"ok":function(){
								PdSys.refresh();
							}});
					},
					"error": function(data) {
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":"删除失败,请联系管理员!"});
					}
				});
			}
		});
	});
	
	//修改品目
	$("#UpdatePn").click(function(){
		var self = $(this);
		var caption = "修改品目";
		var option = {
			"showMsg":true,
			"checkOne":true
		};
		var selIds = getSelectedRowId(option);
		if(selIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"addOrUpPn_div",
			"caption":caption,
			"fields":[
				{
					"name":"id",
					"type":"hidden",
					"value":selIds[0]
				},
				{
					"name":"pn.name",
					"label":"品目名",
					"type":"label",
					"ajax":true,
					"url":"/orderPn/get",
					"ajaxData":{"id":selIds[0]},
					"convertAjaxData" : function(field, data) {
						var op = data.orderPn;
						field.ajax = false;//防止循环调用自身ajax
						dlg.rebuildFieldWithValue("pn.name", "{0} {1}".format(op.pn.pn, op.pn.name));
						dlg.rebuildFieldWithValue("price", op.price);
						dlg.rebuildFieldWithValue("priceUnit.id", op.priceUnit.id);
						dlg.rebuildFieldWithValue("num", op.num);
						dlg.rebuildFieldWithValue("unitName", op.pn.unit.name);
					}
				},
				{
					"name":"price",
					"label":"单价",
					"type":"number",
					"value":"1.0",
					"min":0.000000001,
				},
				{
					"name":"priceUnit.id",
					"label":"单价单位",
					"type":"select",
					"options":[],
					"required":"required",
					"ajax":true,
					"depend": true,
					"ajaxData": {
						"type":2, //货币单位
					},
					"url":"/unit/list/json",
					"convertAjaxData" : function(thisField, data) {
						data.units.forEach(function(unit, idx) {
							thisField.options.push({
							"value": unit.id,
							"caption": unit.name,
							});
						});
					},
				},
				{
					"name":"num",
					"type":"number",
					"label":"数量"
				},
				{
					"name":"unitName",
					"label":"单位",
					"type":"label",
				}],

	    	"url":"/orderPn/update",
	        "success":function(data) {
	        	PdSys.refresh();
	        },
	        "error":function(data) {
	        	PdSys.alert(data.msg);
	        }
	    });
	});
	
	//当前订单的BOM信息
	$("#showBomInfo").click(function(){
		var url = PdSys.url('/orderPn/bomInfo/list?id=' + $('#order_id').val());
		$(location).attr('href', url);
	});

	$("#printBtn").click(function(){
		PdSys.print("detailDiv");
	});
});