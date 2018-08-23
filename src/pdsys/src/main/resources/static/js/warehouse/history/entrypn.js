$(document).ready(function(){
	$("#export").click(function(){
		PdSys.download({
			"url": "/warehouse/history/download/entrypn",
			"data":[
				{
					"name":"pn",
					"value":$("#form [name='pn']").val(),
				},
				{
					"name":"start",
					"value":$("#form [name='start']").val(),
				},
				{
					"name":"end",
					"value":$("#form [name='end']").val(),
				}
			]
		});
	});
});
