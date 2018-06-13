$(document).ready(function(){
  //先更新一次，5分钟更新一次通知数量
  updateIndexNoticeCount();
  setInterval(updateIndexNoticeCount,5*60*1000);//5*60*1000
	
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
	$("#nav_title").html("主页 > " + menuDisplay);
}

function updateIndexNoticeCount() {
	PdSys.ajax({
		"url":"/notice/getcount",
		"disableSysError": true,
		"success": function(data) {
			$('#noticeCount').html(data.count==0?'':data.count);
		},
		"error": function(data) {
			$('#noticeCount').html('');
		}
	});
}

function initContentHeight(h, framWin) 
{
	if(h<$(document).height()) {
		h = $(document).height();
	}
var div = window.parent.document.getElementById('main_div'); 
div.style.height=h+"px";//window.document.body.scrollHeight||window.document.body.offsetHeight+5;

	framWin.style.height=(h-30) + "px";//注意减去右侧标题栏高度
} 

function SetWinHeight(obj) 
{ 
  var win=obj; 
  var height;
   if (win && !window.opera) 
   { 
     if (win.contentWindow && win.contentWindow.document.body.offsetHeight)   //ie  8 
     { 
        try{ 
			var bheight=win.contentWindow.document.body.scrollHeight; 
			var dheight=win.contentWindow.document.documentElement.scrollHeight; 
			    height=Math.max(bheight,dheight); 
			}catch(ex){} 
			win.style.height = height + "px"; 
     } 
     else if(win.Document && win.Document.body.scrollHeight)//ie  6 
     { 
      win.style.height = win.Document.body.scrollHeight; 
     } 
   }
   initContentHeight(height, win); //加上右边标题
} 

/*
//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height());
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();
*/
