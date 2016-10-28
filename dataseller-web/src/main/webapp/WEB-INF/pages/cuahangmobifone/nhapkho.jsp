<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="cuahanggiaodich.nhap_kho_heading_page" /></title>
    <meta name="heading" content="<fmt:message key="cuahanggiaodich.nhap_kho_heading_page" />"/>
</head>
<c:url var="formUrl" value="/cuahangmobifone/nhaphang.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="cuahanggiaodich.nhap_kho_heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">

        <div class="panel-body panel-body-nopadding">
            <c:if test ="${not empty messageResponse}">
                <div class="alert alert-${alertType}">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                    ${messageResponse}
                </div>
            </c:if>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="stockForm" action="${formUrl}" method="post" autocomplete="off" validate="validate">
                    <div class="col-sm-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ngay_vms_giao" /></label>
                                    <div class="col-sm-8"><span class="text-form"><fmt:formatDate value="${ngayGiaoNhanHangTuVMS}" pattern="${datePattern}" /></span></div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ngay_nhap" /></label>
                                    <div class="col-sm-8"><span class="text-form"><fmt:formatDate value="${ngayGiaoNhanHangTuVMS}" pattern="${datePattern}" /></span></div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_luong_ton_kho" /></label>
                                    <div class="col-sm-8"><span class="text-form">${inventoryTotal}</span></div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_luong_nhap" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="quantity" path="pojo.quantity" cssClass="nohtml required onlyNumberInputForceValue form-control" />
                                        <form:errors for="quantity" path="pojo.quantity" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.nguoi_giao_hang" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="tenNguoiGiaoHang" path="pojo.tenNguoiGiaoHang" cssClass="nohtml required form-control" />
                                        <form:errors for="tenNguoiGiaoHang" path="pojo.tenNguoiGiaoHang" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <security:authorize ifAnyGranted="NHANVIEN">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.nhap_kho" /></a>
                                    </div>
                                </div>
                            </security:authorize>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#tdcg2014NhapKhoTab');
    });

    function submitForm(){
        if($('#stockForm').valid()){
            bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />'
                , '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                    if(r){
                        trimAndSubmitForm('#stockForm');
                    }
                });
        }
    }
</script>
</body>
</html>