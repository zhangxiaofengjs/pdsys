function CommonDlg() {
	this.option = null;
};

CommonDlg.CACHE = {};

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

CommonDlg.prototype.id = function() {
	return this.option.target + "_dlg";
}

CommonDlg.prototype.formId = function() {
	return this.option.target + "_dlg_form";
}

CommonDlg.prototype.showFormDlg = function(opt) {
	var self = this;
	this.option = opt;
	
//	var dlgId = this.option.target + "_dlg";
	var dlgId = this.id();
	
	var strFormHtml = '<form class="form-horizontal" id="{0}" action="{1}" enctype="application/x-www-form-urlencoded">'.format(this.option.target + "_dlg_form", PdSys.url(this.option.url));
	this.option.fields.forEach(function(f, idx) {
		
		//hidden不需要单独占行
		if(f.type != "hidden") {
			strFormHtml += 
					'<div class="form-group">\
						<label for="{0}" class="col-sm-3 control-label">{1}</label>\
						<div class="col-sm-9">'.
					format(f.name,
						   f.label);
		}
		
		if(f.groupButtons) {
			strFormHtml += '<div class="input-group">';
		}
		
		if(f.ajax || f.depend) {
			//如果数据是ajax/depend（依赖后面重新做成）取得，先追加一个等待图标'
			strFormHtml += '<div id="{0}" class="form-control" style="box-shadow:0 1px 1px rgba(0, 0, 0, 0);border-width:0px;padding-left:0px;"><img src="{1}" height="24px" /><span class="text-muted">加载中...</span><span name={2} class="text-danger"></span></div>'.
				format(f.name, PdSys.url("/icons/loading.gif"), f.name + "_err");
		} else {
			strFormHtml += self.buildField(f);
		}

		if(f.groupButtons) {
			strFormHtml += '<span class="input-group-btn">\
		        				<button name="{0}" id="{0}" class="btn btn-default" type="button">{1}</button>\
		        			</span></div>'.format(f.groupButtons[0].name, f.groupButtons[0].text);
		}
		
		if(f.type != "hidden") {
			strFormHtml += '</div></div>';
		}
	});
	strFormHtml += '</form>';
	
	//保存Form提交用相关对象值
	CommonDlg.CACHE[dlgId] = {
		"dlg": self
	};
	
	var strHtml = 
		'<div id="{0}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">\
		  <div class="modal-dialog modal-md" role="document">\
		    <div class="modal-content">\
				<div class="modal-header">\
		    	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
			    	<h4 class="modal-title" id="myModalLabel">{1}</h4>\
			  	</div>\
				<div class="modal-body">{2}\
					<div id="{3}"></div>\
				</div>\
				<div class="modal-footer">\
	    			<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>\
	    			<button type="button" class="btn btn-info btn-sm" onclick="CommonDlg.ajaxSubmitForm(\'{0}\');">确定</button>\
				</div>\
		    </div>\
		  </div>\
		</div>'.format(dlgId, this.option.caption, strFormHtml,  this.option.target + "_dlg_msg");
	
	var targetDiv = $("#"+this.option.target);
	targetDiv.children().remove();
	targetDiv.append(strHtml);

	//取得数据
	if(this.option.ajax) {//主FormAjax
		self.buildAjaxFields();
	}
	this.option.fields.forEach(function(f, idx) {//各种Field的ajax
		if(f.ajax && !f.depend) { //需要ajax并且不依赖其他ajaxDepend的项目之间初始化
			self.buildAjaxField(f);
		}
		
		if(f.groupButtons) {
			$("#" + f.groupButtons[0].name).click(function(){
				if(f.groupButtons[0].hasOwnProperty("click")) {
					(f.groupButtons[0].click)(self);
				}
			});
		}
	});
	
	$("#" + this.id()).on('hidden.bs.modal', function(e) {
		delete CommonDlg.CACHE[dlgId];//关闭时销毁缓存
	});
	
	$("#" + this.id()).modal();
};

CommonDlg.prototype.hide = function() {
	$("#" + this.id()).modal('hide');
};

CommonDlg.prototype.rebuildField = function(fieldOrName) {
	var self = this;
	
	var field = fieldOrName;
	if(typeof(fieldOrName)=="string"){
		field = this.fieldByName(field);
	}
	
	if(field.ajax) {
		self.buildAjaxField(field);
		return;
	}
	var strFieldHtml = self.buildField(field);

	var fieldElm = self.findFieldElem(field);
	var fieldElmParent = fieldElm.parent();
	fieldElm.remove();
	fieldElmParent.prepend(strFieldHtml);
	
	if(field.afterBuild) {
		(field.afterBuild)('ajax');
	}
};

CommonDlg.prototype.rebuildFieldWithValue = function(fieldName, val) {
	var field = this.fieldByName(fieldName)
	field.value = val;
	this.rebuildField(field);
};

CommonDlg.prototype.fieldByName = function(name) {
	var ff = null;
	this.option.fields.some(function(f, idx) {
		if(f.name == name) {
			ff = f;
		}
	});
	
	return ff;
}

CommonDlg.prototype.buildField = function(field) {
	var strFormHtml = "";

	if(field.type == "select") {
		strFormHtml += '<select class="form-control" name="{0}" id="{0}" onblur=\"CommonDlg.onFieldBlur(\'{1}\', \'{0}\');\" {2}>'.
			format(field.name, this.id(), field.disabled || '');
		field.options.forEach(function(fo, idxo) {
			strFormHtml += '<option value ="{0}" data="{3}" {2}>{1}</option>'.
				format(fo.value || '', 
					   fo.caption || '',
				(fo.selected || field.value==fo.value)?"selected":"",
				fo.data);
		});
		strFormHtml += '</select><span name="{0}" class="text-danger"><span>'.format(field.name + "_err");
	} else if(field.type == "label") {
		strFormHtml += '<div id={0} name={0} style="padding-top:7px;">{1}</div>'.
			format(field.name, field.value || '');
	} else {
		var strAttrHtml = "";
		strAttrHtml += ' type="{0}"'.format(field.type);
		strAttrHtml += ' class="form-control {0}"'.format(field.class || '');
		strAttrHtml += ' name="{0}"'.format(field.name);
		strAttrHtml += ' id="{0}"'.format(field.name);
		strAttrHtml += (field.value ? ' value="{0}"'.format(field.value) : '');
		strAttrHtml += (field.placeholder ? ' placeholder="{0}"'.format(field.placeholder) : '');
		strAttrHtml += (field.readonly ? ' readonly' : '');
		strAttrHtml += (field.required ? ' required' : '');
		strAttrHtml += ' onblur=\"CommonDlg.onFieldBlur(\'{0}\', \'{1}\');\"'.format(this.id(), field.name);
		
		if($.inArray(field.type, ["number", "range", "date", "datetime", "datetime-local", "month", "time", "week"]) >=0 ) {
			strAttrHtml += (field.min ? ' min="{0}"'.format(field.min) : '');
			strAttrHtml += (field.max ? ' max="{0}"'.format(field.max) : '');
		}

		strFormHtml += '<input {0}><span name={1} class="text-danger"></span>'.format(strAttrHtml, field.name + "_err");
	}
	
	return strFormHtml;
};

CommonDlg.prototype.buildAjaxFields = function() {
	var opt = this.option;
	
	$.ajax({
		url:PdSys.url(opt.ajax.url),
		async:true,
		dataType:"json",
		contentType:"application/json;charset=UTF-8",
		type:"post",
		data:JSON.stringify(opt.ajax.data),
		processData: false,
        cache: false,
		success: function(response) {
			opt.ajax.convertAjaxData(response);
		},
		error: function(response) {
			var msgDiv = $("#" + opt.target + "_dlg_msg");
			msgDiv.empty();
			msgDiv.append('<img src="{0}" height="16px" /><span class="text-danger">&nbsp;取得信息失败!!</span>'.format(PdSys.url("/icons/error.png")));
		}
	});
};

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
			$(this).parent().prepend(strFieldHtml);
			$(this).remove();
			
			if(field.afterBuild) {
				(field.afterBuild)('ajax');
			}
		},
		error: function(response) {
			$(this).empty();
			$(this).append('<img src="{0}" height="16px" /><span class="text-danger">&nbsp;取得信息失败!!</span>'.format(PdSys.url("/icons/error.png")));
		}
	});
};

CommonDlg.prototype.fieldElem = function(type, name) {
	var strId = "#{0}_dlg [id='{1}']".format(
		this.option.target,
		name.safeJqueryId());
	
	return $(strId);
};

CommonDlg.prototype.findFieldElem = function(fieldOrName) {
	var field = fieldOrName;
	if(typeof(field)=="string"){
		field = this.fieldByName(field);
	}
	return this.fieldElem(field.type, field.name);
};

CommonDlg.prototype.fieldVal = function(fieldOrName) {
	var fieldElm = this.findFieldElem(fieldOrName);
	
	return fieldElm.val();
};

CommonDlg.prototype.fieldBlur = function(fieldName) {
	var self = this;
	
	var field = this.fieldByName(fieldName);
	this.doFieldValid(field);
};

CommonDlg.prototype.doFieldValid = function(field) {
	if(field.type == "label") {
		return true;//label类型无需验证
	}
	var self = this;
	var fieldElm = self.findFieldElem(field);
	var val = fieldElm.val();
	
	if(field.required != undefined && !val) {
		self.setError(field, "这个值不能为空");
		return false;
	}
	if(field.min != undefined && (val == "" || val == null || val.compareNumber(field.min) < 0)) {
		self.setError(field, "这个值必须大于等于" + field.min);
		return false;
	}
	if(field.max != undefined && (val == ""|| val == null || val.compareNumber(field.max) > 0)) {
		self.setError(field, "这个值必须小于等于" + field.max);
		return false;
	}
	
	self.setError(field, "");
	return true;
} 
	
CommonDlg.prototype.setError = function(fieldOrName, msg) {
	var self = this;

	var field = fieldOrName;
	if(typeof(fieldOrName) == "string") {
		field = self.fieldByName(fieldOrName);
	}
	var strId = "#{0} span[name='{1}']".format(
			this.id(),
			(field.name + "_err").safeJqueryId());
	$(strId).html(msg);
}

CommonDlg.prototype.doValid = function() {
	var self = this;
	var isOK = true;
	
	self.option.fields.forEach(function(field, idx) {
		if(!self.doFieldValid(field)) {
			isOK = false;
		}
	});
	
	if(isOK) {
		if(self.option.hasOwnProperty("valid")) {
			isOK = (self.option.valid)();
		}
	}
	return isOK;
};

//formJson = {
//	'num': 11,
//	'bom':{
//		'id':22
//	}
//};
//将 a.b.c=1的名字转化为{'a':{'b':{'c':1}}}取得b对象,
//将 a.b[0].c=1的名字转化为{'a':{'b':[{'c':1}]}}取得b对象,
function getJsonObj(o, strName) {
	var names = strName.split(".");
	
	var ret = o;
	for(var i = 0; i < names.length -1; i++) {
		var name = names[i];
		
		var isArray = false;
		var arrIndex = 0;
		if(name.substring(name.length-1, name.length) == "]") {
			//如果是数组
			isArray = true;
			var is = name.indexOf("[");
			var ie = name.indexOf("]");
			arrIndex = parseInt(name.substring(is+1, ie));
			name = name.substring(0, is);
		}
			
		if (ret[name] == undefined) {
			if(isArray) {
				ret[name] = [];
				
				var idx = arrIndex;
				while(idx>-1) {
					ret[name].push({});//放0到指定位置空对象
					idx--;
				}
			} else {
				ret[name] = {};
			}
		}
		
		//取元素
		ret = ret[name];
		
		if(isArray) {
			while(ret.length <= arrIndex) {
				ret.push({});//放0到指定位置空对象
			}
			
			ret = ret[arrIndex];
		}
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
   });
   return o;
};

//静态方法
CommonDlg.onFieldBlur = function(dlgId, fieldName) {
	var args = CommonDlg.CACHE[dlgId];
	if(args == null) {
		alert("sys:The dlg is not found!!");
		return;
	}
	
	var dlg = args.dlg;
	dlg.fieldBlur(fieldName);
};

//静态方法
CommonDlg.ajaxSubmitForm = function(dlgId) {
	var args = CommonDlg.CACHE[dlgId];
	if(args == null) {
		alert("sys:The dlg is not found!!");
		return;
	}
	
	var dlg = args.dlg;
	if(!dlg.doValid()) {
		return;
	}
	var option = dlg.option;
	var formJson = CommonDlg.encodeFormJson("#"+dlg.formId());
	//	var ff = new FormData();//FormData不好用，暂用json代替
	
	var args = {
    	url : PdSys.url(option.url),
        type : 'post',
        dataType : 'json',//接受服务端数据类型
        contentType:"application/json;charset=UTF-8",
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
        	PdSys.sysError();
        }
     };
    
	//提交
	$.ajax(args);
}
