<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="admin.edit_user_group.list.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.edit_user_group.list.heading_page" />"/>
</head>

<c:url var="formUrl" value="/admin/usergroup/list.html"/>
<c:url var="editUrl" value="/admin/usergroup/edit.html"/>
<c:url var="addUrl" value="/admin/usergroup/add.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.edit_user_group.list.heading_page" /></h3>
    </div>

    <div class="title_right">
        <div class="action-bar">
            <a class="btn btn-primary" href="${addUrl}"> <fmt:message key="label.button.them"/></a>
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
                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                               id="tableList" pagesize="${items.maxPageItems}" export="false"
                               class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">

                    <display:column headerClass="table_header text-center" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                    </display:column>
                    <display:column headerClass="table_header text-center" property="code" sortName="userName" sortable="true" titleKey="usergroup.label.code" style="20%"/>
                    <display:column headerClass="table_header text-center" property="description" sortName="displayName" sortable="false" titleKey="usergroup.label.description" style="60%"/>
                    <display:column headerClass="table_header  text-center" class="text-center" titleKey="label.action" style="width:15%;">
                        <a href="${editUrl}?pojo.userGroupId=${tableList.userGroupId}" class="tip-top" title="<fmt:message key="label.edit" />"><fmt:message key="label.edit" /></a>
                        | <a class="tip-top" onclick="javascript: deleteUserGroup(${tableList.userGroupId});"><fmt:message key="label.delete" /></a>
                    </display:column>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.nguoi_dung" /></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.nguoi_dung" /></display:setProperty>
                </display:table>
                <input type="hidden" name="crudaction" value="find"/>
            </div>
        </div>
    </div>
</div>
<script language="javascript" type="text/javascript">
    function deleteUserGroup(userGroupId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.userGroupId=' + userGroupId + '&crudaction=delete';
            }
        });
    }
</script>