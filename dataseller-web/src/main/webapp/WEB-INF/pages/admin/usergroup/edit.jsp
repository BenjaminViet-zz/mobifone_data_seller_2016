<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="admin.edit_user_group.edit.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.edit_user_group.edit.heading_page" />"/>
</head>
<c:url var="backUrl" value="/admin/usergroup/list.html"/>
<c:url var="formUrl" value="/admin/usergroup/add.html"/>

<c:if test="${pojo.userGroupId != null}">
    <c:url var="formUrl" value="/admin/usergroup/edit.html"/>
</c:if>

<div class="page-title">
    <div class="title_left">
        <h3>
            <c:choose>
                <c:when test="${not empty item.pojo.userGroupId}">
                    <fmt:message key="admin.edit_user_group.edit.heading_page" />
                </c:when>
                <c:otherwise><fmt:message key="admin.edit_user_group.label.add_usergroup" /></c:otherwise>
            </c:choose>
        </h3>
    </div>
</div>
<div class="clearfix"></div>

<c:if test="${!empty messageResponse}">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_content">
                    <div class="alert alert-${alertType} no-bottom">
                        <a class="close" data-dismiss="alert" href="#">&times;</a>
                        ${messageResponse}
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="formEdit" action="${formUrl}" method="post" validate="validate">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="code">
                            <fmt:message key="label.full_name" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="code" path="pojo.code" cssClass="required nohtml form-control"></form:input>
                            <form:errors for="code" path="pojo.code" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">
                            <fmt:message key="label.user_name" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="description" path="pojo.description" cssClass="required nohtml form-control"></form:input>
                            <form:errors for="description" path="pojo.description" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">
                            <fmt:message key="permission.list" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <c:if test="${permissionDTOList.size() > 0}">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th><fmt:message key="permission.group_name" /></th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:set var="lastPermissionGroupNumber" value="${-1}" />
                                    <c:forEach items="${permissionDTOList}" var="permision" varStatus="status">
                                        <c:if test="${lastPermissionGroupNumber == -1}">
                                            <tr>
                                            <td><fmt:message key="permission.group.${permision.permissionGroupNumber}" /></td>
                                            <td>${permision.description}
                                        </c:if>
                                        <c:if test="${lastPermissionGroupNumber != permision.permissionGroupNumber}">
                                            </td>
                                            </tr>
                                            <tr>
                                            <td><fmt:message key="permission.group.${permision.permissionGroupNumber}" /></td>
                                            <td>${permision.description}
                                        </c:if>
                                        <c:if test="${lastPermissionGroupNumber == permision.permissionGroupNumber}">
                                            ${permision.description}
                                        </c:if>
                                        <c:if test="${status.count == permissionDTOList.size()}">
                                            </tr>
                                        </c:if>
                                        <c:set var="lastPermissionGroupNumber" value="${permision.permissionGroupNumber}" />
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-info"><fmt:message key="label.huy" /></a>&nbsp;
                            <button id="btnSave" class="btn btn-primary">
                                <c:choose>
                                    <c:when test="${not empty item.pojo.userGroupId}"><fmt:message key="label.update" /></c:when>
                                    <c:otherwise><fmt:message key="label.save" /></c:otherwise>
                                </c:choose>
                            </button>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden path="pojo.userGroupId" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $("#btnSave").click(function(){
            if($('#formEdit').valid()){
                $("#formEdit").submit();
            }
        });
    });
</script>