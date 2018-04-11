$(function () {
	
	//返回上一页
	$("#reback").click(function(){
		history.go(-1);
	});
	
	//添加品目
	$("#addPn").click(function(){
		var self = $(this);
        
		var unitArr = [];
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
			"options":[],
			"ajax":true,
			"url":"/pn/list/json",
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options.push({
					"value": -1,
					"caption":"请选择品目...",
				});
				data.forEach(function(pn, idx) {
					thisField.options.push({
					"value": pn.id,
					"caption": "{0} {1}".format(pn.pn, pn.name)
					});
					
					unitArr.push(pn.unit.name);
				});
			},
			"afterBuild": function() {
				var self = this;
				
				var thisElem = dlg.fieldElem(self.type, self.name);
				
				//select选择以后刷新品目
				thisElem.change(function() {
					var selIndex = thisElem[0].selectedIndex;
					if(selIndex == 0) {
						//第一项是[请选择]，无视
						return;
					}
					var val = self.options[selIndex].value;
					
					var pnField = fields[2];
					
					pnField.ajaxData = {
						"id": val
					};
					
					dlg.buildAjaxField(pnField);
					
					//单位
					var utField = fields[4];
					dlg.fieldElem("type", "pn.unit.name").val(unitArr[selIndex]);
					dlg.fieldElem("type", "pn.unit.name").attr("readonly","readonly");
				});
			}
		},
		{
			"name":"pn.pnCls.id",
			"label":"子类",
			"type":"select",
			"options":[],
			"ajax":true,
			"depend":true,//不立即执行，等订单项目的刷新
			"url":"/pn/clsList/json",
			"ajaxData":{
				"id": -1
			},
			"convertAjaxData" : function(thisField, data) {
				//将返回的值转化为Field规格数据,以供重新渲染
				//做成选择分支
				thisField.options = [];
				thisField.options.push({
					"value": -1,
					"caption":"请选择子类...",
				});
				data.forEach(function(clsPn, idx) {
					thisField.options.push({
						"value": clsPn.id,
						"caption": clsPn.name
					});
				});
			},
		},
		{
			"name":"num",
			"label":"数量",
			"type":"number"
		}
		,
		{
			"name":"pn.unit.name",
			"label":"单位",
			"type":"text",
			"readonly":"readonly"
		}];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"addOrUpPn_div",
			"caption":"新建/修改品目",
			"fields":fields,
			"url":"/pn/add/pn",
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
					"msg":"添加品目失败,请联系管理员!"});
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
			"caption":"品目删除",
			"type":"yesno",
			"msg":"确定删除已选品目?",
			"yes": function() {
				PdSys.ajax({
					"url":"/pn/delete/pn",
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
	
	//BOM信息
	$("#showBomInfo").click(function(){
		var url = PdSys.url('/pn/bomInfo/list?id=' + $('#order_id').val());
		$(location).attr('href', url);
	});
	
});