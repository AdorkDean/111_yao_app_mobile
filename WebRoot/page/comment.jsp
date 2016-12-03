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
    <title>评论</title>
<link href="<%= basePath %>page/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%= basePath %>page/css/iconfont.css" rel="stylesheet" type="text/css" /> 

</head>

<body>
<article class="order-box">
	<article class="order-info-box">
        <header><h1>订单信息</h1></header>
        <ul class="order-info-text">
        	<li>
            	<span>订单编号</span>
                <p>
                   <c:if test="${!empty order}">
               		 ${order.orderSn}
				   </c:if>
				</p>
            </li>
            <li>
            	<span>下单时间</span>
                <p><time pubdate="pubdate">
                    <c:if test="${!empty order}">
               		 ${order.accdate}
				   </c:if>
				   </time></p>
            </li>
            <li>
            	<span>订单状态</span>
                <p><span class="red-state">
                <c:if test="${!empty order}">
               		 ${order.orderStatus}
				   </c:if>
				   </span></p>
            </li>
        </ul>
    </article>
    
    <div class="common-text">
    	<textarea placeholder="宝贝很好哦，极力向大家推荐！！" id="content" onpropertychange="checkLength(this,100);" oninput="checkLength(this,100);"></textarea>
    </div>
    <input type="hidden" id="sessionId" value="${sessionId}">
    <input type="hidden" id="orderSn" value="${orderSn}">
    <!--<p class="comment-tips">发表评论赢取积分哟~</p>-->
</article>
<div class="bottom-btn">
	<a href="javascript:void(0)" class="common-btn orange-btn" onclick="sub()">提交评论</a>
</div>
</body>
<script type="text/javascript" src="<%= basePath %>page/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%= basePath %>page/js/common.js"></script>
<script type="text/javascript">
var u = navigator.userAgent, app = navigator.appVersion;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; 
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); 

 function sub(){
    var sessionId = '${sessionId}';
    var orderSn = '${orderSn}';
    var content = $("#content").val();
    if($.trim(content)==""){
      	content = "宝贝很好哦，极力向大家推荐！！";
    }
    if(content.length>100){
        if(isAndroid){
			contact.toast("评论内容不得大于100个字符");
	    }else if(isiOS){
			alert("评论内容不得大于100个字符");
	    }else{
		    alert("评论内容不得大于100个字符");
	    }
       return;
    }
    $.post("<%=basePath %>sltRouter?method=storeOrderComment", {sessionId:sessionId,orderSn:orderSn,content:content,random:Math.random()}, function(data){
          if(isAndroid){
			contact.toast(data);
			contact.finishs();
	      }else if(isiOS){
			alert(data);
			document.location = "type*4";
	      }else{
		    alert(data);
	      }
    });
 }
 
function checkLength(obj,maxlength){
    if(obj.value.length > maxlength){
        obj.value = obj.value.substring(0,maxlength);
    }
}

</script>
</html>
