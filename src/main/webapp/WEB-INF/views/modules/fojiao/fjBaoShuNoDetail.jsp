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
			<td >
				<label class="control-label">共修日期：</label>
				<input id="createDate" name="createDate" type="text"
						readonly="readonly" maxlength="20" class="input-xlarge Wdate"
						value="<fmt:formatDate value="${empty fjBaoshu.createDate?now:fjBaoshu.createDate}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
						placeholder="请选择报数日期" />
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="查询" />
			</td>
		</tr>
		<tr>
			<td >
			顶礼大恩上师！/mg/mg/mg
			<br/><fmt:formatDate value="${empty fjBaoshu.createDate?now:fjBaoshu.createDate}" pattern="yyyy-MM-dd"/> 亿遍共修未报数名单公布：
			</td>
		</tr>
		<c:forEach items="${list}" var="item">
		<tr><td width="150px">@${item.name }</td></tr>
		</c:forEach>
		<tr>
		<td><p class="MsoListParagraph" style="margin-left:24px">
		    <span style="font-family: 宋体">请师兄们核对一下，如有错误，及时反馈</span>/mg/mg/mg<span style="font-family:宋体">。请师兄们积极报数，积累福德资粮！</span>
		</p>
		</td>
		</tr>
		<tr>
		<td>
		<p class="MsoListParagraph" style="margin-left:24px;text-indent:0">
		    <span style="font-family:宋体">不管遇到何种对境，都不应忘失最初发心，逆境来时绝不屈服，顺境来时越要谦虚。加油、努力、向上、拼搏。</span>/fendou
		</p>
		</td>
		</tr>
		</table>
	</form:form>
</body>
</html>