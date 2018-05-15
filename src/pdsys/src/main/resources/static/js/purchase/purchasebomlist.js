$(function () {
	
	//返回上一页
	$("#reback").click(function(){
		history.go(-1);
	});
	
	//删除采购单详细
	$("#delPurchaseDetail").click(function(){
		var self = $(this);
		var purchaseBomIds = getSelectedRowId();
		if(purchaseBomIds.length == 0) {
			return;
		}
		
		var ajaxDatas = [];
		$.each(purchaseBomIds, function() {
			ajaxDatas.push({"id":this});
		});
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"delPurchase_div",
			"caption":"删除采购单详细",
			"type":"yesno",
			"msg":"确定删除已选采购单详细?",
			"yes": function() {
				PdSys.ajax({
					"url":"/purchase/delete/bom",
					"data":ajaxDatas,
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						PdSys.success({
							"ok":function(){
								PdSys.refresh();
							}
						});
					},
					"error": function(data) {
						dlg.hide();
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});
	
	//修改采购单
	$("#upPurchaseDetail").click(function(){
		var self = $(this);
		var option = {
				"showMsg":true,
				"checkOne":true
			};
		var purchaseBomIds = getSelectedRowId(option);
		if(purchaseBomIds.length != 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"upPurchase_div",
			"caption":"修改采购单详细",
			"fields":[
				{
					"name":"id",
					"type":"hidden",
					"value":purchaseBomIds[0]
				},
				{
					"name":"bomName",
					"label":"原包材名",
					"type":"label",
					"ajax":true,
					"url":"/purchase/get",
					"ajaxData":{"id":purchaseBomIds[0]},
					"convertAjaxData" : function(field, data) {
						var pb = data.pb;
						field.ajax = false;//防止循环调用自身ajax
						dlg.rebuildFieldWithValue("bomName", "{0} {1}".format(pb.bom.pn, pb.bom.name));
						dlg.rebuildFieldWithValue("num", pb.num);
						dlg.rebuildFieldWithValue("price", pb.price);
						var f = dlg.fieldByName("supplier.id");
						pb.bom.suppliers.forEach(function(supplier, idx) {
							f.options.push({
								"value": supplier.id,
								"caption":supplier.name,
							});
						});
						
						dlg.rebuildField(f);
						
					}
				},
				{
					"name":"num",
					"type":"number",
					"label":"下单量"
				},
				{
					"name":"supplier.id",
					"label":"供应商",
					"type":"select",
					"options":[],

				},
				{
					"name":"price",
					"label":"单价",
					"type":"text",
				}],

	    	"url":"/purchase/updatePB",
	        "success":function(data) {
	        	PdSys.refresh();
	        },
	        "error":function(data) {
	        	PdSys.alert(data.msg);
	        }
	    });

	});
	
	//追加采购单详细
	$("#addPurchaseDetail").click(function(){
		var purchaseId = $('#purchaseId').val();
		if(purchaseId < 1) {
			return;
		}
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"upPurchaseBom_div",
			"caption":"添加采购单详细",
			"fields":[
				{
					"name":"purchase.id",
					"type":"hidden",
					"value":purchaseId
				},
				{
					"name":"bom.id",
					"label":"原包材",
					"type":"select",
					"options":[],
					"ajax":true,
					"ajaxData":{"id":purchaseId},
					"url":"/purchase/get/bom",
					"convertAjaxData" : function(thisField, data) {
						thisField.options.push({
							"value": -1,
							"caption":"请选择原包材...",
						});
						data.boms.forEach(function(bom, idx) {
							thisField.options.push({
							"value": bom.id,
							"caption": "{0} {1}".format(bom.pn, bom.name),
							"data":bom.price
							});
						});
					},
					"afterBuild": function() {
						var self = this;
						var thisElem = dlg.fieldElem(self.type, self.name);
						//select选择以后刷新品目
						thisElem.change(function() {
							var selIndex = thisElem[0].selectedIndex;
							var bomId= self.options[selIndex].value;
							dlg.fieldElem("number", "num").val("");
							dlg.rebuildFieldWithValue("price", self.options[selIndex].data);
							var f = dlg.fieldByName("supplier.id");
							f.options.length = 0;
							//供应商
							PdSys.ajax({
								"url":"/purchase/get/supplier",
								"data": {"id":bomId},
								"success": function(data) {
									var f = dlg.fieldByName("supplier.id");
									data.suppliers.forEach(function(supplier, idx) {
										f.options.push({
											"value": supplier.id,
											"caption":supplier.name,
										});
									});
									dlg.rebuildField(f);
								},
								"error": function(data) {
								}	
							});

						});
					}
				},
				
				{
					"name":"num",
					"type":"number",
					"label":"数量"
				},
				{
					"name":"price",
					"label":"单价",
					"type":"text",
				},
				{
					"name":"supplier.id",
					"label":"供应商",
					"type":"select",
					"options":[],

				}],

	    	"url":"/purchase/addPB",
	        "success":function(data) {
	        	PdSys.refresh();
	        },
	        "error":function(data) {
	        	PdSys.alert(data.msg);
	        }
	    });

	});
	
	//下单
	$("#placePurchase").click(function(){
		var purchaseId = $('#purchaseId').val();
		if(purchaseId < 1) {
			return;
		}
		
		var dlg = new CommonDlg();
		dlg.showMsgDlg({
			"target":"msg_div",
			"caption":"下单",
			"type":"yesno",
			"msg":"确定下单?",
			"yes": function() {
				PdSys.ajax({
					"url":"/purchase/updateState",
					"data":{"id":purchaseId},
					"success": function(data) {
						dlg.hide();
				        var url = '/purchase/list?id=' + purchaseId;
				        $(location).attr('href', PdSys.url(url));
					},
					"error": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"err_div",
							"type":"ok",
							"msg":data.msg
//							"ok":function() {
//								PdSys.refresh();
//							}
						});
					}
				});
			}
		});
	});

});