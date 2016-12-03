// JavaScript Document
//展开收起
//版本号
	var brower = {
	versions: function() {
		var u = window.navigator.userAgent;
		var num;
		if (u.indexOf('Mobile') > -1) {
			//移动端
			if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
				//android
				num = u.substr(u.indexOf('Android') + 8, 3);
				return num;
			}
		}
	}
}



$(function() {
    var liCount = $(".order-product-info").children().length;
    oPro = $(".order-product-info");
    if (liCount == 1) {
        oPro.css({
            "height": 97 + "px"
        });
        $(".show-more").css({
            "display": "none"
        })
    } else if (liCount == 2) {
        oPro.css({
            "height": 194 + "px"
        });
        $(".show-more").css({
            "display": "none"
        })
    } else {
        $(".show-more").click(function() {
			
			if(brower.versions()==4.3){
				if (oPro.hasClass("height-auto")) {
                 	location.reload();
				} else {
					oPro.addClass("height-auto");
					$(".show-more").html("收起部分商品");
				}
			} else {
				if (oPro.hasClass("height-auto")) {
					oPro.removeClass("height-auto");
					$(".show-more").html("显示全部");
					oPro.css({
						"height": 194 + "px"
					});
				} else {
					oPro.addClass("height-auto");
					$(".show-more").html("收起部分商品");
				}
			}
			
        })
    }
})

$(function() {
    var liCurr = $(".pay-info-box").children('li');
    liCurr.click(function() {
        var _th = $(this);
        if (_th.hasClass('curr')) {
            _th.siblings('li').removeClass('curr');

        } else {
            _th.addClass('curr');
            _th.siblings('li').removeClass('curr');

        }

    })

})