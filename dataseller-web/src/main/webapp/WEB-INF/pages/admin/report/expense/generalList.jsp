<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="general_expense.heading" /></title>
    <meta name="menu" content="<fmt:message key="general_expense.heading" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="historyUrl" value="${prefix}/reportGeneralExpense/list.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="general_expense.page_title" /></h3>
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
                                <a class="btn btn-primary" onclick="javascript: exportExcel();"><fmt:message key="label.button.export" /></a>
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
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                           partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                           id="tableList" pagesize="${items.maxPageItems}" export="false"
                                           class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">
                                <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" style="width: 5%" >${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                                <display:column headerClass="table_header text-center" property="shopCode" titleKey="general_expense_report.shop_code" style="width: 10%"/>
                                <display:column headerClass="table_header text-center" property="shopName" titleKey="general_expense_report.shop_name" style="width:15%"/>
                                <display:column headerClass="table_header text-center" class="text-center" titleKey="general_expense_report.development_phase1" style="width: 10%" >
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.developmentAmount1}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" class="text-center" titleKey="general_expense_report.development_phase2" style="width: 10%" >
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.developmentAmount2}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" class="text-center" titleKey="general_expense_report.development_phase3" style="width: 10%" >
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.developmentAmount3}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" class="text-center" titleKey="general_expense_report.maintain_phase1" style="width: 10%" >
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.maintainAmount1}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" class="text-center" titleKey="general_expense_report.maintain_phase2" style="width: 10%" >
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.maintainAmount2}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" class="text-center" titleKey="general_expense_report.maintain_phase3" style="width: 10%" >
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.maintainAmount3}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" class="text-center" titleKey="general_expense_report.total" style="width: 10%" >
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.developmentAmount1 + tableList.developmentAmount2 + tableList.developmentAmount3 + tableList.maintainAmount1 + tableList.maintainAmount2 + tableList.maintainAmount3}" />
                                </display:column>
                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.expense" /></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.expense" /></display:setProperty>
                            </display:table>
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

<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        initDatePicker();
    });

    function resetForm(){
        $("input[type='text']").val('');
        selectFirstItemSelect2('#userGroupMenu');
    }

    function submitForm(){
        $('#crudaction').val('${Constants.ACTION_SEARCH}');
        $('#listForm').submit();
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

    function exportExcel(){
        $('#crudaction').val('${Constants.ACTION_EXPORT}');
        $('#listForm').submit();
    }

    function restructureTableHeader(){
        var $tableEl = $('#tableList');
        var newTheadDOM = $("<thead>" +
                                "<tr>"  +
                                    "<th class='table_header text-center middle-vertical' rowspan='2'>" + '<fmt:message key="label.stt" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical' rowspan='2'>" + '<fmt:message key="general_expense_report.shop_code" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical' rowspan='2'>" + '<fmt:message key="general_expense_report.shop_name" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical' colspan='3'>" + '<fmt:message key="general_expense_report.development_fee" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical' colspan='3'>" + '<fmt:message key="general_expense_report.maintain_fee" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical' rowspan='2'>" + '<fmt:message key="general_expense_report.total" />' + "</th>" +
                                "</tr>" +
                                "<tr>"  +
                                    "<th class='table_header text-center middle-vertical'>" + '<fmt:message key="general_expense_report.development_phase1" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical'>" + '<fmt:message key="general_expense_report.development_phase2" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical'>" + '<fmt:message key="general_expense_report.development_phase3" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical'>" + '<fmt:message key="general_expense_report.maintain_phase1" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical'>" + '<fmt:message key="general_expense_report.maintain_phase2" />' + "</th>" +
                                    "<th class='table_header text-center middle-vertical'>" + '<fmt:message key="general_expense_report.maintain_phase3" />' + "</th>" +
                                "</tr>" +
                            "</thead>");
        $tableEl.find("thead").replaceWith(newTheadDOM);
    }

    $(document).ready(function(){
        restructureTableHeader();
    });
</script>