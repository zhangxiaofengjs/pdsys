$(document).ready(function(){
	
	//排序
	function CellSort(tableId)
	{
		var table =document.getElementById(tableId);
		var rows = table.rows.length;
		//除去表头和列名所在行
		for (i=1;i<rows-1;i++)
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
	CellSort('table2');

	function mergeCell(table, startRow, endRow, col) {  
        var tb = document.getElementById(table);  
        if (col >= tb.rows[0].cells.length) {  
            return;  
        }  
        if (col == 0) { endRow = tb.rows.length-1; }  
        for (var i = startRow; i < endRow; i++) {

            if (tb.rows[startRow].cells[col].innerHTML == tb.rows[i + 1].cells[col].innerHTML) {  
            	tb.rows[i + 1].cells[col].style.display='none';
            	tb.rows[i + 1].cells[7].style.display='none';
                tb.rows[startRow].cells[col].rowSpan = (tb.rows[startRow].cells[col].rowSpan | 0) + 1;
                
                if( table=="table1" )
                {
                	if(col ==1)
                	{
                		tb.rows[startRow].cells[7].rowSpan = tb.rows[startRow].cells[7].rowSpan + 1;
                		tb.rows[i].cells[7].innerHTML = tb.rows[startRow].cells[7].rowSpan;
                	}
                	
                }
                else if( table=="table2" )
                {
                	if(col==0)
                	{
                		tb.rows[startRow].cells[7].rowSpan = tb.rows[startRow].cells[7].rowSpan + 1;
                		tb.rows[i].cells[7].innerHTML = tb.rows[startRow].cells[7].rowSpan;
                	}
                }
                if (i == endRow - 1 && startRow != endRow) {  
                	mergeCell(table, startRow, endRow, col + 1);  
                }  
            } else {
            	mergeCell(table, startRow, i + 0, col + 1);  
                startRow = i + 1;  
            }
        }
    }
	
	mergeCell('table1',1,0,0)
	mergeCell('table2',1,0,0)
	
	//radio相关
	$("input[name='mode']").each(function(){
	    if($(this).attr("checked"))
	    {
	        if(this.value=='0')
	        {
	        	document.getElementById('table2').style.display='none';
	        	document.getElementById('table1').style.display='';
	        }
	        else if(this.value=='1')
	        {
	        	document.getElementById('table1').style.display='none';
	        	document.getElementById('table2').style.display='';
	        }
	    }
	})
	
	$("input[name='mode']").click(function() {
        $("#abc").html(this.value);
        if(this.value=='0')
        {
        	document.getElementById('table2').style.display='none';
        	document.getElementById('table1').style.display='';
        }
        else if(this.value=='1')
        {
        	document.getElementById('table1').style.display='none';
        	document.getElementById('table2').style.display='';
        }
    });
	
	
});
