<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<html>
<head>
<title>报数管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	 	$("#btnExport").click(function(){
			top.$.jBox.confirm("确认要导出报数统计数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#inputForm").attr("action","${ctx}/fojiao/fjBaoshu/export");
					$("#inputForm").submit();
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	 	$("#btnSearch").click(function(){
	 		$("#inputForm").attr("action","${ctx}/fojiao/fjBaoshu/list");
			$("#inputForm").submit();
		});
	}); 
	function userIdTreeselectCallBack(v, h, f,ids){
		//根据f对应的id查询对应的用户信息
		 $.post('${ctx}//sys/user/getUserInfo', {
             id:ids
         }, function (result) {
             if (result.success) {
                var user = result.obj;
                $("#userLogin").html(user.loginName);
                if(user.daixiu){
                	$("#daixiuName").html(user.daixiu.name);
                    $("#daixiuLogin").html(user.daixiu.loginName);	
                }else{
                	$("#daixiuName").html("");
                    $("#daixiuLogin").html("");	
                }
                
             } else {
                 alert(result.msg);
             }
         }, 'JSON');
	}
	function initBaoshu(){
		//统计报数
		var str = "";
		$(".baoshutype").each(function(){
			var key = $(this).attr("id").replace("num_","");
			var value = $(this).val()?parseInt($(this).val()):0;
			if(isNaN(value)) {
				$("#messageBox").text("报数必须为数字");
				$(this).focus();
				return false;
			}
			str+=key+":"+value+";";
		});
		$("#baoshuNum").val(str);
		return true;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:void(0);"><shiro:hasPermission
					name="fojiao:fjBaoshu:edit">${not empty fjBaoshu.id?'修改':'提交'}</shiro:hasPermission>
				<shiro:lacksPermission name="fojiao:fjBaoshu:edit">查看</shiro:lacksPermission>报数</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="fjBaoshu"
		action="${ctx}/fojiao/fjBaoshu/save" method="post"
		class="form-horizontal" onsubmit="return initBaoshu();">
		<input name="baoshuNum" id="baoshuNum" type="hidden"/>
		<sys:message content="${message}" />
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<td width="25%"><label class="control-label">共修师兄：</label></td>
			<td width="25%">
			<sys:treeselect id="userId" name="createBy.id" value="${fjBaoshu.createBy.id}" labelName="createBy.name" labelValue="${fjBaoshu.createBy.name}" 
				title="用户" url="/sys/user/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true" />
			</td>
			<td width="25%"><label class="control-label">代修师兄：</label></td>
			<td width="25%"><label class="control-label" id="daixiuName">${fjBaoshu.createBy.daixiu.name }</label></td>
		</tr>
		<tr>
			<td><label class="control-label">共修编号：</label></td>
			<td><label class="control-label" id="userLogin">${fjBaoshu.createBy.loginName }</label></td>
			<td><label class="control-label">共修编号：</label></td>
			<td><label class="control-label" id="daixiuLogin">${fjBaoshu.createBy.daixiu.loginName }</label></td>
		</tr>
		<tr>
			<td><label class="control-label">共修日期：</label></td>
			<td>
				<input id="createDate" name="createDate" type="text"
						readonly="readonly" maxlength="20" class="input-xlarge Wdate"
						value="${today }"
						onclick="WdatePicker({minDate:'${minDate}',maxDate:'${maxDate}',dateFmt:'yyyy-MM-dd',isShowClear:false});"
						placeholder="请选择报数日期"  />
			</td>
			<td><label class="control-label">代修日期：</label></td>
			<td>
				<input id="createDate" type="text"
						readonly="readonly" maxlength="20" class="input-xlarge Wdate"
						value="${today }"
						onclick="WdatePicker({minDate:'${minDate}',maxDate:'${maxDate}',dateFmt:'yyyy-MM-dd',isShowClear:false});"
						placeholder="请选择报数日期" />
			</td>
		</tr>
		<tr>
			<th><label class="control-label">共修项目</label></th>
			<th><label class="control-label">当日共修报数</label></th>
			<th><label class="control-label">已完成数量</label></th>
			<th><label class="control-label">班内排名</label></th>
		</tr>
		<c:forEach items="${list}" var="type">
		<tr>
			<td><label class="control-label">${type.label }</label></td>
			<td><input type="text" id="num_${type.value }" class="input-xlarge baoshutype" placeholder="请输入报数次数" value="${type.today }"/></td>
			<td><label class="control-label">${type.nums }</label></td>
			<td><label class="control-label">${type.seq }</label></td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="4" style="text-align: center;">
			<shiro:hasPermission name="fojiao:fjBaoshu:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="提交报数" />&nbsp;</shiro:hasPermission>
				<input id="btnSearch" class="btn btn-primary" type="button"
					value="查询" />
			<input id="btnExport" class="btn btn-primary" type="button"
					value="导出" />
			</td>
		</tr>
		</table>
	</form:form>
</body>
</html>