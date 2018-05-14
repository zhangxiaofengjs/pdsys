$(function () {
	
//	$('#search').click(function(){
//		var phurchaseId = $("#purchase_id").value();
//		
//		var bomNo = $('#bomNo').val();
//        var storeId = document.getElementById('store'); 
//        var rowsLength = storeId.rows.length;
//        var searchCol = 1;
//  
//        for(var i=2;i<rowsLength;i++){
//            var searchText = storeId.rows[i].cells[searchCol].innerHTML; 
//  
//            if(searchText.match(bomNo)){
//                storeId.rows[i].style.display='';
//            }else{  
//                storeId.rows[i].style.display='none';
//            }  
//        }
//	});
	
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
			"target":"msg_div",
			"caption":"删除采购单详细",
			"type":"yesno",
			"msg":"确定删除已选采购单详细?",
			"yes": function() {
				PdSys.ajax({
					"url":"/purchase/delete/purchaseDetail",
					"data":ajaxDatas,
					"success": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":data.msg,
							"ok":function(){
								PdSys.refresh();
							}});
					},
					"error": function(data) {
						dlg.hide();
						var msgDlg = new CommonDlg();
						msgDlg.showMsgDlg({
							"target":"msg_div",
							"type":"ok",
							"msg":data.msg});
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
	
	//下单
	$("#placePurchase").click(function(){
		var purchaseId = $('#purchase_id').val();
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
						if(data.success) {
				        	var url = '/purchase/list?id=' + purchaseId;
				        	$(location).attr('href', PdSys.url(url));
						}
						else
						{
							var msgDlg = new CommonDlg();
							msgDlg.showMsgDlg({
								"target":"msg_div",
								"type":"ok",
								"msg":data.msg,
								"ok":function() {
									PdSys.refresh();
								}
							});
							
						}
					},
					"error": function(data) {
						dlg.hide();
						PdSys.alert(data.msg);
					}
				});
			}
		});
	});

});