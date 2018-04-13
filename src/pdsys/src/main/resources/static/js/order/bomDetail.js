$(document).ready(function(){
	
	//返回上一页
	$("a[name='showBomDetail']").click(function(){
		//var url = PdSys.url('/pn/bomInfo/list?id=' + $('#supplierId').val());
		//$(location).attr('href', url);
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
	function MergeCell(tableId, startRow, endRow, col) {  
        var tb = document.getElementById(tableId);  
        if (col >= tb.rows[0].cells.length) {  
            return;  
        }  
        //当检查第0列时检查所有行  
        if (col == 0 || endRow == 0) {  
            endRow = tb.rows.length - 1;  
        }  
        for (var i = startRow; i < endRow; i++) {  
            //程序是自左向右合并  
            if (tb.rows[startRow].cells[col].innerHTML == tb.rows[i + 1].cells[col].innerHTML) {  
                //如果相同则删除下一行的第0列单元格  
                tb.rows[i + 1].cells[col].style.display='none';  
                //更新rowSpan属性  
                tb.rows[startRow].cells[col].rowSpan = (tb.rows[startRow].cells[col].rowSpan | 0) + 1;  
                tb.rows[startRow].cells[9].rowSpan = tb.rows[startRow].cells[9].rowSpan + 1; 
                //当循环到终止行前一行并且起始行和终止行不相同时递归 
                if (i == endRow - 1 && startRow != endRow) {  
                    MergeCell(tableId, startRow, endRow, col + 1);  
                }  
            } else {  
                //起始行，终止行不变，检查下一列  
                MergeCell(tableId, startRow, i, col + 1);  
                //增加起始行  
                startRow = i + 1;  
            }  
        }  
    }  
    MergeCell('table1', 0, 0, 0);
});