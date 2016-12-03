<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!doctype html>
<html lang="zh-CN">
<head>
<title>医卡通支付</title>
</head>
<body onload="javascript: document.forms[0].submit();">
	<form action="${requestUrl}" method="${requestMethod}"
		accept-charset="${requestCharset}">
		<c:forEach var="map" items="${parameterMap}">
			<input type="hidden" name="${map.key}" value="${map.value}" />
		</c:forEach>
		<c:forEach items="${items}" var="item">
			<input type="hidden" name="mname"
				value="${item.goods_name}" />
			<input type="hidden" name="mspec" value="-" />
			<input type="hidden" name="mtype" value="-" />
			<input type="hidden" name="munit" value="-" />
			<input type="hidden" name="mprice" value="${item.price}" />
			<input type="hidden" name="mnum" value="${item.quantity}" />
			<input type="hidden" name="mcode" value="${item.goodsno}" />
		</c:forEach>
	</form>
</body>
</html>
