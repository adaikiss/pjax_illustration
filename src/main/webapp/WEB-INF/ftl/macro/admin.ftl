<#-- page -->
<#macro page pagination action optional=true>
<input type="hidden" value="${pagination.number!'1'}" id="currentPage">
<#assign pageCount=pagination.totalPages>
<#if pageCount &gt; 1 || !optional>
<#assign page=pagination.number>
<#--
	<div class="page">
		<a data-pjax href="${base}${action}/1" class="first pageprv pjax"><span class="pagearr">&nbsp;First&nbsp;</span></a>
		<a data-pjax <#if page &gt; 1>href="${base}${action}/${page - 1}"></#if><span class="pagearr">&lt;</span></a>
	    <#if page = 1 || page = 2>
	    	<#if pageCount &gt; 5>
	    		<#list 1.. 5 as pageIndex>
	    			 <a data-pjax href="${base}${action}/${pageIndex}" <#if page=pageIndex>class="z-crt"</#if>>${pageIndex}</a>
	    		</#list>
	    	<#else>
	    		<#list 1.. pageCount as pageIndex>
	    			 <a data-pjax href="${base}${action}/${pageIndex}" <#if page=pageIndex>class="z-crt"</#if>>${pageIndex}</a>
	    		</#list>
	    	</#if>	
	    <#else>
	    	<#if page - 2 &gt; 0><a data-pjax href="${base}${action}/${page - 2}">${page - 2}</a></#if>
	    	<#if page - 1 &gt; 0><a data-pjax href="${base}${action}/${page - 1}">${page - 1}</a></#if>
	   	 	<a data-pjax class="z-crt">${page}</a>
	    	<#if page + 1 &lt; pageCount><a data-pjax href="${base}${action}/${page + 1}">${page + 1}</a></#if>
	    	<#if page + 2 &lt; pageCount><a data-pjax href="${base}${action}/${page + 2}">${page + 2}</a></#if>
	    </#if>
	    <a data-pjax <#if page &lt; pageCount>href="${base}${action}/${page + 1}"<#elseif page = pageCount>href="${base}${action}/${page}"</#if>" class="last pagenxt"><span class="pagearr">&gt;</span></a>
	    <a data-pjax href="${base}${action}/${pageCount}" class="last pagenxt"><span class="pagearr">&nbsp;Last&nbsp;</span></a>
	</div>
-->

<div class="page">
<#if pagination.hasPreviousPage()>
<a data-pjax href="${base}${action}/1">&lt;&lt;</a>
<a data-pjax href="${base}${action}/${page - 1}">&lt;</a>
<#else>
<span>&lt;&lt;</span>
<span>&lt;</span>
</#if>
<#if pagination.totalPages <= 7>
	<#list 1 .. pagination.totalPages as index>
		<#if index == page >
			<span class="disabled">${index}</span>
		<#else>
			<a data-pjax href="${base}${action}/${index}">${index}</a>
		</#if>
	</#list>
<#elseif  page <= 4>
	<#list 1 .. 5 as index>
		<#if index == page >
			<span class="cur">${index}</span>
		<#else>
			<a data-pjax href="${base}${action}/${index}">${index}</a>
		</#if>
	</#list>
	...
	<a data-pjax href="${base}${action}/${pagination.totalPages}">${pagination.totalPages}</a>
<#else>
	<a data-pjax href="${base}${action}/1">1</a>
	...
	<#if (pagination.totalPages - page)< 4>
		<#list (pagination.totalPages-4) .. pagination.totalPages as index>
			<#if index == page >
				<span class="cur">${index}</span>
			<#else>
				<a data-pjax href="${base}${action}/${index}">${index}</a>
			</#if>
		</#list>
	<#else>
		<a data-pjax href="${base}${action}/${page-1}">${page-1}</a>
		<span class="cur">${page}</span>
		<a data-pjax href="${base}${action}/${page+1}" >${page+1}</a>
		...
		<a data-pjax href="${base}${action}/${pagination.totalPages}">${pagination.totalPages}</a>
	</#if>
</#if>
<#if pagination.hasNextPage()>
<a data-pjax href="${base}${action}/${page + 1}">&gt;</a>
<a data-pjax href="${base}${action}/${pagination.totalPages}">&gt;&gt;</a>
<#else>
<span>&gt;</span>
<span>&gt;&gt;</span>
</#if>
		共${pagination.totalElements}条 当前${page}/${pagination.totalPages}页 到第<input data-origin="${page}" class="jumpPage" style="text-align:center;font-size:11px;" type="text" value="${page}"/>页<a class="jumpPageLink" data-pjax data-base="${base}${action}/" href="${base}${action}/${page}">跳转</a>
</div>

</#if>
</#macro>