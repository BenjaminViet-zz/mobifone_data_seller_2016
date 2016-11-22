<%--
  Created by IntelliJ IDEA.
  User: thaihoang
  Date: 11/1/2016
  Time: 9:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ taglib prefix="ft" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<head>
    <title><fmt:message key="admin.khdn.label.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.khdn.label.heading_page" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="addUrl" value="${prefix}/khdn/add.html"/>
<c:url var="editUrl" value="${prefix}/khdn/edit.html"/>
<c:url var="formUrl" value="${prefix}/khdn/list.html"/>
<c:url var="importUrl" value="${prefix}/khdn/import.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.khdn.label.heading_page" /></h3>
    </div>

    <div class="title_right">
        <div class="action-bar">
            <a class="btn btn-primary" href="${addUrl}"><i class="fa fa-plus" aria-hidden="true"></i> <fmt:message key="label.button.them"/></a>
            <a class="btn btn-primary" href="${importUrl}"><i class="fa fa-file-excel-o" aria-hidden="true"></i> <fmt:message key="label.button.import"/></a>
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

                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="shopCode"><fmt:message key="admin.khdn.shopCode" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="shopCode" path="pojo.shopCode" cssClass="form-control" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="mst"><fmt:message key="admin.khdn.label.mst" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="mst" path="pojo.mst" cssClass="form-control" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="gpkd"><fmt:message key="admin.khdn.label.gpkd" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="gpkd" path="pojo.gpkd" cssClass="form-control" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="stb_vas"><fmt:message key="admin.khdn.label.stb_vas" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="stb_vas" path="pojo.stb_vas" cssClass="form-control" />
                        </div>
                    </div>

                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();" ><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search" /></a>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="<%=Constants.ACTION_SEARCH%>"/>
                </form:form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <div id="tableListContainer" style="width: 100%;">
                    <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                   partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                   id="tableList" pagesize="${items.maxPageItems}" export="false"
                                   class="table table-striped table-bordered" style="margin: 1em 0 1.5em; width: 1850px;">
                        <display:column headerClass="table_header text-center" sortable="false" class="text-center" titleKey="label.stt" style="width: 50px">${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                        <display:column headerClass="table_header text-center" property="shopCode" sortName="shopCode" sortable="true" titleKey="admin.khdn.shopCode" style="width: 200px;" />
                        <display:column headerClass="table_header text-center" property="name" sortName="name" sortable="true" titleKey="admin.khdn.label.name" style="width: 300px;" />
                        <display:column headerClass="table_header text-center" property="mst" sortName="mst" sortable="true" titleKey="admin.khdn.label.mst" style="width: 300px;" />
                        <display:column headerClass="table_header text-center" property="gpkd" sortName="gpkd" sortable="true" titleKey="admin.khdn.label.gpkd" style="width: 300px;" />
                        <display:column headerClass="table_header text-center" sortable="true" class="text-center" sortName="issuedContractDate" titleKey="admin.khdn.label.issuedContractDate" style="width: 250px;">
                            <fmt:formatDate value="${tableList.issuedContractDate}" pattern="${datePattern}" />
                        </display:column>
                        <display:column headerClass="table_header text-center" property="stb_vas" sortName="stb_vas" sortable="true" class="text-center" titleKey="admin.khdn.label.stb_vas" style="width: 200px;" />
                        <display:column headerClass="table_header text-center" class="text-center" titleKey="label.action" style="width:250px;">
                            <a href="${editUrl}?pojo.KHDNId=${tableList.KHDNId}" class="tip-top action-group" data-toggle="tooltip" title="<fmt:message key="label.edit" />"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                             <a class="tip-top action-group" data-toggle="tooltip" title="<fmt:message key="label.delete" />" onclick="javascript: deleteKHDN(${tableList.KHDNId});"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
                        </display:column>
                        <display:setProperty name="paging.banner.item_name"><fmt:message key="admin.khdn.footer.label.doanhnghiep" /></display:setProperty>
                        <display:setProperty name="paging.banner.items_name"><fmt:message key="admin.khdn.footer.label.doanhnghiep" /></display:setProperty>--%>
                    </display:table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        jQueryMaskNonThousandFormat();
        initScrollablePane();
    });

    function initScrollablePane(){
        $('#tableListContainer').mCustomScrollbar({axis:"x"});
    }

    function resetForm(){
         $("input[type='text']").val('');
         selectFirstItemSelect2('#KHDN');
     }

    function submitForm(){
        $('#listForm').submit();
    }

    function deleteKHDN(userId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.KHDNId=' + userId + '&crudaction=delete';
            }
        });
    }
</script>