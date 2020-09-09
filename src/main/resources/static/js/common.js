$(function(){
	
	jQuery.fn.center = function () {
		this.css("position","fixed");
		return this;
	};

	// 탭
	$('ul.tab-menu li').click(function () {
		$('ul.tab-menu li').removeClass('active');
		$(this).addClass('active');
		$('.tab-cont > div').removeClass('active')
			var activeTab = $(this).attr('rel');
		$('#' + activeTab).addClass('active')
	})

	$('ul.select-type li').click(function () {
		$('ul.select-type li').removeClass('active');
		$(this).addClass('active');
	})


	// 메뉴슬라이드
	$('.slide-togg > dt, .slide-togg > .dt').on('click', function () {
		if ($(this).hasClass('on')) {
			slideUp();
		} else {
			slideUp();
			$(this).addClass('on').next().slideDown();
		}
		function slideUp() {
			$('.slide-togg > dt, .slide-togg > .dt').removeClass('on').next().slideUp();
		};
	});

});

// popup
function openLayer(el) {
	var temp = $('#' + el);
	var tempInner = $('#' + el + ' .pop-wrap');
	console.log(temp);
	if(!$('.popup:visible').length) {
		$("<div/>", {
			"class": "dim-layer",
		}).appendTo('.popup').fadeIn(200);
	}
	temp.fadeIn(200).center();
	tempInner.fadeIn(200).center();
	//setModalMaxHeight(temp);

	// $('body, html, #wrap').css('overflow','hidden').css('height','100%');
	$('body').on('click touchstart', '.popup .btn-confirm, .popup .btn-cancel, .popup .pop-close, .dim-layer', function() {
		$(this).parent().parent('.popup').hide();
		temp.fadeOut(200);
		if($('.dim-layer').length) {
			$('.dim-layer').fadeOut(200, function() {
				$(this).remove();
			});
		}
		$('body, html, #wrap').css('overflow','').css('height','');
	});
}

