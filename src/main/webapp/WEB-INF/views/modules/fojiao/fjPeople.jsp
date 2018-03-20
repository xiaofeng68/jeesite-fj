<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>报数统计管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function(){
			top.$.jBox.confirm("确认要导出个人历史数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/fojiao/fjBaoshu/exportPeopleData");
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
		<li class="active"><a href="${ctx}/fojiao/fjBaoshu/people">个人报数历史</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="fjBaoshuTj"
		action="${ctx}/fojiao/fjBaoshu/people" method="post"
		class="breadcrumb form-search">
		<label>用户名：</label>
		<sys:treeselect id="userId" name="createBy.id" value="${fjBaoshu.createBy.id}" labelName="createBy.name" labelValue="${fjBaoshu.createBy.name}" 
				title="用户" url="/sys/user/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
		<label>开始时间：</label>
		<input id="startDate" name="startDate" type="text" readonly="readonly"
			maxlength="20" class="input-small Wdate" value="${startDate }"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
			placeholder="请选择日期" />

		<label>结束时间：</label>
		<input id="endDate" name="endDate" type="text" readonly="readonly"
			maxlength="20" class="input-small Wdate" value="${endDate }"
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
				<th>共修报数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="type">
			<tr>
				<td width="150px">${type.label }</td>
				<td>${type.nums }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>