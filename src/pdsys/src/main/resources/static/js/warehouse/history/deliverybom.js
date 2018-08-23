$(document).ready(function(){
	$("#export").click(function(){
		PdSys.download({
			"url": "/warehouse/history/download/deliverybom",
			"data":[
				{
					"name":"bomType",
					"value":$("#form [name='bomType']").val(),
				},
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
