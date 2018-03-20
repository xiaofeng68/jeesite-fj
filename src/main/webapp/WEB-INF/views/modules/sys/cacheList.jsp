<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/cache/list">缓存管理</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>缓存名称</th>
			<shiro:hasPermission name="sys:cache:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>系统缓存</td>
			<shiro:hasPermission name="sys:user:edit"><td><a href="${ctx}/sys/cache/reload?name=sysCache">清理</a></td></shiro:hasPermission>
		</tr>
		<tr>
			<td>用户缓存</td>
			<shiro:hasPermission name="sys:user:edit"><td><a href="${ctx}/sys/cache/reload?name=userCache">清理</a></td></shiro:hasPermission>
		</tr>
		<tr>
			<td>集团缓存</td>
			<shiro:hasPermission name="sys:user:edit"><td><a href="${ctx}/sys/cache/reload?name=corpCache">清理</a></td></shiro:hasPermission>
		</tr>
		<tr>
			<td>内容管理模块缓存</td>
			<shiro:hasPermission name="sys:user:edit"><td><a href="${ctx}/sys/cache/reload?name=cmsCache">清理</a></td></shiro:hasPermission>
		</tr>
		<tr>
			<td>简单页面缓存</td>
			<shiro:hasPermission name="sys:user:edit"><td><a href="${ctx}/sys/cache/reload?name=actCache">清理</a></td></shiro:hasPermission>
		</tr>
		<tr>
			<td>工作流模块缓存</td>
			<shiro:hasPermission name="sys:user:edit"><td><a href="${ctx}/sys/cache/reload?name=pageCachingFilter">清理</a></td></shiro:hasPermission>
		</tr>
		<tr>
			<td>系统活动会话缓存</td>
			<shiro:hasPermission name="sys:user:edit"><td><a href="${ctx}/sys/cache/reload?name=activeSessionsCache">清理</a></td></shiro:hasPermission>
		</tr>
		</tbody>
	</table>
</body>
</html>