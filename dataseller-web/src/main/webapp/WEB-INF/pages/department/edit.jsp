<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title>
        <c:choose>
            <c:when test="${not empty item.pojo.departmentId}">
                <fmt:message key="admin.edit_cua_hang.heading_page" />
            </c:when>
            <c:otherwise><fmt:message key="admin.add_cua_hang.heading_page" /></c:otherwise>
        </c:choose>
    </title>
    <c:choose>
        <c:when test="${not empty item.pojo.departmentId}">
            <meta name="menu" content="<fmt:message key="admin.edit_cua_hang.heading_page"/>"/>
        </c:when>
        <c:otherwise>
            <meta name="menu" content="<fmt:message key="admin.add_cua_hang.heading_page"/>"/>
        </c:otherwise>
    </c:choose>
    <style>
        input{
            width: 220px;
        }
    </style>
</head>
<c:url var="backUrl" value="/admin/danhmuccuahang.html"/>
<c:url var="formUrl" value="/admin/thongtincuahang.html"/>

<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.thong_tin_cua_hang.label" /></h2>

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
        <br />
        <div class="panel-body panel-body-nopadding">
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="formEdit" action="${formUrl}" method="post" validate="validate">
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Mã cửa hàng <span style="color:red;">*</span></label>
                                    <div class="col-sm-8">
                                        <form:input id="code" path="pojo.code" cssClass="required  form-control nohtml"></form:input>
                                        <form:errors for="code" path="pojo.code" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Tên cửa hàng <span style="color:red;">*</span></label>
                                    <div class="col-sm-8">
                                        <form:input id="name" path="pojo.name" cssClass="required nohtml form-control"></form:input>
                                        <form:errors for="name" path="pojo.name" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Địa chỉ <span style="color:red;">*</span></label>
                                    <div class="col-sm-8">
                                        <form:input id="address" path="pojo.address" cssClass="required nohtml form-control"></form:input>
                                        <form:errors for="address" path="pojo.address" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Điện thoại</label>
                                    <div class="col-sm-8">
                                        <form:input id="tel" path="pojo.tel" cssClass="nohtml validate_phone_number form-control"></form:input>
                                        <form:errors for="tel" path="pojo.tel" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Liên hệ</label>
                                    <div class="col-sm-8">
                                        <form:input id="contactName" path="pojo.contactName" cssClass="nohtml form-control"></form:input>
                                        <form:errors for="contactName" path="pojo.contactName" cssClass="error-inline-validate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="chiNhanhMenu" path="pojo.chiNhanhId" cssStyle="width: 250px;" onchange="javascript: ajaxGetDepartmentList();">
                                            <c:forEach items="${chiNhanhList}" var="chiNhanhVar">
                                                <option <c:if test="${item.pojo.chiNhanhId eq chiNhanhVar.chiNhanhId}">selected="true"</c:if> value="${chiNhanhVar.chiNhanhId}">${chiNhanhVar.name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-8">
                                        <a id="btnSave" class="btn btn-primary"><fmt:message key="label.update" /></a>&nbsp;
                                        <a href="<c:url value="/admin/danhmuccuahang.html"/>" class="btn btn-info"><fmt:message key="label.huy" /></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <form:hidden path="pojo.departmentId"/>
                    <input type="hidden" name="crudaction" value="insert-update" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        // active tab
        setActiveMenu4Admin('#menuDanhMucTab', '#dmCuaHang');

        $("#btnSave").click(function(){
            if($('#formEdit').valid()){
                bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                    if(r){
                        $("#formEdit").submit();
                    }
                });
            }
        });

        jQuery.validator.addClassRules('validate_phone_number', {
            validatePhoneNumber: true
        });

        jQuery.validator.addMethod("validatePhoneNumber", function () {
            return validatePhoneNumber($.trim($('#tel').val()));
        }, "<fmt:message key="user.msg.invalid_phone_number"/>");
    });
</script>
</body>