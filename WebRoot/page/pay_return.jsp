<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!doctype html>
<html lang="zh-CN">
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<title>支付回掉</title>
<script type="text/javascript" src="<%= basePath %>page/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
function payReturn(){
	var orderId = $("#orderId").val();
	var paySuccessFlag =$("#paySuccessFlag").val();
	document.location = "orderId*"+orderId+"*"+paySuccessFlag;
	alert("paySuccessFlag====>"+paySuccessFlag);
}
</script>
</head>
<body onload="payReturn()">
	<input type="hidden" name="orderId" value="${orderId}" id="orderId" />
	<input type="hidden" name="paySuccessFlag" value="${paySuccessFlag}" id="paySuccessFlag" />
</body>

</html>
