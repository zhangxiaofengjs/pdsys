$(document).ready(function(){
  $("a[refURL]").click(function(){
	  var self = $(this);

	  showPage(self);
  });
  
  $("#frame_content").load(function(){
	  var src = $("#frame_content").attr("src");
	  if(src=="login.html") {
		  //子画面加载了Login，Index直接跳转Login/////貌似没搞定！！！
		  window.parent.href="/login";
	  }
  }); 
  
  $("#changePwd").click(function(){
	  var self = $(this);

	  var fields = [
		{
			"name":"id",
			"label":"id",
			"type":"hidden",
			"value":$("#userid").val(),
			"required":"required",
		},
		{
			"name":"password",
			"label":"旧密码",
			"type":"password",
			"required":"required",
		},
		{
			"name":"password2",
			"label":"新密码",
			"type":"password",
			"required":"required",
		},
		{
			"name":"password3",
			"label":"再输入新密码",
			"type":"password",
			"required":"required",
		},
		];
		
		var dlg = new CommonDlg();
		dlg.showFormDlg({
			"target":"chg_pwd_dlg_div",
			"caption":"修改密码",
			"fields":fields,
			"url":"/user/changepwd",
			"valid": function() {
				if(dlg.fieldVal("password2") != dlg.fieldVal("password3")) {
					dlg.setError("password3", "两次输入密码不一致");
					return false;
				}
				return true;
			},
			"success": function(data) {
				dlg.hide();
				PdSys.success({
					"ok" : function() {
						PdSys.refresh();
					}
				});
			},
			"error": function(data) {
				alert(data.msg);
			}
		});
  	});
});

function showPage(pATag)
{
	var menuId = pATag.attr('id');
	var url = pATag.attr('refURL')
	var menuDisplay = pATag.attr('displayText')
	
	//reset background color
	var menus = $("a[id^='menu_']");
	menus.each(function()
	{
		if( $(this).attr('id') == menuId )
		{
			$(this).addClass('active');
		}
		else
		{
			$(this).removeClass('active');
		}
	});

	var frame = $("iframe[id='frame_content']");
	frame.attr('src', url);

	//更新导航条
	$("#nav_title").html(menuDisplay);
}

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 155);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

