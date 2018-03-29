$(document).ready(function(){
  $("a[id^='menu_']").click(function(){
	  var self = $(this);

	  showPage(self.attr('id'), self.attr('refURL'));
  });
});

function showPage(menuId, url)
{
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
		}
	);

	var frame = $("iframe[id='frame_content']");
	frame.attr('src', url);
}

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 155);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();
