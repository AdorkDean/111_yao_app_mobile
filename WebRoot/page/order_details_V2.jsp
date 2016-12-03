<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String imgPath = "http://ui.111yao.com";
 %>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<!--允许全屏-->
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="fullscreen=yes,preventMove=no" name="ML-Config">
<!--缩放比例-->
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta name="format-detection" content="email=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>订单详情</title>
<link href="<%= basePath %>page/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%= basePath %>page/css/iconfont.css" rel="stylesheet" type="text/css" />  
<script type="text/javascript" src="<%= basePath %>page/js/jquery-1.7.2.min.js"></script>

</head>

<body>
<article class="order-box">
    <article class="order-info-box">
        <header><h1>订单信息</h1></header>
        <ul class="order-info-text">
        	<li>
            	<span>订单编号</span>
                <p>${order.order_sn}</p>
            </li>
            <li>
            	<span>下单时间</span>
                <p><time pubdate="pubdate">${order.create_date}</time></p>
            </li>
            <li>
            	<span>支付方式</span>
                <p>${order.name}</p>
            </li>
            <li>
            	<span>订单状态</span>
                <p style="color:#ff8338"><c:if test="${order.order_status==0}">订单待付款</c:if><c:if test="${order.order_status==1}">订单待收货</c:if><c:if test="${order.order_status==2}">订单待收货</c:if><c:if test="${order.order_status==3}">订单已完成</c:if><c:if test="${order.order_status==4}">订单已取消</c:if></p>
            </li>
        </ul>
    </article>
    <article class="order-info-box">
        <header><h1>商品信息1</h1></header>
        <ul class="order-product-info">
			<c:forEach items="${itemList}" var="item">
			<a href="javascript:product(${item.goods_id},'${item.short_name}');" >
				<li>
	            	<img src="<%=imgPath%>${item.abbreviation_picture}">
	                <div class="order-product">
	                	<div class="order-product-unit">
	                    	<p class="order-product-price">￥${item.price}</p>
	                        <p class="order-product-count">x${item.quantity}</p>
	                    </div>
	                	<div class="order-product-title">
	                    	<h2>${item.short_name}</h2>
	                        <p class="order-product-spec">规格:${item.spec}</p>
	                    </div>
	                </div>
			     </li>		
			     </a>	        
			</c:forEach>
        </ul>
        <a href="javascript:void(0)" class="show-more">显示全部</a>
    </article>
    <article class="order-info-box">
        <header><h1>收货信息</h1></header>
        <ul class="order-info-text">
        	<li>
            	<span>收货人</span>
                <p>${order.receiver}</p>
            </li>
            <li>
            	<span>手机号码</span>
                <p>${order.phone}</p>
            </li>
            <li>
            	<span>收货地址</span>
                <p>${order.area_name}&nbsp;&nbsp;${order.detailed_address}</p>
            </li>
            <c:if test="${not empty order.invoice_title}">
            <li>
            	<span>发票抬头</span>
                <p>${order.invoice_title}</p>
            </li>
            </c:if>
        </ul>
    </article>
    <article class="order-info-box">
        <header><h1>订单金额</h1></header>
        <ul class="order-info-text order-info-price">
        	<li>
            	<span>商品总金额</span>
                <p>￥${order.product_price}</p>
            </li>
            <li>
            	<span>运费</span>
                <p id="freight">￥${order.shipping_fee}</p>
            </li>
            <li>
            	<span>使用优惠劵</span>
                <p id="coupon"></p>
            </li>
            <li>
            	<span>应付金额</span>
                <p class="final-price" id="price">￥${order.payable_amount}</p>
            </li>
        </ul>
    </article>
    <c:if test="${order.order_status=='0'}">
     <ul class="pay-info-box">
    	<c:forEach items="${list}" var="pay">
    	<li onclick="javascript:pay('${pay.paymethodId}','${pay.paypluginId}','${sessionId}',${order.id});">
        	<b></b>
            <div class="pay-way clearfix"><img src="${pay.imgurl}">${pay.name}</div>
        </li>
        </c:forEach>
    </ul>
    </c:if>
</article>
</body>
<script type="text/javascript" src="<%= basePath %>page/js/common.js"></script>
<script type="text/javascript">

var freight = ${order.shipping_fee};
var price = ${order.payable_amount};
var coupon = ${order.coupon_discount};
$('#coupon').html("￥"+coupon.toFixed(2));
function product(id,name){
   document.location = "id*"+id+"*"+name;
}

function pay(methodId,pluginId,sessionId,orderId){
    //alert("methodId="+methodId);
    //alert("sessionId="+sessionId);
    //alert("orderId="+orderId);
    $.post("<%=basePath %>sltRouter?method=orderFreight", {sessionId:sessionId,orderId:orderId,paymentmethodId:methodId,random:Math.random()}, function(data){
        //alert(data);
        var s=jQuery.parseJSON(data);
        $('#freight').html("￥"+parseFloat(s.freight).toFixed(2));
        //alert(s.price);
        var p = parseFloat(price)-parseFloat(freight)+parseFloat(s.freight);
        //alert(p);
        $('#price').html("￥"+parseFloat(p).toFixed(2));
        price = p;
        freight = s.freight;
    });
    document.location = "methodId*"+methodId+"*"+pluginId;
}


</script>
</html>
