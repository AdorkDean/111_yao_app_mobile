<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!doctype html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
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
<title>查看物流</title>
<link href="<%= basePath %>page/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%= basePath %>page/css/iconfont.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%= basePath %>page/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%= basePath %>page/js/common.js"></script>
<script type="text/javascript">
function product(id,name){
	   document.location = "id*"+id+"*"+name;
}
</script>
</head>

<body>
<article class="order-box">
	<dl class="logistics-company clearfix">
    	<dt><img src="<%= basePath %>page/images/logistics-img.jpg"></dt>
        <dd>
        	<p class="company-title">${obj.name }</p>
            <p class="serial-number">运单编号：${obj.expid }</p>
            <p class="serial-number">物流状态：${obj.status }</p>
        </dd>
    </dl>
    <article class="order-info-box">
        <header><h1>物品信息</h1></header>
        <ul class="order-product-info">
			<c:forEach items="${obj.plist}" var="list" varStatus="id">
        	<li>
        	<a href="javascript:product(${list.id},'${list.name}');">
            	<img src="http://ui.111yao.com/${list.thumbnail }">
        	</a>
                <div class="order-product">
                	<div class="order-product-unit">
                    	<p class="order-product-price">${list.price }</p>
                        <p class="order-product-count">x${list.quantity }</p>
                    </div>
                	<div class="order-product-title">
                		<a href="javascript:product(${list.id},'${list.name}');">
	                    	<h2>${list.name}</h2>
                		</a>
                    </div>
                </div>
            </li>
			</c:forEach>
        </ul>
        <c:if test="${fn:length(obj.plist)>1}">
        	<a href="javascript:void(0)" class="show-more">显示全部</a>
        </c:if>
    </article>
    
    <c:if test="${fn:length(obj.llist)>0}">
    <article class="order-info-box">
        <header><h1>物流跟踪</h1></header>
        <ul class="follow-logistics">
        <c:forEach items="${obj.llist}" var="list" varStatus="id">
        	<c:if test="${id.index==0}">
        	<li class="first-state">
            	<div class="logistics-info">
                    <p class="logistics-state">${list.context }</p>
                    <p class="logistics-time"><time pubdate="pubdate">${list.time }</time></p>
                </div>
            </li>
        	</c:if>
        	<c:if test="${id.index!=0}">
        	<li>
            	<div class="logistics-info">
                    <p class="logistics-state">${list.context }</p>
                    <p class="logistics-time"><time pubdate="pubdate">${list.time }</time></p>
                </div>
            </li>
        	</c:if>
        </c:forEach>
        </ul>
    </article>
    </c:if>
</article>
</body>
</html>
