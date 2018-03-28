function alarmFormatter(cellvalue, options, rowdata) {
    return ' <img src="'+cellvalue+ '" id="img' + rowdata.Id + '" style="width:80px; height:80px;"/>';
}
var imgUrl = "";
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'cont/houserelevantpersonnel/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 30, key: true },
			{ label: '英文姓名', name: 'name', index: 'name', width: 60 }, 			
			{ label: '中文姓名', name: 'nameCn', index: 'name_cn', width: 60 }, 			
			{ label: '联系方式', name: 'telNumber', index: 'tel_number', width: 80 }, 			
			{ label: '地址', name: 'location', index: 'location', width: 100 }, 			
			{ label: '照片', name: 'photo', index: 'photo', width: 80, align: "center", sortable: false, editable: false, formatter: alarmFormatter}, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 60 }, 			
			{ label: '修改时间', name: 'updateTime', index: 'update_time', width: 60 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    
    new AjaxUpload('#upload', {
        action: baseURL + 'sys/oss/upload?token=' + token,
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
                imgUrl = r.url;
                $("#imgSrc").remove();
                $("#nbsp").remove();
                $("#img").prepend("<img src='"+ imgUrl +"' alt='照片' id='imgSrc' style='width:120px; height:120px;'/><p id='nbsp'>&nbsp;&nbsp;&nbsp;&nbsp;</p>");
                alert('照片上传成功！');
            }else{
                alert(r.msg);
            }
        }
    });
});
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		houseRelevantPersonnel: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.houseRelevantPersonnel = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			if(imgUrl){
				vm.houseRelevantPersonnel.photo = imgUrl;
			}
			var url = vm.houseRelevantPersonnel.id == null ? "cont/houserelevantpersonnel/save" : "cont/houserelevantpersonnel/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.houseRelevantPersonnel),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "cont/houserelevantpersonnel/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "cont/houserelevantpersonnel/info/"+id, function(r){
                vm.houseRelevantPersonnel = r.houseRelevantPersonnel;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});