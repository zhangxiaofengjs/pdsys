function CommonDlg() {
	this.option = null;
};

CommonDlg.ajaxSubmitOption = null;

CommonDlg.prototype.showMsgDlg = function(opt) {
	this.option = opt;
	
	var dlgId = this.option.target + "_dlg";
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
			<div class="modal-footer">\
    			<button type="button" class="btn btn-info btn-sm" data-dismiss="modal">确定</button>\
			</div>\
	    </div>\
	  </div>\
	</div>'.format(dlgId, this.option.msg);

	var targetDiv = $("#"+ this.option.target);
	targetDiv.children().remove();
	targetDiv.append(strHtml);
	$("#" + dlgId).modal();
};

CommonDlg.prototype.showFormDlg = function(opt) {
	this.option = opt;
	
	var dlgId = this.option.target + "_dlg";
	
	var strFormHtml = '<form class="form-horizontal" id="{0}" action={1}>'.format(this.option.target + "_dlg_form", this.option.url);
	this.option.fields.forEach(function(f, idx) {
		strFormHtml += 
				'<div class="form-group">\
					<label for="{0}" class="col-sm-3 control-label">{1}</label>\
					<div class="col-sm-9">'.
				format(f.name,
					   f.label);
		if(f.ajax) {
			//如果数据是ajax取得，先追加一个等待图标
			strFormHtml += '<div id={0} class="form-control" style="box-shadow:0 1px 1px rgba(0, 0, 0, 0);border-width:0px;padding-left:0px;"><img src="icons/loading.gif" width=48px/>加载中...</div>'.format(f.name + "_ajax_field");
		} else {
			strFormHtml += buildField(f);
		}

		strFormHtml += '</div></div>';
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
	$("#" + dlgId).modal();

	//取得数据
	this.option.fields.forEach(function(f, idx) {
		if(f.ajax) {
			this.getAjaxField(f);
		}
	});	
}

CommonDlg.prototype.buildField = function(field) {
	var strFormHtml = "";

	if(field.type == "select") {
		strFormHtml += '<select>';
		field.options.forEach(function(fo, idxo) {
			strFormHtml += '<option value ="{0}" {2}>{1}</option>'.
				format(fo.value, fo.text,
				fo.selected?"selected":"");
		});
		strFormHtml += '</select>';
	} else {
		strFormHtml += '<input type="{0}" class="form-control" name="{1}" placeholder="{2}">'.
			format(field.type,
				   field.name,
				   field.placeholder?field.placeholder:"");
	}

	return strFormHtml;
}

//ajax取得field的信息
CommonDlg.prototype.getAjaxField = function(field) {
	var self = $(this);

	$.ajax({
		url:field.url,
		async:true,
		dataType:"json",
		type:"post",
		data:field.ajaxData,
		context:$("#"+field.name + "_ajax_field")[0],//设置success/error的回调上下文this([0]表示转化为dom元素咯)
		success: function(response) {
			if(response.success) {
				$(this).parent().append(self.buildField(response.data));
			} else {
				$(this).parent().append("取得信息失败!!");
			}
			$(this).remove();
		},
		error: function(response) {
			$(this).parent().append("取得信息失败!!");
			$(this).remove();
		}
	});
};

//静态方法
CommonDlg.ajaxSubmitForm = function() {
	var option = CommonDlg.ajaxSubmitOption;
	
	var args = {
    	url : option.url,
        type : 'POST',
        dataType : 'json',
        headers : {"ClientCallMode" : "ajax"},
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
    
	 $("#"+option.target + "_dlg_form").ajaxSubmit(args);
}
