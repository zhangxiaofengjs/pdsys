$(document).ready(function(){
  $("a[refURL]").click(function(){
	  var self = $(this);

	  showPage(self);
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
