<%--
  Created by IntelliJ IDEA.
  User: vovanphuc0810
  Date: 2/25/15
  Time: 9:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="td" uri="http://displaytag.sf.net" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:url var="formlUrl" value="/admin/thuebaophattrienmoi/danhmucgoicuoc.html"/>
<head>
    <title><fmt:message key="danhmucgoicuoc.heading_page"/></title>
    <meta name="heading" content="<fmt:message key="danhmucgoicuoc.heading_page"/>"/>
</head>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i> <fmt:message key="danhmucgoicuoc.heading_page"/></h2>
</div>
<div class="contentpanel">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title"><fmt:message key="danhmucgoicuoc.heading_page"/></h4>
        </div>
        <div class="panel-body panel-body-nopadding">

            <div class="clear"></div>
            <div class="table-responsive">
                <div id="table2_wrapper" class="dataTables_wrapper" role="grid">
                    <display:table name="items.listResult" cellspacing="0" cellpadding="0"
                                   requestURI="${formlUrl}"
                                   partialList="true" sort="external" size="${items.totalItems}" defaultsort="1"
                                   id="tableList" pagesize="${items.maxPageItems}" export="false"
                                   class="tableVms table-hover" style="margin: 1em 0 1.5em;">

                        <display:column headerClass="table_header_center" sortable="false" class="text-center"
                                        titleKey="label.stt" style="width: 5%;">
                            ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                        </display:column>
                        <display:column headerClass="table_header_center nowrap" property="package_Name"
                                        titleKey="Tên gói cước" style="width: 45%"/>
                        <display:column headerClass="table_header_center nowrap" property="package_Code"
                                        class="nowrap text-center" titleKey="Mã gói cước" style="width: 25%"/>
                        <display:column headerClass="table_header_center nowrap" titleKey="Đơn giá" style="width: 25%" class="nowrap text-right">
                            <fmt:formatNumber type="number" value="${tableList.prom_Amount}"
                                              maxFractionDigits="2"/>

                        </display:column>
                        <display:setProperty name="paging.banner.items_name" value="gói cước"/>
                    </display:table>
                    <%--<form:hidden path="crudaction" id="crudaction"/>--%>
                </div>
            </div>
        </div>
    </div>
</div>

<script language="javascript" type="text/javascript">
    $(document).ready(function () {
        // active tab
        setActiveMenu4Admin('#dmGoiCuocTab');
    });
</script>
</body>