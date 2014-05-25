<table width="1024px;" border="0" cellspacing="0" cellpadding="0" class="u-table" <#if pagination.totalPages &gt; 1>style="border-bottom:1px solid #7C7C7C"</#if>>
	<tr style="background-color:#eee0e3;height:40px;">
		<th style="width:50px;text-align:right;">编号</th>
		<th style="width:100px;">名称</th>
		<th style="width:100px;">父模块</th>
		<th style="width:120px;">url</th>
		<th style="width:100px;">权限</th>
		<th style="width:80px;">标签</th>
		<th style="width:50px;">状态</th>
		<th style="width:50px;text-align:right;">排序</th>
		<th>描述</th>
		<th style="width:80px;">操作</th>
	</tr>
<#if pagination.numberOfElements == 0>
<tr>
	<td colspan="42" style="text-align:center">暂无数据.</td>
</tr>
</#if>
<#list pagination.content as record>
	<tr style="height:40px;<#if (record_index % 2) == 1>background-color:#eeeeee;</#if>">
		<td style="text-align:right;padding-right:8px;">${record.id!!}</td>
		<td style="text-align:center;" class="_name">${record.name!!}</td>
		<td style="text-align:center;">${(record.parent.name)!!}</td>
		<td style="text-align:center;"><#if record.url??>${(record.url)!!}<#else><span style="color:#999999;">第一个有权限的可见子模块</span></#if></td>
		<td style="text-align:center;"><#if record.perm??>${(record.perm)!!}<#elseif record.parent??&record.parent.perm??><span style="color:#999999;">${record.parent.perm}</span></#if></td>
		<td style="text-align:center;">${record.tag!!}</td>
		<td style="text-align:center;"><#if record.state=1>显示<#else>隐藏</#if></td>
		<td style="text-align:right;padding-right:8px;">${record.order!!}</td>
		<td style="text-align:center;">${record.description!!}</td>
		<td style="text-align:center;"></td>
	</tr>
</#list>
</table>
<@a.page pagination "/admin/module/page" false/>