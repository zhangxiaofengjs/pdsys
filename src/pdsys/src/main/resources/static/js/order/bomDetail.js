$(document).ready(function(){
	
	//返回上一页
	$("#reback").click(function(){
		history.go(-1);
	});

	//BOM详细
	$("a[name='showBomDetail']").click(function(){
		var self = $(this);
		var supplier = {
			"id":self.attr('supplierId')
		};
		var option = {
			"url":"/supplier/detail",
			"data":supplier,
			"success":function(data) {
				
        		var dlg = new CommonDlg();
    			dlg.showMsgDlg({
    				"target":"msg_div",
    				"type":"ok",
    				"msg":'<b>供应商信息</b><br/>\
							供应商名：&nbsp&nbsp<span>{0}</span><br/>\
							地址：&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<span>{1}</span><br/>\
							联系方式：&nbsp&nbsp<span>{2}</span><br/>'.format(data.sup.name,data.sup.address,data.sup.phone)});
			},
			"error:":function(data) {
				
			}
		};
		PdSys.ajax(option);
	});
	
	//排序
	function CellSort(tableId)
	{
		var table =document.getElementById(tableId);
		var rows = table.rows.length;
		//除去表头和列名所在行
		for (i=3;i<rows-1;i++)
		{
			k=i;
			for (j=i+1;j<rows;j++)
			{
			 
				if (table.rows[k].cells[0].innerHTML>table.rows[j].cells[0].innerHTML)
					k=j;
			}
			if (k>i)
			{
				tmp=table.rows[i].cells[0].innerHTML;
				table.rows[i].cells[0].innerHTML=table.rows[k].cells[0].innerHTML;
				table.rows[k].cells[0].innerHTML=tmp;
			}
		}
	}
	
	//调用排序
	CellSort('table1');
	
	//合并单元格
	function MergeCell(tableId, startRow, isEnd) {
		var startNum = 0;
		var totalNum = 0;
		var totalSum = 0;
        var tb = document.getElementById(tableId);
        if (isEnd) {
            return;
        }
        var endRow = tb.rows.length - 1;
        for (var i = startRow; i < endRow; i++) {
        	startNum = parseInt(tb.rows[startRow].cells[8].innerHTML);
            //程序是自左向右合并
            if (tb.rows[startRow].cells[0].innerHTML == tb.rows[i + 1].cells[0].innerHTML) {
                //如果相同则删除下一行的第0列单元格
                tb.rows[i + 1].cells[0].style.display='none';
                tb.rows[i + 1].cells[9].style.display='none';
                //更新rowSpan属性
                tb.rows[startRow].cells[0].rowSpan = (tb.rows[startRow].cells[0].rowSpan | 0) + 1;
                tb.rows[startRow].cells[9].rowSpan = tb.rows[startRow].cells[9].rowSpan + 1;
                totalNum += parseInt(tb.rows[i + 1].cells[8].innerHTML);

            } else {
            	tb.rows[startRow].cells[9].innerHTML = totalNum + startNum;
            	totalSum+=totalNum + startNum;
                //增加起始行  
                startRow = i + 1;
                totalNum = 0;
            }
        }
        
        //合计
        tb.rows[endRow].cells[1].innerHTML = totalSum;
    }
	
	//表头和列所在行除外
    MergeCell('table1', 3, false);
});