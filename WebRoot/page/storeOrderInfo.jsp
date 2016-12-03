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
<title>门店订单详情</title>
<link href="<%= basePath %>page/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%= basePath %>page/css/iconfont.css" rel="stylesheet" type="text/css" />  

</head>

<body>
<article class="order-box pb70">
    <article class="order-info-box">
        <header><h1>订单信息</h1></header>
        <ul class="order-info-text">
        	<li>
            	<span>订单编号</span>
                <p><c:if test="${!empty order}">
               		 ${order.orderSn}
				   </c:if>
				</p>
				   
            </li>
            <li>
            	<span>下单时间</span>
                <p><time pubdate="pubdate"><c:if test="${!empty order}">
               		 ${order.accdate}
				   </c:if>
				   </time>
				</p>
            </li>
            <li>
            	<span>订单状态</span>
                <p><span class="red-state">
					<c:if test="${!empty order}">
               		 ${order.orderStatus}
				   </c:if>
				   </span>
			   </p>
            </li>
        </ul>
    </article>
    <article class="order-info-box">
        <header><h1>商品信息</h1></header>
        <ul class="order-product-info-md"> 
        <c:if test="${!empty goodsList }">
          <c:forEach items="${goodsList}" var="item">
        	<li>
                <div class="order-product">
                	<div class="order-product-unit">
                    	<p class="order-product-price">￥${item.price }</p>
                        <p class="order-product-count">x${item.orderItemQty }</p>
                    </div>
                	<div class="order-product-title">
                    	<h2>${item.shortName }</h2>
                        <p class="order-product-spec">${item.spec }</p>
                    </div>
                </div>
            </li>
          </c:forEach> 
         </c:if>  
        </ul>
    </article>
    <article class="order-info-box">
        <header><h1>订单金额</h1></header>
        <ul class="order-info-text order-info-price" style="padding:0;">
            <li style="border:none;margin:0">
            	<span>应付金额</span>
                <p class="final-price">￥
                   <c:if test="${!empty order}">
               		 ${order.orderAmount}
				   </c:if>
				</p>
            </li>
        </ul>
    </article>
</article>
<div class="bottom-btn">
	<a href="<%= basePath %>sltRouter?method=comment&sessionId=${sessionId}&orderSn=${orderSn}" class="common-btn orange-btn">评 论</a>
</div>
</body>
<script type="text/javascript" src="<%= basePath %>page/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%= basePath %>page/js/common.js"></script>
</html>
