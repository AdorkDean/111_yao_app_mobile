// JavaScript Document
//展开收起
$(function(){
	var liCount = $(".order-product-info").children().length;
	oPro = $(".order-product-info");
	if(liCount == 1){
		oPro.css({"height":91+"px"});
		$(".show-more").css({"display":"none"})
	}else{
		$(".show-more").click(function(){
		oPro = $(".order-product-info");
		if(oPro.hasClass("height-auto")){
			oPro.removeClass("height-auto");
			$(".show-more").html("显示全部");
		}else{
			oPro.addClass("height-auto");
			$(".show-more").html("收起部分商品");
		}	
	})
}
})