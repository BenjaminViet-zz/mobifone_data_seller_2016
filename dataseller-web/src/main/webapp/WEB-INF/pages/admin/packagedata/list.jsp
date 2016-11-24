<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<head>
    <title><fmt:message key="packagedata.list.heading" /></title>
    <meta name="menu" content="<fmt:message key="packagedata.list.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize access="hasAnyAuthority('KHDN')">
    <c:set var="prefix" value="/khdn" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/package_data/list.html"/>
<c:url var="editUrl" value="${prefix}/package_data/edit.html"/>
<c:url var="addUrl" value="${prefix}/package_data/add.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.data_code_list.heading_page" /></h3>
    </div>

    <div class="title_right">
        <div class="action-bar">
            <a class="btn btn-primary" href="${addUrl}"><i class="fa fa-plus" aria-hidden="true"></i> <fmt:message key="label.button.them"/></a>
        </div>
    </div>
</div>
<div class="clearfix"></div>
<c:if test ="${not empty messageResponse}">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_content">
                    <div class="alert alert-${alertType} no-bottom">
                        <a class="close" data-dismiss="alert" href="#">&times;</a>
                            ${messageResponse}
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <div id="tableListContainer" style="width: 100%;">
                    <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                   partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                   id="tableList" pagesize="${items.maxPageItems}" export="false"
                                   class="table table-striped table-bordered" style="margin: 1em 0 1.5em; width: 1550px;">

                        <display:column headerClass="table_header text-center vertical-middle" titleKey="label.stt" class="text-center" style="width: 50px;" >
                            ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                        </display:column>
                        <display:column headerClass="table_header text-center vertical-middle" property="name" sortName="name" class="text-center" titleKey="packagedata.label.tenGoiCuoc" style="width: 200px;"/>
                        <display:column headerClass="table_header text-center vertical-middle" sortName="value" class="text-center" titleKey="packagedata.label.giaGoiCuoc" style="width: 150px;">
                            <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.value}" />
                        </display:column>
                        <display:column headerClass="table_header text-center vertical-middle" property="customPrefixUnitPrice" sortable="false" class="text-center" titleKey="packagedata.label.prefixCardCode" style="width: 150px;" />
                        <display:column headerClass="table_header text-center vertical-middle" property="volume" sortable="false" class="text-center" titleKey="packagedata.label.dungLuongMienPhi" style="width: 350px;" />
                        <display:column headerClass="table_header text-center vertical-middle" property="durationText" sortable="false" class="text-center" titleKey="packagedata.label.thoiGianSuDung" style="width: 150px;" />
                        <display:column headerClass="table_header text-center vertical-middle" property="numberOfExtend" sortable="false" class="text-center" titleKey="packagedata.label.soLanGiaHan" style="width: 150px;" />
                        <display:column headerClass="table_header text-center vertical-middle" property="tk" sortable="false" class="text-center" titleKey="packagedata.label.tk" style="width: 150px;" />
                        <display:column headerClass="table_header text-center vertical-middle" class="text-center" titleKey="label.action" style="width: 200px;">
                            <a href="${editUrl}?pojo.packageDataId=${tableList.packageDataId}" class="tip-top" title="<fmt:message key="label.edit" />"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                            <c:if test="${tableList.generatedCardCode == false}">
                                | <a class="tip-top action-group" title="<fmt:message key="label.delete" />" onclick="javascript: deletePackageData(${tableList.packageDataId});"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
                            </c:if>
                        </display:column>
                        <display:setProperty name="paging.banner.item_name"><fmt:message key="packagedata.label.package_item" /></display:setProperty>
                        <display:setProperty name="paging.banner.items_name"><fmt:message key="packagedata.label.package_item" /></display:setProperty>
                    </display:table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        replaceSpecialCharacter();
        initScrollablePane();
    });

    function initScrollablePane(){
        $('#tableListContainer').mCustomScrollbar({axis:"x"});
    }

    function replaceSpecialCharacter() {
        var str = $('#tableList').text();
        var textToReplace = '{delimiter_line}';
        var replaceWith = '<span class="line_separator"></span>'
        if (str.indexOf(textToReplace) == -1) {

        } else {
            $("td:contains(" + textToReplace + ")").addClass('breakTwoLines');
            var newString = $('td.breakTwoLines').text();
            var result = newString.replace(textToReplace, replaceWith);
            $('td.breakTwoLines').html(result);
        }
    }

    function resetForm(){
        $("input[type='text']").val('');
    }

    function deletePackageData(packageDataId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.packageDataId=' + packageDataId + '&crudaction=<%=Constants.ACTION_DELETE%>';
            }
        });
    }
</script>