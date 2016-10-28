<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="cuahanggiaodich.baocaotheokhcn.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="cuahanggiaodich.baocaotheokhcn.heading_page" />"/>
</head>

<c:set var="prefix" value="/cuahangmobifone" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<security:authorize ifAnyGranted="BAOCAO">
    <c:set var="prefix" value="/baocao" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/qstudent/baocaotheokhcn.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="cuahanggiaodich.baocaotheokhcn.heading_page" /></h2>

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
                <form:form commandName="item" id="reportForm" action="${formUrl}" method="post" autocomplete="off" validate="validate">
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_thue_bao" /></label>
                                    <div class="col-sm-8">
                                        <form:input id="soThueBao" path="pojo.giftDeliveryThueBao.thueBao" cssClass="nohtml validate_phone_number form-control" />
                                        <form:errors for="soThueBao" path="pojo.giftDeliveryThueBao.thueBao" cssClass="error-inline-validate" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ngay_giao_qua_tu" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="fromDate" id="fromDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.pojo.fromDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ngay_giao_qua_den" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="toDate" id="toDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.pojo.toDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <div class="col-sm-8">
                                        <a class="btn btn-info" onclick="javascript: filterForm();"><fmt:message key="label.search" /></a>
                                        <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                        <c:if test="${not empty items.listResult}">
                                            <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <hr/>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult}">
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                       id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" sortName="giftDeliveryThueBao.thueBao" property="giftDeliveryThueBao.thueBao" titleKey="label.so_thue_bao" class="text-center" style="width: 10%;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" sortName="ma_phieu" property="ma_phieu" titleKey="label.maPhieu" class="text-center nowrap" style="width: 10%;" />
                            <display:column headerClass="table_header nowrap" sortable="false" property="gift.name" titleKey="lichsugiaonhanhang.label.mat_hang_giao" class="nowrap" style="width: 15%;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" property="quantity" titleKey="lichsugiaonhanhang.label.so_luong_giao" class="text-center" style="width: 5%;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" sortName="giftDeliveryThueBao.deliveryTime" titleKey="cuahanggiaodich.baocaotheokhcn.label.ngay_giao" class="text-center" style="width: 10%;">
                                <fmt:formatDate value="${tableList.giftDeliveryThueBao.deliveryTime}" pattern="${datePattern}" />
                            </display:column>
                            <display:column headerClass="table_header nowrap" sortable="false" sortName="giftDeliveryThueBao.nvGiao.displayName" property="giftDeliveryThueBao.nvGiao.displayName" titleKey="cuahanggiaodich.baocaotheokhcn.label.nguoi_giao" style="width: 30%;" />
                            <display:column headerClass="table_header nowrap" sortable="false" sortName="giftDeliveryThueBao.nvGiao.displayName" property="tenCuaHangGiaoQua" titleKey="label.cua_hang" style="width: 30%;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" sortName="status" titleKey="cuahanggiaodich.baocaotheokhcn.label.status" class="text-center nowrap" style="width: 20%px;" >
                                <fmt:message key="label.da_giao_qua" />
                            </display:column>
                            <display:setProperty name="paging.banner.item_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.giao_dich_qua"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="cuahanggiaodich.baocaotheokhcn.giao_dich_qua"/></display:setProperty>
                        </display:table>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#qStudent2015ReportTheoKHCNTab');

        var fromDateVar = $("#fromDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromDateVar.hide();
                }).data('datepicker');

        $('#fromDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        var toDateVar = $("#toDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toDateVar.hide();
                }).data('datepicker');

        $('#toDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        jQuery.validator.addClassRules('validate_phone_number', {
            validatePhoneNumber: true
        });

        jQuery.validator.addMethod("validatePhoneNumber", function () {
            return validatePhoneNumber($.trim($('#soThueBao').val()));
        }, "<fmt:message key="user.msg.invalid_phone_number"/>");
    });

    function filterForm(){
        $('#crudaction').val('search');
        $('#reportForm').submit();
    }

    function reset(){
        $('#soThueBao').val('');
        $('#fromDate').val('');
        $('#toDate').val('');
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }
</script>
</body>