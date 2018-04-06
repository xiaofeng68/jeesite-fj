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
		action="${ctx}/fojiao/fjBaoshu/classStatic" method="post"
		class="form-horizontal" >
		<sys:message content="${message}" />
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<td colspan="2">
				<label>归属群：</label>
				<sys:treeselect id="company" name="createBy.office.id" value="${fjBaoshu.createBy.office.id}" labelName="company.name" labelValue="${fjBaoshu.createBy.office.name}" 
				title="群" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"/>
				<label>开始日期：</label>
				<input id="startDate" name="startDate" type="text"
						readonly="readonly" maxlength="20" class="input-xlarge Wdate"
						value="${fjBaoshu.startDate}"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
						placeholder="请选择报数日期" />
						
				<label>结束日期：</label>
				<input id="endDate" name="endDate" type="text"
						readonly="readonly" maxlength="20" class="input-xlarge Wdate"
						value="${fjBaoshu.endDate}"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
						placeholder="请选择报数日期" />
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="查询" />
			</td>
		</tr>
		<tr>
			<td colspan="2">顶礼大恩上师佛！</td>
		</tr>
		<c:forEach items="${list}" var="item">
		<tr><td width="150px">班级名</td><td>${item.name }</td></tr>
		<tr><td>亿遍班总人数</td><td>${item.allnums }</td></tr>
		<tr><td>应报人数</td><td>${item.allnums }</td></tr>
		<tr><td>实报人数</td><td>${item.nums }</td></tr>
		<tr><td>未报人数</td><td>${item.mnums }</td></tr>
		</c:forEach>
		</table>
	</form:form>
</body>
</html>