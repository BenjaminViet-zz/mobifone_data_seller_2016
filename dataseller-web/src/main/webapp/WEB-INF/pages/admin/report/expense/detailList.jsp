<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="detail_expense_report.heading" /></title>
    <meta name="menu" content="<fmt:message key="detail_expense_report.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="historyUrl" value="${prefix}/reportDetailExpense/list.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="detail_expense_report.page_title" /></h3>
    </div>
</div>
<div class="clearfix"></div>

<form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post" autocomplete="off" name="listForm">
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
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="shop_Code"><fmt:message key="general_expense_report.shop_code" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="shop_Code" path="pojo.shopCode" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="empCode"><fmt:message key="general_expense_report.emp_code" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="empCode" path="pojo.empCode" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="isdn"><fmt:message key="general_expense_report.isdn" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="isdn" path="pojo.isdn" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="issuedDateFrom"><fmt:message key="general_expense_report.issuedDateFrom" /></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class="input-append date" >
                                <input name="issuedDateFrom" id="issuedDateFrom" class="prevent_type text-center form-control" type="text"
                                       value="<fmt:formatDate pattern="${datePattern}" value="${item.issuedDateFrom}" />" placeholder="${symbolDateEmpty}"/>
                                <span class="add-on" id="staDateFromIcon"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="issuedDateTo"><fmt:message key="general_expense_report.issuedDateTo" /></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class="input-append date" >
                                <input name="issuedDateTo" id="issuedDateTo" class="prevent_type text-center form-control" type="text"
                                       value="<fmt:formatDate pattern="${datePattern}" value="${item.issuedDateTo}" />" placeholder="${symbolDateEmpty}"/>
                                <span class="add-on" id="issuedDateToIcon"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();" ><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search" /></a>
                            <c:if test="${item.crudaction == 'search' && item.listResult.size() > 0}">
                                <a class="btn btn-primary" onclick="javascript: exportExcel();"><i class="fa fa-file-excel-o" aria-hidden="true"></i> <fmt:message key="label.button.export" /></a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_content">
                    <c:choose>
                        <c:when test="${item.crudaction eq 'search'}">
                            <div id="tableListContainer" style="width: 100%;">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false"
                                               class="table table-striped table-bordered" style="margin: 1em 0 1.5em; width: 3700px;">
                                    <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" ><div style="width: 50px">${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</div></display:column>
                                    <display:column headerClass="table_header text-center" titleKey="detail_expense_report.custId">
                                        <div style="width: 200px;">${tableList.custId}</div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" titleKey="detail_expense_report.isdn">
                                        <div style="width: 250px;">${tableList.isdn}</div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.emp_name">
                                        <div style="width: 200px;">${tableList.name}</div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.ma_nvpt">
                                        <div style="width: 200px;">${tableList.empCode}</div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.loai_hm">
                                        <div style="width: 200px;">${tableList.busType}</div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.loai_tb">
                                        <div style="width: 200px;">${tableList.loaiTB}</div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.loai_kh">
                                        <div style="width: 200px;">${tableList.custType}</div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.ngay_dau_noi">
                                        <div style="width: 200px;">
                                            <fmt:formatDate value="${tableList.staDateTime}" pattern="${datePattern}" />
                                        </div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.act_status">
                                        <div style="width: 200px;">${tableList.actStatus}</div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.status">
                                        <div style="width: 200px;">${tableList.status}</div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.cuoc_thuc_thu">
                                        <div style="width: 200px;">
                                            <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.cuocThucThu}" />
                                        </div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.developmentAmount1">
                                        <div style="width: 200px;">
                                            <fmt:formatNumber type="number" value="${tableList.developmentAmount1}" maxFractionDigits="0" />
                                        </div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.developmentAmount2">
                                        <div style="width: 200px;">
                                            <fmt:formatNumber type="number" value="${tableList.developmentAmount2}" maxFractionDigits="0" />
                                        </div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.developmentAmount3">
                                        <div style="width: 200px;">
                                            <fmt:formatNumber type="number" value="${tableList.developmentAmount3}" maxFractionDigits="0" />
                                        </div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.maintainAmount1">
                                        <div style="width: 200px;">
                                            <fmt:formatNumber type="number" value="${tableList.maintainAmount1}" maxFractionDigits="0" />
                                        </div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.maintainAmount2">
                                        <div style="width: 200px;">
                                            <fmt:formatNumber type="number" value="${tableList.maintainAmount2}" maxFractionDigits="0" />
                                        </div>
                                    </display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="detail_expense_report.maintainAmount3">
                                        <div style="width: 200px;">
                                            <fmt:formatNumber type="number" value="${tableList.maintainAmount3}" maxFractionDigits="0" />
                                        </div>
                                    </display:column>
                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.expense" /></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.expense" /></display:setProperty>
                                </display:table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="please_choose_filter" />
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <input id="crudaction" type="hidden" name="crudaction" value="<%=Constants.ACTION_SEARCH%>" />
</form:form>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        initDatePicker();
        restructureTableHead();
        initScrollablePane();
    });

    function initScrollablePane(){
        $('#tableListContainer').mCustomScrollbar({axis:"x"});
    }

    function initDatePicker(){
        var issuedDateFromEl = $("#staDateFrom");
        issuedDateFromEl.datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    issuedDateFromEl.hide();
                }).data('datepicker');

        $('#staDateFromIcon').click(function () {
            issuedDateFromEl.focus();
            return true;
        });

        var issuedDateToEl = $("#staDateTo");
        issuedDateToEl.datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    issuedDateToEl.hide();
                }).data('datepicker');

        $('#staDateToIcon').click(function () {
            issuedDateToEl.focus();
            return true;
        });
    }

    function resetForm(){
        $("input[type='text']").val('');
    }

    function submitForm(){
        $('#crudaction').val('${Constants.ACTION_SEARCH}');
        $('#listForm').submit();
    }

    function exportExcel(){
        $('#crudaction').val('${Constants.ACTION_EXPORT}');
        $('#listForm').submit();
    }

    function restructureTableHead(){
        var $tableList = $('#tableList');
        if($tableList.length ){

        }
    }
</script>