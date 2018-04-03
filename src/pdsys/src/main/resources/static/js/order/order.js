$(function () {
	$("#addOrder").click(function(){
		alert('haha');
	});
	
	$("#delOrder").click(function(){
		var orderIds = getSelectedRowId();
		if(orderIds == null){
			return ;
		}
		confirm('确定要删除选中的订单？', function(){
			$.ajax({
				type: "POST",
			    url: "order/delete",
                contentType: "application/json",
                data: JSON.stringify(orderIds),
			    success: function(r){
					if(r.success){
						alert('操作成功', function(){
                            alert(r.msg);
						});
					}else{
						alert(r.msg);
					}
				}
			});
		});
	});

});