<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="admin.user_list.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.user_list.heading_page" />"/>
</head>

<c:url var="formUrl" value="/admin/user/list.html"/>
<c:url var="viewUrl" value="/admin/user/view.html"/>
<c:url var="editUrl" value="/admin/user/edit.html"/>
<c:url var="addUrl" value="/admin/user/add.html"/>

<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.user_list.heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="panel-btns">
                <a class="btn btn-primary" style="margin-top:-10px" href="${addUrl}"> <fmt:message key="label.button.them"/></a>
            </div>
            <h4 class="panel-title"><fmt:message key="admin.user_list.heading_page" /></h4>
        </div>
    <div class="panel-body panel-body-nopadding">
        <c:if test ="${not empty messageResponse}">
            <div class="alert alert-${alertType}">
                <a class="close" data-dismiss="alert" href="#">&times;</a>
                    ${messageResponse}
            </div>
        </c:if>
        <div class="clear"></div>
        <div class="row-fluid report-filter">
            <form:form commandName="item" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm">
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-4 control-label"><fmt:message key="user.label.usergroup" /></label>
                                <div class="col-sm-8">
                                    <form:select id="userGroupMenu" path="pojo.userGroup.userGroupId" >
                                        <option value=""><fmt:message key="label.all" /></option>
                                        <c:forEach items="${userGroupList}" var="userGroup">
                                            <option <c:if test="${item.pojo.userGroup.userGroupId eq userGroup.userGroupId}">selected="true"</c:if> value="${userGroup.userGroupId}">${userGroup.name}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Tên đăng nhập</label>
                                <div class="col-sm-8">
                                    <form:input path="pojo.userName" cssClass="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Họ và tên</label>
                                <div class="col-sm-8">
                                    <form:input path="pojo.displayName" cssClass="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="form-group">
                                <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                <a class="btn btn-info" onclick="javacsript: resetForm();" ><fmt:message key="label.reset" /></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="table-responsive">
                    <div id="tableList_wrapper" class="dataTables_wrapper" role="grid" >
                        <div class="clear"></div>
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formlUrl}"
                                           partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                           id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em;">

                                <display:column headerClass="table_header" sortable="false" titleKey="label.stt" >
                                    ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header" property="userName" sortName="userName" sortable="true" titleKey="user.label.username" style="20%"/>
                                <display:column headerClass="table_header" property="displayName" sortName="displayName" sortable="true" titleKey="user.label.displayName" style="20%"/>
                                <display:column headerClass="table_header_center" property="userGroup.name" sortable="false" class="text-center" titleKey="user.label.usergroup" />
                                <display:column headerClass="table_header_center nowrap" sortName="status" sortable="true" class="text-center" titleKey="label.status" style="10%">
                                    <c:choose>
                                        <c:when test = "${tableList.status eq 1}">
                                            <ftm:message key="label.active" />
                                        </c:when>
                                        <c:otherwise>
                                            <ftm:message key="label.inactive" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column headerClass="table_header_center" class="text-center" titleKey="label.action" style="width:10%;">
                                    <a href="${editUrl}?pojo.userId=${tableList.userId}" class="tip-top" title="Sửa"><i class="icon-edit">Sửa</i></a>
                                    | <a class="tip-top" onclick="javascript: deleteUser(${tableList.userId});"><ftm:message key="label.delete" /></a>
                                </display:column>
                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.nguoi_dung" /></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.nguoi_dung" /></display:setProperty>
                            </display:table>
                            <input type="hidden" name="crudaction" value="find"/>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        // active tab
        setActiveMenu4Admin('#dmNguoiDungTab');

        $(".deleteLink").click(function(){
            var id = $(this).attr("id");
            bootbox.confirm('<fmt:message key="delete.confirm.message.title"/>', '<fmt:message key="delete.confirm.message.content"/>', function(r) {
                if(r){
                    if(id != null && id != undefined){
                        $("<input type='hidden' name='checkList' value='"+id+"'>").appendTo($("#listForm"));
                        $("#crudaction").val("delete");
                        $("#listForm").submit();
                    }
                }
            });
        });
    });

    function submitForm(){
        $('#listForm').submit();
    }

    function resetForm(){
        $("input").val('');
        selectFirstItemSelect2('#userGroupMenu');
    }

    function deleteUser(userId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.userId=' + userId + '&crudaction=delete';
            }
        });
    }
</script>