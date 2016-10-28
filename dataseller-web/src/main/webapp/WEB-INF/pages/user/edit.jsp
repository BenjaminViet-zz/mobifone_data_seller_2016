<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="admin.edit_user.heading_page" /></title>
    <meta name="menu" content="<fmt:message key="admin.edit_user.heading_page" />"/>
</head>
<c:url var="backUrl" value="/admin/userList.html"/>
<c:url var="formUrl" value="/admin/editUserInfo.html"/>

<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.edit_user.heading_page" /></h2>

</div>
<div class="contentpanel">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <c:choose>
                    <c:when test="${not empty item.pojo.userId}">
                        <fmt:message key="admin.edit_user.label.edit_user" />
                    </c:when>
                    <c:otherwise><fmt:message key="admin.edit_user.label.add_user" /></c:otherwise>
                </c:choose>
            </h4>
            <c:if test="${!empty messageResponse}">
                <div class="span12">
                    <div class="alert alert-${alertType}">
                        <a class="close" data-dismiss="alert" href="#">&times;</a>
                        ${messageResponse}
                    </div>
                </div>
            </c:if>
        </div>
        <br />
        <div class="panel-body panel-body-nopadding">
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="formEdit" action="${formUrl}" method="post" validate="validate">
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.full_name" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="displayName" path="pojo.displayName" cssClass="required nohtml form-control"></form:input>
                                        <form:errors for="displayName" path="pojo.displayName" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.user_name" /><span style="color:red;">*</span></label>
                                    <div class="col-sm-8">
                                        <form:input id="userName" path="pojo.userName" cssClass="required nohtml form-control"></form:input>
                                        <form:errors for="userName" path="pojo.userName" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="user.label.password" /><span style="color:red;">*</span></label>
                                    <div class="col-sm-8">
                                        <form:password id="password" path="pojo.password" cssClass="required nohtml form-control"></form:password>
                                        <span class="add-on-attach" id="btnShowHidePassword">Xem</span>
                                        <form:errors for="password" path="pojo.password" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="user.label.email" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="email" path="pojo.email" cssClass=" form-control nohtml"></form:input>
                                        <form:errors for="email" path="pojo.email" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.mobile" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="phoneNumber" path="pojo.mobileNumber" cssClass="nohtml validate_phone_number form-control"></form:input>
                                        <form:errors for="phoneNumber" path="pojo.mobileNumber" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="chiNhanhMenu" path="pojo.chiNhanh.chiNhanhId" cssStyle="width: 250px;" onchange="javascript: ajaxGetDepartmentList();">
                                            <form:option value=""><fmt:message key="label.select" /></form:option>
                                            <c:forEach items="${chiNhanhList}" var="chiNhanhVar">
                                                <option <c:if test="${chiNhanhVar.chiNhanhId eq item.pojo.chiNhanh.chiNhanhId}">selected="true"</c:if> value="${chiNhanhVar.chiNhanhId}">${chiNhanhVar.name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ten_cua_hang" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="cuaHangMenu" path="pojo.department.departmentId" cssStyle="width: 250px;">
                                            <form:option value=""><fmt:message key="label.select" /></form:option>
                                            <c:forEach items="${listDepartments}" var="department">
                                                <option <c:if test="${department.departmentId eq item.pojo.department.departmentId}">selected="true"</c:if> value="${department.departmentId}">${department.name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="user.label.usergroup" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="userGroup" path="pojo.userGroup.userGroupId" cssClass="required" cssStyle="width: 250px;">
                                            <form:option value=""><fmt:message key="label.select" /></form:option>
                                            <c:forEach items="${userGroups}" var="userGroup">
                                                <option <c:if test="${userGroup.userGroupId eq item.pojo.userGroup.userGroupId}">selected="true"</c:if> value="${userGroup.userGroupId}">${userGroup.name}</option>
                                            </c:forEach>
                                        </form:select>
                                        <form:errors path="pojo.userGroup.userGroupId" for="userGroup" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.status" /></label>
                                    <div class="col-sm-8">
                                        <form:select path="pojo.status" cssStyle="width: 150px;">
                                            <option <c:if test="${1 eq item.pojo.status}">selected="true"</c:if> value="1"><fmt:message key="label.active" /></option>
                                            <option <c:if test="${0 eq item.pojo.status}">selected="true"</c:if> value="0"><fmt:message key="label.inactive" /></option>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden path="pojo.userId" />
                </form:form>
            </div>
        </div>
        <br />
        <div class="panel-footer">
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <button id="btnSave" class="btn btn-primary">
                        <c:choose>
                            <c:when test="${not empty item.pojo.userId}"><fmt:message key="label.update" /></c:when>
                            <c:otherwise><fmt:message key="label.save" /></c:otherwise>
                        </c:choose>
                    </button>&nbsp;
                    <a href="<c:url value="/admin/userList.html"/>" class="btn btn-info"><fmt:message key="label.huy" /></a>
                </div>
            </div>
        </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        // active tab
        setActiveMenu4Admin('#menuDanhMucTab', '#dmNguoiDungTab');

        $('#password').val('${item.pojo.password}');
        $("#btnSave").click(function(){
            if($('#formEdit').valid()){
                $("#formEdit").submit();
            }
        });

        jQuery.validator.addClassRules('validate_phone_number', {
            validatePhoneNumber: true
        });

        jQuery.validator.addMethod("validatePhoneNumber", function () {
            return validatePhoneNumber($.trim($('#phoneNumber').val()));
        }, "<fmt:message key="user.msg.invalid_phone_number"/>");
    });

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
</body>