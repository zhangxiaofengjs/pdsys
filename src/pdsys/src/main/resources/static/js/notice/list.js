$(document).ready(function(){
	$("a[id^=toggleRead]").click(function(){
		var self = $(this);
		
		var id = self.attr("nid");
		var read = self.attr("read");
		
		PdSys.ajax({
			"url":"/notice/toggleread",
			"data": {
				"id": id
			},
			"success": function() {
				self.attr("read", read==0?1:0);
				self.find("img").first().attr("src", (read == 0? PdSys.url("/icons/envelop-open.png") : PdSys.url("/icons/envelop.png")));
				
				try {
					parent.updateIndexNoticeCount();//更新父窗口的通知数量
				} catch {
					
				}
			},
			"error": function(data) {
				PdSys.alert(data.msg);
			}
		});
	});
});
