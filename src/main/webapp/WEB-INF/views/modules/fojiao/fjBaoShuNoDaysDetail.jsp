<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<html>
<head>
<title>报数管理</title>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:void(0);">班级统计</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="fjBaoshu"
		action="${ctx}/fojiao/fjBaoshu/noDetail" method="post"
		class="form-horizontal" >
		<sys:message content="${message}" />
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<td colspan="2">
			连续3天不报的师兄名单如下：
			</td>
		</tr>
		<c:forEach items="${list3}" var="item">
		<tr><td width="150px">@${item.name }</td><td>${item.mobile }</td></tr>
		</c:forEach>
		
		<tr>
			<td colspan="2">
			连续一周不报的师兄名单如下：
			</td>
		</tr>
		<c:forEach items="${list7}" var="item">
		<tr><td width="150px">@${item.name }</td><td>${item.mobile }</td></tr>
		</c:forEach>
		</table>
	</form:form>
</body>
</html>