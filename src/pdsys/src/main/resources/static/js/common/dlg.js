function CommonDlg() {
	this.option = null;
};

CommonDlg.ajaxSubmitOption = null;

//var dlg = new CommonDlg()
//dlg.showMsgDlg({
//	"target":"msg_div",
//	"type":"yesno",
//	"yes": function() {alert('yes');},
//	"ok": function() {alert('ok');},
//	"msg":"请选择要添加到出库的单的对象。"});
CommonDlg.prototype.showMsgDlg = function(opt) {
	this.option = opt;
	
	var dlgId = this.option.target + "_dlg";
	
	var strButtonHtml = "";
	if(this.option.type == "yesno") {
		strButtonHtml += '<button type="button" class="btn btn-info btn-sm" id="{0}" name="{0}">是</button>\
						  <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" id="{1}" name="{1}">否</button>'.
						  format(this.option.target + "_btn_yes",
								  this.option.target + "_btn_no");
	} else {
		strButtonHtml += '<button type="button" class="btn btn-info btn-sm" data-dismiss="modal" id="{0}" name="{0}">确定</button>'.
						  format(this.option.target + "_btn_ok");
	}
	//bootstrap dialog
	var strHtml = 
	'<div id="{0}" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">\
	  <div class="modal-dialog modal-sm" role="document">\
	    <div class="modal-content">\
			<div class="modal-header">\
		    	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
		    	<h4 class="modal-title" id="myModalLabel">提示</h4>\
		  	</div>\
			<div class="modal-body">{1}</div>\
			<div class="modal-footer">{2}</div>\
	    </div>\
	  </div>\
	</div>'.format(dlgId, this.option.msg, strButtonHtml);

	var targetDiv = $("#"+ this.option.target);
	targetDiv.children().remove();
	targetDiv.append(strHtml);
	
	//按钮的click回调函数
	$("#" + dlgId + " button").click(
		function() {
			if(this.name == opt.target + "_btn_yes" && opt.yes) {
				(opt.yes)();
			} else if(this.name == opt.target + "_btn_no" && opt.no) {
				(opt.no)();
			} else if(this.name == opt.target + "_btn_ok" && opt.ok) {
				(opt.ok)();
			}
		}
	);
	
	$("#" + dlgId).modal();
};

CommonDlg.prototype.showFormDlg = function(opt) {
	var self = this;
	this.option = opt;
	
	var dlgId = this.option.target + "_dlg";
	
	var strFormHtml = '<form class="form-horizontal" id="{0}" action="{1}" enctype="application/x-www-form-urlencoded">'.format(this.option.target + "_dlg_form", PdSys.url(this.option.url));
	this.option.fields.forEach(function(f, idx) {
		
		//hidden不需要单独占行
		if(f.type != "hidden") {
			strFormHtml += 
					'<div class="form-group">\
						<label for="{0}" class="col-sm-4 control-label">{1}</label>\
						<div class="col-sm-8">'.
					format(f.name,
						   f.label);
		}
		
		if(f.ajax) {
			//如果数据是ajax取得，先追加一个等待图标
			strFormHtml += '<div id="{0}" class="form-control" style="box-shadow:0 1px 1px rgba(0, 0, 0, 0);border-width:0px;padding-left:0px;"><img src="{1}" height="24px" /><span class="text-muted">加载中...</span></div>'.
				format(f.name, PdSys.url("/icons/loading.gif"));
		} else {
			strFormHtml += self.buildField(f);
		}

		if(f.type != "hidden") {
			strFormHtml += '</div></div>';
		}
	});
	strFormHtml += '</form>';
	
	CommonDlg.ajaxSubmitOption = this.option;
	
	var strHtml = 
		'<div id="{0}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">\
		  <div class="modal-dialog modal-sm" role="document">\
		    <div class="modal-content">\
				<div class="modal-header">\
		    	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
			    	<h4 class="modal-title" id="myModalLabel">{1}</h4>\
			  	</div>\
				<div class="modal-body">{2}\
				</div>\
				<div class="modal-footer">\
	    			<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>\
	    			<button type="button" class="btn btn-info btn-sm" onclick="CommonDlg.ajaxSubmitForm();">确定</button>\
				</div>\
		    </div>\
		  </div>\
		</div>'.format(dlgId, this.option.caption, strFormHtml);
	
	var targetDiv = $("#"+this.option.target);
	targetDiv.children().remove();
	targetDiv.append(strHtml);

	//取得数据
	this.option.fields.forEach(function(f, idx) {
		if(f.ajax && !f.depend) { //需要ajax并且不依赖其他ajaxDepend的项目之间初始化
			self.buildAjaxField(f);
		}
	});
	
	$("#" + dlgId).modal();
}

CommonDlg.prototype.hide = function() {
	var dlgId = this.option.target + "_dlg";
	$("#" + dlgId).modal('hide');
}

CommonDlg.prototype.buildField = function(field) {
	var strFormHtml = "";

	if(field.type == "select") {
		strFormHtml += '<select class="form-control" name="{0}" id="{0}">'.format(field.name);
		field.options.forEach(function(fo, idxo) {
			strFormHtml += '<option value ="{0}" {2}>{1}</option>'.
				format(fo.value || '', 
					   fo.caption || '',
				fo.selected?"selected":"");
		});
		strFormHtml += '</select>';
	} else {
		strFormHtml += '<input type="{0}" class="form-control" name="{1}" id="{1}" value="{2}" placeholder="{3}">'.
			format(field.type,
				   field.name,
				   field.value || '',
				   field.placeholder || "");
	}

	return strFormHtml;
}

//ajax取得field的信息
CommonDlg.prototype.buildAjaxField = function(field) {
	var self = this;

	$.ajax({
		url:PdSys.url(field.url),
		async:true,
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		type:"post",
		data:JSON.stringify(field.ajaxData),
		processData: false,
        cache: false,
		context:$(("#"+field.name).safeJqueryId())[0],//设置success/error的回调上下文this([0]表示转化为dom元素咯)
		success: function(response) {
			field.convertAjaxData(field, response);
			var strFieldHtml = self.buildField(field);
			$(this).parent().append(strFieldHtml);
			$(this).remove();
			
			if(field.afterBuild) {
				(field.afterBuild)();
			}
		},
		error: function(response) {
			$(this).empty();
			$(this).append('<img src="{0}" height="16px" /><span class="text-danger">&nbsp;取得信息失败!!</span>'.format(PdSys.url("/icons/error.png")));
		}
	});
};

CommonDlg.prototype.fieldElem = function(type, name) {
	var strId = "#{0}_dlg {1}[name='{2}']".format(
		this.option.target,
		type != 'select' ? 'input' : 'select',
		name.safeJqueryId());
	
	return $(strId);
};

//formJson = {
//	'num': 11,
//	'bom':{
//		'id':22
//	}
//};
//将 a.b.c=1的名字转化为{'a':{'b':{'c':1}}}取得b对象,
function getJsonObj(o, strName) {
	var names = strName.split(".");
	
	var ret = o;
	for(var i = 0; i < names.length -1; i++) {
		var name = names[i];
		if (ret[name] == undefined) {
			ret[name] = {};
		}
		ret = ret[name];
	}
	
	return ret;
}

CommonDlg.encodeFormJson = function(frm) {
   var o = {};
   var a = $(frm).serializeArray();

   $.each(a, function() {
	   var obj = getJsonObj(o, this.name);
	   var names = this.name.split(".");
	   var name = names.pop();
	   
	   obj[name] = this.value || '';
	   
//      if (o[this.name] !== undefined) {
//         if (!o[this.name].push) {
//            o[this.name] = [ o[this.name] ];
//         }
//         o[this.name].push(this.value || '');
//      } else {
//         o[this.name] = this.value || '';
//      }
   });
   return o;
};

//静态方法
CommonDlg.ajaxSubmitForm = function() {
	var option = CommonDlg.ajaxSubmitOption;
	var formJson = CommonDlg.encodeFormJson("#"+option.target + "_dlg_form");
	//	var ff = new FormData();//FormData不好用，暂用json代替
	
	var args = {
    	url : PdSys.url(option.url),
        type : 'post',
        dataType : 'json',//接受服务端数据类型
        contentType:"application/json",
        processData: false,
        cache: false,
        data: JSON.stringify(formJson),
        success : function(data) {
        	if(data.success)
        	{
        		option.success(data);
        	}
        	else
        	{
        		option.error(data);
        	}
        },
        error: function(data) {
        	option.error(data);
        }
     };
    
	//提交
	$.ajax(args);
}
