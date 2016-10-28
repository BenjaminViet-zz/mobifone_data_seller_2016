<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="chinhanh.danh_muc_chi_nhanh.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="chinhanh.danh_muc_chi_nhanh.heading_page" />"/>
</head>
<c:url var="formUrl" value="/admin/danhmucchinhanh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="chinhanh.danh_muc_chi_nhanh.heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">
        <div class="panel-body panel-body-nopadding">
            <div class="row-fluid report-filter">
                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                               partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 10%;" >
                        ${tableList_rowNum}
                    </display:column>
                    <display:column headerClass="table_header_center nowrap" sortable="true" sortName="chiNhanh" property="chiNhanh" titleKey="label.chi_nhanh" class="text-center" style="width: 10%;" />
                    <display:column headerClass="table_header_center nowrap" sortable="true" sortName="name" property="name" titleKey="label.ten_chi_nhanh" class="text-center nowrap" style="width: 50%;" />
                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.action" class="text-center nowrap" style="width: 20%px;" >
                        <a href="<c:url value="/admin/thongtinchinhanh.html" />?pojo.chiNhanhId=${tableList.chiNhanhId}"><fmt:message key="label.edit" /></a>
                    </display:column>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.chi_nhanh"/></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.chi_nhanh"/></display:setProperty>
                </display:table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#dmChiNhanhTab');
    });
</script>
</body>
</html>