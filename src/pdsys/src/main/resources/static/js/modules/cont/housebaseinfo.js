$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'cont/housebaseinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'Id', name: 'houseId', index: 'house_id', width: 50, key: true },
			{ label: '房屋标题', name: 'houseTitle', index: 'house_title', width: 240 }, 			
			{ label: '类型', name: 'type', index: 'type', width: 80 }, 			
			{ label: '地理位置', name: 'location', index: 'location', width: 180 }, 			
			{ label: '建筑面积', name: 'areaConstruction', index: 'area_construction', width: 80 }, 			
			{ label: '使用面积', name: 'areaUsing', index: 'area_using', width: 80 }, 			
			{ label: '面积单位', name: 'areaUnit', index: 'area_unit', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }			
        ],
		viewrecords: true,
        height: 685,
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		houseBaseInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.houseBaseInfo = {};
			vm.getHouseType();
		},
		update: function (event) {
			var houseId = getSelectedRow();
			if(houseId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getHouseType();
            vm.getInfo(houseId);
		},
		saveOrUpdate: function (event) {
			var url = vm.houseBaseInfo.houseId == null ? "cont/housebaseinfo/save" : "cont/housebaseinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.houseBaseInfo),
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
			var houseIds = getSelectedRows();
			if(houseIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "cont/housebaseinfo/delete",
                    contentType: "application/json",
				    data: JSON.stringify(houseIds),
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
		getInfo: function(houseId){
			$.get(baseURL + "cont/housebaseinfo/info/"+houseId, function(r){
                vm.houseBaseInfo = r.houseBaseInfo;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
		getHouseType: function (event) {
			$.ajax({
				type: "GET",
			    url: baseURL + "/data/housetype/all",
                contentType: "application/json",
			    success: function(r){
			    	if(r.code === 0){
			    		$("#houseTypeSelect option").remove(); 
						jQuery.each(r.houseTypeList, function(i, item) {
							 $("#houseTypeSelect").append("<option value='" + item.houseTypeName + "'>" + item.houseTypeName + "(" + item.houseTypeNameCn + ")</option>");
						});
					}else{
						alert(r.msg);
					}
				}
			});
		}
	}
});