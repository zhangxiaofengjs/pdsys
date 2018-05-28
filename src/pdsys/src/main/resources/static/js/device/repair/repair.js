$(document).ready(function(){
	
	function mergeTable1Cell(table, startRow, endRow, col) {  
        var tb = document.getElementById(table);  
        if (col >= tb.rows[0].cells.length-1) {  
            return;  
        }  
        if (col == 0) { endRow = tb.rows.length-1; }  
        for (var i = startRow; i < endRow; i++) {

            if (tb.rows[startRow].cells[col].innerHTML == tb.rows[i + 1].cells[col].innerHTML) {  
            	if( i!=6)
            		tb.rows[i + 1].cells[col].style.display='none';
                tb.rows[startRow].cells[col].rowSpan = (tb.rows[startRow].cells[col].rowSpan | 0) + 1;

            	if(col ==1)
            	{
            		tb.rows[i + 1].cells[7].style.display='none';
            		tb.rows[startRow].cells[7].rowSpan = tb.rows[startRow].cells[7].rowSpan + 1;
            		tb.rows[i].cells[7].innerHTML = tb.rows[startRow].cells[1].rowSpan;
            	}
                	
           
                if (i == endRow - 1 && startRow != endRow) {  
                	mergeTable1Cell(table, startRow, endRow, col + 1);  
                }  
            }
            else {
                	
            		if(tb.rows[startRow].cells[1].rowSpan==1)
            		{
            			tb.rows[i+1].cells[7].innerHTML = 1;
            		}

            		mergeTable1Cell(table, startRow, i + 0, col + 1);  
	                startRow = i + 1;  
            }
        }
    }
	
	function mergeTable2Cell(table, startRow, endRow, col) {  
        var tb = document.getElementById(table);  
        if (col >= tb.rows[0].cells.length-1) {  
            return;  
        }  
        if (col == 0) { endRow = tb.rows.length-1; }  
        for (var i = startRow; i < endRow; i++) {

            if (tb.rows[startRow].cells[0].innerHTML == tb.rows[i + 1].cells[0].innerHTML) {  

            	tb.rows[i + 1].cells[0].style.display='none';
                tb.rows[startRow].cells[0].rowSpan = (tb.rows[startRow].cells[0].rowSpan | 0) + 1;
                

                tb.rows[i + 1].cells[7].style.display='none';
                tb.rows[startRow].cells[7].rowSpan = tb.rows[startRow].cells[7].rowSpan + 1;
                tb.rows[startRow].cells[7].innerHTML = tb.rows[startRow].cells[0].rowSpan;

            }
            else
            {
            	if(tb.rows[startRow].cells[0].rowSpan==1)
        		{
            		tb.rows[startRow].cells[7].innerHTML = 1;
        		}
                startRow = i + 1;  
            }
        }
    }
	
	mergeTable1Cell('table1',1,0,0)
	mergeTable2Cell('table2',1,0,0)

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
