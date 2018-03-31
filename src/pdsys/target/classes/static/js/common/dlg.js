//取到选择的Id
function showMsgDlg(option) {
	
	//去除头上的#
	var msgDlgId= subtoption.target.substr(1) + ".msg.dlg";
	
	//bootstrap dialog
	var strHtml = 
	'<div id="'+ msgDlgId + '" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">' +
	'  <div class="modal-dialog modal-sm" role="document">'+
	'    <div class="modal-content">'+
	'		<div class="modal-header">'+
	'	    	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
	'	    	<h4 class="modal-title" id="myModalLabel">提示</h4>'+
	'	  	</div>'+
	'		<div class="modal-body">' + option.msg + '</div>' +
	'		<div class="modal-footer">'+
    '			<button type="button" class="btn btn-info btn-sm" data-dismiss="modal">确定</button>'+
	'		</div>'+
	'    </div>'+
	'  </div>'+
	'</div>';
	
	var targetDiv = $(option.target);
	targetDiv.children().remove();
	targetDiv.append(strHtml);
	$("#" + msgDlgId).modal();
}

function showFormDlg(option) {
	//去除头上的#
	var msgDlgId= subtoption.target.substr(1) + ".form.dlg";
	
	var targetDiv = $(option.target);
	targetDiv.children().remove();
	targetDiv.append(msgBoxHtml);
	$("#pdsysMsgDlg").modal();
}
