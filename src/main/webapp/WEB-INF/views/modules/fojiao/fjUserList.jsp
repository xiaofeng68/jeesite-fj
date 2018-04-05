<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>报数统计管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function(){
			top.$.jBox.confirm("确认要导出用户统计数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/fojiao/fjBaoshu/exportUserStatiscs");
					$("#searchForm").submit();
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/fojiao/fjBaoshu/userList">用户统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="fjBaoshuTj"
		action="${ctx}/fojiao/fjBaoshu/userList" method="post"
		class="breadcrumb form-search">
		<label>开始时间：</label>
		<input id="startDate" name="startDate" type="text" readonly="readonly"
			maxlength="20" class="input-xlarge Wdate" value="${startDate }"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
			placeholder="请选择日期" />

		<label>结束时间：</label>
		<input id="endDate" name="endDate" type="text" readonly="readonly"
			maxlength="20" class="input-xlarge Wdate" value="${endDate }"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
			placeholder="请选择日期" />
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
		<input id="btnExport" class="btn btn-primary" type="button"
					value="导出" />
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>共修编号</th>
				<th>共修昵称</th>
				<th>登陆名</th>
				<c:forEach items="${fns:getDictList('PROTYPE')}" var="type">
					<th>${type.label }</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="data">
			<tr>
			<c:choose>
				<c:when test="${empty data.login_name }">
				<td style="text-align: center;" colspan="3">合计</td>
				</c:when>
				<c:otherwise>
				<td>${data.login_name }</td>
				<td>${data.name }</td>
				<td>${data.name }</td>
				</c:otherwise>
			</c:choose>
			<c:forEach items="${fns:getDictList('PROTYPE')}" var="type">
			<th>${data[type.value]
			 }</th>
			</c:forEach>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>