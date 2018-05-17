$(document).ready(function(){

	//初期化
	$("#uploadfile").fileinput({
	    language: 'zh',
	    uploadUrl: PdSys.url("/upload/image"),
	    allowedFileExtensions: ['jpg', 'png', 'gif','jpeg'],
	    uploadAsync: true,
	    showUpload: false,
	    showRemove : true,
	    showPreview : true,
	    showCaption: false,
	    browseClass: "btn btn-primary",
	    dropZoneEnabled: false,
	    maxFileCount: 5,
	    enctype: 'multipart/form-data',
	    validateInitialCount:true
	});
	
	$("#uploadfile").on("fileuploaded", function(event, data, previewId, index) {
        if(data.response){
        	PdSys.alert('haha');
        }
    });
	
	$('#uploadfile').on('fileerror', function(event, data, msg) {
		PdSys.alert('gaga');
	});
	
	//提交按钮
	$("#btn_add").click(function (){
		$('#uploadfile').fileinput('upload');
	});

});