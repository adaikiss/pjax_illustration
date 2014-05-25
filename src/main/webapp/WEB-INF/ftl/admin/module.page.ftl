<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<@adminHeader title="模块管理"/>
	<script type="text/javascript">
	$(function(){
	});
	</script>
</head>
<body>
	<div id="body">
		<div id="header">
		</div>
		<div id="middle">
			<div id="left"></div>
			<div id="right">
				<div>
					<span>模块管理</span>
				</div>
				<div>
					<form id="searchForm" method="POST">
						<input type="hidden" name="page.size" value="5">
						名称:<input type="text" name="name"><input type="submit" value="查询">
					</form>
				</div>
				<div id="tableContent">
					<#include "/admin/fragment/module.page.ftl"/>
				</div>
			</div>
		</div>
	</div>
</body>
</html>