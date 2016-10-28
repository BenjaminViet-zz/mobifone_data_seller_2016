<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="cuahanggiaodich.xuat_kho_heading_page" /></title>
    <meta name="heading" content="<fmt:message key="cuahanggiaodich.xuat_kho_heading_page" />"/>
</head>
<c:url var="formUrl" value="/cuahangmobifone/xuatkho.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="cuahanggiaodich.xuat_kho_heading_page" /></h2>

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
                    <div class="col-sm-8">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.so_luong_trong_kho" /></label>
                                    <div class="col-sm-8"><span class="text-form">${inventoryTotal}</span></div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.so_luong_xuat" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="quantity" path="pojo.quantity" cssClass="nohtml required onlyNumberInputForceValue form-control" />
                                        <form:errors for="quantity" path="pojo.quantity" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <security:authorize ifAnyGranted="NHANVIEN">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.xuat_kho" /></a>
                                    </div>
                                </div>
                            </security:authorize>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#tdcg2014XuatKhoTab');
    });

    function submitForm(){
        if($('#stockForm').valid()){
            if(validateSoLuongXuatKho()){
                bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />'
                        , '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                            if(r){
                                trimAndSubmitForm('#stockForm');
                            }
                        });
            }else{
                bootbox.alert('<fmt:message key="label.alert_title" />', '<fmt:message key="cuahanggiaodich.xuat_kho.msg.not_enough_stock_inventory" />', '<fmt:message key="label.dong_y" />', function(){});
            }
        }
    }

    function validateSoLuongXuatKho(){
        var result = true,
            soLuongCanXuat = $.trim($('#quantity').val()) != '' ? eval($.trim($('#quantity').val())) : 0,
            soLuongConTrongKho = ${inventoryTotal};

        if(soLuongCanXuat > soLuongConTrongKho){
            result = false;
        }
        return result;
    }
</script>
</body>
</html>