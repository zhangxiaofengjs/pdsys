$(function () {
	
	//表格动态加载
	function tableLoad()
	{
		var order= {
			"id":$('#order_id').val()
		};
		PdSys.ajax({
			"url":"/orderPn/list/json",
			"data":order,
			"success": function(data) {
				var bodyHtml = "";
				data.orderPns.forEach(function(orderPn, idx) {
					var rowspanNum = 0;
					orderPn.pn.pnClsRels.forEach(function(pnClsRel, idx3) {
						var PnClsModel = pnClsRel.pnCls;
						var yuanArr = [];
						var baoArr = [];
						var bomNum = 0;
						PnClsModel.pnClsBOMRels.forEach(function(pnClsBOMRel, idx2) {
							var bom = pnClsBOMRel.bom
							if( bom.type ==0 ) {
								yuanArr.push("<td>{0}</td><td>{1}</td><td>{2}</td>".format(
										bom.pn +"/"+bom.name,
										pnClsBOMRel.useNum,
										bom.unit.name));
							}
							if( bom.type ==1 ) {
								baoArr.push("<td>{0}</td><td>{1}</td><td>{2}</td>".format(
										bom.pn +"/"+bom.name,
										pnClsBOMRel.useNum,
										bom.unit.name));
							}
						});
						
						if(yuanArr.length<baoArr.length){
							bomNum = baoArr.length;
							yuanArr.push("<td></td><td></td><td></td>");
						}
						else{
							bomNum = yuanArr.length;
							if(yuanArr.length>baoArr.length){
								baoArr.push("<td></td><td></td><td></td>");
							}
						}
						
						if( bomNum==0 )
						{
							rowspanNum+=1;
						}
						else
						{
							rowspanNum+=bomNum;
						}
						
					});
						
					orderPn.pn.pnClsRels.forEach(function(pnClsRel, idx3) {
						var PnClsModel = pnClsRel.pnCls;
						var yuanArr = [];
						var baoArr = [];
						var bomNum = 0;
						PnClsModel.pnClsBOMRels.forEach(function(pnClsBOMRel, idx2) {
							var bom = pnClsBOMRel.bom
							if( bom.type ==0 ) {
								yuanArr.push("<td>{0}</td><td>{1}</td><td>{2}</td>".format(
										bom.pn +"/"+bom.name,
										pnClsBOMRel.useNum,
										bom.unit.name));
							}
							if( bom.type ==1 ) {
								baoArr.push("<td>{0}</td><td>{1}</td><td>{2}</td>".format(
										bom.pn +"/"+bom.name,
										pnClsBOMRel.useNum,
										bom.unit.name));
							}
						});
						
						if(yuanArr.length<baoArr.length){
							bomNum = baoArr.length;
							yuanArr.push("<td></td><td></td><td></td>");
						}
						else{
							bomNum = yuanArr.length;
							if(yuanArr.length>baoArr.length){
								baoArr.push("<td></td><td></td><td></td>");
							}
						}
						
						//品目信息
						if( idx3 == 0 )
						{
							bodyHtml += "<tr><td rowspan='{0}'><input type='checkbox' name='select' rowid='{1}'/></td><td rowspan='{0}'>{2}</td><td rowspan='{0}'>{3}</td>".format(
									rowspanNum, 
									orderPn.id, 
									orderPn.pn.pn,
									orderPn.pn.name);
						}
						
						bodyHtml += "<td>{0}</td>".format(PnClsModel.name);
						
						if( idx3 == 0 )
						{
							bodyHtml += "<td rowspan='{0}'>{1}</td><td rowspan='{0}'>{2}</td>".format(
									rowspanNum, 
									orderPn.num,
									orderPn.pn.unit.name);
						}
						
						//原材、包材
						for (i=0;i<bomNum;i++)
						{
							//原材
							bodyHtml += yuanArr[i];
							//包材
							bodyHtml += baoArr[i];
							
						}
						//bom信息没有的情况下
						if(bomNum==0)
						{
							//原材
							bodyHtml += "<td></td><td></td><td></td>";
							//包材
							bodyHtml += "<td></td><td></td><td></td>";
						}
						
						bodyHtml += "</tr>";

					});
					
					if(rowspanNum==0)
					{
						bodyHtml += "<tr><td><input type='checkbox' name='select' rowid='{0}'/></td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td>".format(
								orderPn.id, 
								orderPn.pn.pn,
								orderPn.pn.name,
								'',
								orderPn.num,
								orderPn.pn.unit.name);
						
						//原材
						bodyHtml += "<td></td><td></td><td></td>";
						//包材
						bodyHtml += "<td></td><td></td><td></td>";
						bodyHtml += "</tr>";
					}

				});

				var body = $('#bomInfo');
				body.children().remove();
				body.append(bodyHtml);
			},
			"error": function(data) {
				PdSys.alert("error");
			}
		});
	}
	tableLoad();
	
	//返回上一页
	$("#reback").click(function(){
		history.go(-1);
	});
	
	//添加品目
	$("#addPn").click(function(){
		var self = $(this);
		var caption = "添加品目";
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
					var pnField = fields[2];
					if(selIndex == 0) {
						//第一项是[请选择]，其它控件清空处理
						var clsElem = dlg.fieldElem("select", "pn.pnCls.id");
						clsElem[0].selectedIndex = 0;
						dlg.fieldElem("type", "pn.unit.name").val("");
						dlg.fieldElem("number", "num").val("");
						return;
					}

					var val = self.options[selIndex].value;
					pnField.ajaxData = {
						"id": val
					};
					
					dlg.buildAjaxField(pnField);
					
					//单位
					var utField = fields[4];
					dlg.fieldElem("type", "pn.unit.name").val(unitArr[selIndex-1]);
					utField.readonly = false;
				});
			}
		},
		{
			"name":"pn.pnCls.id",
			"label":"子类",
			"type":"select",
			"depend":true,//不立即执行，等订单项目的刷新
			"options":[],
			"ajax":true,
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
			"readonly":true
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
		var numArr = [];
		var unitArr = [];
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
					"name":"pn.id",
					"label":"品目名",
					"disabled":"disabled",
					"type":"select",
					"options":[],
					"ajax":true,
					"url":"/orderPn/showPnInfo?id="+selIds[0],
					"convertAjaxData" : function(field, data) {
						data.data.forEach(function(op, idx) {
							field.options.push({
							"value": op.pn.id,
							"caption": "{0} {1}".format(op.pn.pn, op.pn.name)

						    });
							
							numArr.push(op.num);
							unitArr.push(op.pn.unit.name);
					    });
						dlg.fieldElem("type", "num").val(numArr[0]);
						dlg.fieldElem("type", "unitName").val(unitArr[0]);
						dlg.fieldElem("type", "unitName").attr("readonly","readonly");
					}
				},
				{
					"name":"pn.pnCls.id",
					"label":"子类",
					"disabled":"disabled",
					"type":"select",
					"options":[],
					"ajax":true,
					"url":"/orderPn/showClsInfo?id="+selIds[0],
					"convertAjaxData" : function(field, data) {
						data.data.forEach(function(cls, idx) {
							field.options.push({
							"value": cls.id,
							"caption": cls.name

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
					"name":"unitName",
					"label":"单位",
					"type":"text",
					"readonly":"readonly"
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

});