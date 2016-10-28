<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="admin.user_list.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.user_list.heading_page" />"/>
</head>
<c:url var="formUrl" value="/admin/userList.html"/>
<c:url var="viewUrl" value="/admin/user/view.html"/>
<c:url var="editUrl" value="/admin/editUserInfo.html"/>
<c:url var="importUrl" value="/admin/importUserFile.html"/>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.user_list.heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="panel-btns">
                <a class="btn btn-primary" style="margin-top:-10px" href="${importUrl}"> <fmt:message key="label.button.import"/></a>
                <a class="btn btn-primary" style="margin-top:-10px" href="${editUrl}"> <fmt:message key="label.button.them"/></a>
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
                                <label class="col-sm-4 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                <div class="col-sm-8">
                                    <form:select id="chiNhanhMenu" path="pojo.chiNhanhId" onchange="javascript: ajaxGetDepartmentList();" >
                                        <option value=""><fmt:message key="label.all" /></option>
                                        <c:forEach items="${chiNhanhList}" var="chiNhanhVar">
                                            <option <c:if test="${item.pojo.chiNhanhId eq chiNhanhVar.chiNhanhId}">selected="true"</c:if> value="${chiNhanhVar.chiNhanhId}">${chiNhanhVar.name}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-4 control-label"><fmt:message key="label.ten_cua_hang" /></label>
                                <div class="col-sm-8">
                                    <form:select id="cuaHangMenu" path="pojo.department.departmentId">
                                        <option value=""><fmt:message key="label.all" /></option>
                                        <c:forEach items="${departmentList}" var="cuaHang">
                                            <option <c:if test="${item.pojo.department.departmentId eq cuaHang.departmentId}">selected="true"</c:if> value="${cuaHang.departmentId}">${cuaHang.name}</option>
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
                                <button type="reset" class="btn btn-info"><fmt:message key="label.reset" /></button>
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
                                <display:column headerClass="table_header" property="tenChiNhanh" sortable="false" titleKey="label.chi_nhanh" class="nowrap" style="20%"/>
                                <display:column headerClass="table_header" property="department.name" sortable="false" titleKey="label.cua_hang" style="20%"/>
                                <display:column headerClass="table_header" property="userName" sortName="userName" sortable="true" titleKey="label.username" style="20%"/>
                                <display:column headerClass="table_header" property="displayName" sortName="displayName" sortable="true" titleKey="label.fullname" style="20%"/>
                                <display:column headerClass="table_header_center" property="mobileNumber" sortName="mobileNumber" sortable="true" titleKey="label.so_dt" style="20%"/>
                                <display:column headerClass="table_header_center" property="userGroup.name" sortable="false" class="text-center" titleKey="label.usergroup" />
                                <display:column headerClass="table_header_center nowrap" sortName="status" sortable="true" class="text-center" titleKey="label.status" style="10%">
                                    <c:choose>
                                        <c:when test = "${tableList.status eq 1}">
                                            Hoạt động
                                        </c:when>
                                        <c:otherwise>
                                            Không hoạt động
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

    function deleteUser(userId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.userId=' + userId + '&crudaction=delete';
            }
        });
    }

    function ajaxGetDepartmentList(){
        $('#cuaHangMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#cuaHangMenu');
        if($('#chiNhanhMenu').val() != ''){
            $.ajax({
                url: '<c:url value="/ajax/cuahangmobifone/getDeprtmentListByChiNhanh.html" />',
                type: 'get',
                dataType: 'json',
                data: {chiNhanh: $('#chiNhanhMenu').val()},
                success: function(res){
                    if(res.departmentList != null){
                        $(res.departmentList).each(function(index, el){
                            $('#cuaHangMenu').append("<option value=" +el.departmentId+ ">" +el.name+ "</option>");
                        });
                    }
                }
            });
        }
    }
</script>