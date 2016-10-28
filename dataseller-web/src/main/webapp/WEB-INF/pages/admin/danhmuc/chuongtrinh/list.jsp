<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="dmchuongtrinh.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="dmchuongtrinh.heading_page" />"/>
</head>
<c:url var="formUrl" value="/admin/danhmucchuongtrinh.html"/>
<c:url var="addUrl" value="/admin/danhmucchuongtrinh/themmoi.html"/>
<c:url var="editUrl" value="/admin/danhmucchuongtrinh/chinhsua.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="dmchuongtrinh.heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">
        <div class="panel-heading">
            <c:if test="${!empty messageResponse}">
                <div class="span12">
                    <div class="alert alert-${alertType}">
                        <a class="close" data-dismiss="alert" href="#">&times;</a>
                            ${messageResponse}
                    </div>
                </div>
            </c:if>
        </div>
        <div class="clear"></div>
        <div class="panel-heading">
            <div class="panel-btns">
                <a class="btn btn-primary" href="${addUrl}"> <fmt:message key="button.add"/></a>
            </div>
            <h4 class="panel-title">Danh mục chương trình</h4>
        </div>
        <div class="panel-body panel-body-nopadding">
            <div class="row-fluid report-filter">
                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                               partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                               id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                    <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 10%;" >
                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                    </display:column>
                    <display:column headerClass="table_header_center nowrap" sortable="true" sortName="code" property="code" titleKey="dmchuongtrinh.label.code" class="text-center" style="width: 5%;" />
                    <display:column headerClass="table_header_center nowrap" sortable="true" sortName="description" property="description" titleKey="dmchuongtrinh.label.description" class="text-center nowrap" style="width: 35%;" />
                    <display:column headerClass="table_header_center nowrap" sortable="true" sortName="description" property="dbLinkName" titleKey="dmchuongtrinh.label.db_link" class="text-center nowrap" style="width: 15%;" />
                    <display:column headerClass="table_header_center nowrap" sortable="true" sortName="createdDate" titleKey="label.ngay_tao" class="text-center nowrap" style="width: 10%px;" >
                        <fmt:formatDate value="${tableList.createdDate}" pattern="${datePattern}" />
                    </display:column>
                    <display:column headerClass="table_header_center nowrap" sortable="true" sortName="modifiedDate" titleKey="label.ngay_sua" class="text-center nowrap" style="width: 10%px;" >
                        <fmt:formatDate value="${tableList.modifiedDate}" pattern="${datePattern}" />
                    </display:column>
                    <display:column headerClass="table_header_center nowrap" sortable="true" sortName="modifiedDate" titleKey="label.action" class="text-center nowrap" style="width: 10%px;" >
                        <a class="tip-top" href="${editUrl}?pojo.chuongTrinhId=${tableList.chuongTrinhId}"><fmt:message key="label.edit" /></a>
                        |<a class="tip-top" onclick="javascript: deleteChuongTrinh(${tableList.chuongTrinhId});"><fmt:message key="label.delete" /></a>
                    </display:column>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.chuong_trinh"/></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.chuong_trinh"/></display:setProperty>
                </display:table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#dmChuongTrinh');
    });

    function deleteChuongTrinh(chuongTrinhId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${editUrl}?pojo.chuongTrinhId=' +chuongTrinhId+ '&crudaction=delete';

            }
        });
    }
</script>
</body>
</html>