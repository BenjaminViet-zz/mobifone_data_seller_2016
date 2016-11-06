<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="admin.user_list.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.user_list.heading_page" />"/>
</head>

<c:url var="formUrl" value="/admin/user/list.html"/>
<c:url var="editUrl" value="/admin/user/edit.html"/>
<c:url var="addUrl" value="/admin/user/add.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="admin.user_list.heading_page" /></h3>
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
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userGroupMenu"><fmt:message key="user.label.usergroup" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select cssClass="form-control" id="userGroupMenu" path="pojo.userGroup.userGroupId" >
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${userGroups}" var="userGroup">
                                    <option <c:if test="${item.pojo.userGroup.userGroupId eq userGroup.userGroupId}">selected="true"</c:if> value="${userGroup.userGroupId}">${userGroup.description}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="username"><fmt:message key="label.user_name" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="username" path="pojo.userName" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="displayName"><fmt:message key="label.display_name" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="displayName" path="pojo.displayName" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javacsript: resetForm();" ><fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
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
                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                               id="tableList" pagesize="${items.maxPageItems}" export="false"
                               class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">

                    <display:column headerClass="table_header text-center" titleKey="label.stt" sortable="false" class="text-center" style="width: 5%;" >
                        ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                    </display:column>
                    <display:column headerClass="table_header text-center" property="userName" sortName="userName" sortable="true" titleKey="user.label.username" style="20%"/>
                    <display:column headerClass="table_header text-center" property="displayName" sortName="displayName" sortable="true" titleKey="user.label.displayName" style="25%"/>
                    <display:column headerClass="table_header text-center" property="userGroup.description"  sortable="true" class="" titleKey="user.label.usergroup" style="width: 20%" />
                    <display:column headerClass="table_header text-center nowrap" sortName="status" sortable="true" class="text-center" titleKey="label.status" style="15%">
                        <c:choose>
                            <c:when test = "${tableList.status eq 1}">
                                <fmt:message key="label.active" />
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="label.inactive" />
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <display:column headerClass="table_header  text-center" class="text-center" titleKey="label.action" style="width:15%;">
                        <a href="${editUrl}?pojo.userId=${tableList.userId}" class="tip-top" title="<fmt:message key="label.edit" />"><fmt:message key="label.edit" /></a>
                        | <a class="tip-top" onclick="javascript: deleteUser(${tableList.userId});"><fmt:message key="label.delete" /></a>
                    </display:column>
                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.nguoi_dung" /></display:setProperty>
                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.nguoi_dung" /></display:setProperty>
                </display:table>
            </div>
        </div>
    </div>
</div>
<script language="javascript" type="text/javascript">
    function submitForm(){
        $('#listForm').submit();
    }

    function resetForm(){
        $("input[type='text']").val('');
        selectFirstItemSelect2('#userGroupMenu');
    }

    function deleteUser(userId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.userId=' + userId + '&crudaction=<%=Constants.ACTION_DELETE%>';
            }
        });
    }
</script>