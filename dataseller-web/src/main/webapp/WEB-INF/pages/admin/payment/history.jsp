<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="code_history.heading" /></title>
    <meta name="menu" content="<fmt:message key="code_history.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="historyUrl" value="${prefix}/codeHistory/list.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="code_history.page_title" /></h3>
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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="isdn"><fmt:message key="code_history.management.filter.isdn" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="isdn" path="pojo.isdn" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"><fmt:message key="code_history.management.filter.name" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="name" path="pojo.name" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="shop_Code"><fmt:message key="code_history.management.filter.shop_Code" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="shop_Code" path="pojo.shopCode" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="staDateFrom"><fmt:message key="code_history.management.filter.ngay_đang_ky_from" /></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class="input-append date" >
                                <input name="staDateFrom" id="staDateFrom" class="prevent_type text-center form-control" type="text"
                                       value="<fmt:formatDate pattern="${datePattern}" value="${item.staDateFrom}" />" placeholder="${symbolDateEmpty}"/>
                                <span class="add-on" id="staDateFromIcon"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="staDateTo"><fmt:message key="code_history.management.filter.ngay_đang_ky_den" /></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class="input-append date" >
                                <input name="staDateTo" id="staDateTo" class="prevent_type text-center form-control" type="text"
                                       value="<fmt:formatDate pattern="${datePattern}" value="${item.staDateTo}" />" placeholder="${symbolDateEmpty}"/>
                                <span class="add-on" id="staDateToIcon"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();" ><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: searchForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search" /></a>
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
                            <div id="tableListContainer" style="width: 100%">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false"
                                               class="table table-striped table-bordered" style="width: 3850px; margin: 1em 0 1.5em;">
                                    <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" style="width: 50px" >${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="payment.manager.table.payment_status" style="width: 200px;">
                                        <fmt:message key="payment.manager.table.payment_status_paid" />
                                    </display:column>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.payment_dateTime" class="text-center" style="width: 200px;">
                                        <fmt:formatDate value="${tableList.paymentDate}" pattern="${datePattern}" />
                                    </display:column>

                                    <display:column headerClass="table_header text-center" property="shopCode" titleKey="payment.manager.table.shop_Code" style="width: 200px"/>
                                    <display:column headerClass="table_header text-center" property="shopName" titleKey="payment.manager.table.shop_Name" style="width: 200px"/>
                                    <display:column headerClass="table_header text-center" property="isdn" titleKey="payment.manager.table.isdn" class="text-center" style="width: 200px"/>
                                    <display:column headerClass="table_header text-center" property="name" titleKey="payment.manager.table.name" style="width: 200px"/>
                                    <display:column headerClass="table_header text-center" property="empCode" titleKey="payment.manager.table.emp_code" style="width: 200px"/>
                                    <display:column headerClass="table_header text-center" property="busType" titleKey="payment.manager.table.bus_type" style="width: 200px"/>
                                    <display:column headerClass="table_header text-center" property="custType" titleKey="payment.manager.table.cust_type" style="width: 200px"/>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.sta_dateTime" class="text-center" style="width: 200px">
                                        <fmt:formatDate value="${tableList.staDateTime}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header text-center" property="actStatus" class="text-center" titleKey="payment.manager.table.act_status" style="width: 200px"/>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.issue_month" class="text-center" style="width: 200px">
                                        <fmt:formatDate value="${tableList.issueMonth}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.payment" class="text-center" style="width: 200px">
                                        <%--<fmt:formatDate value="${tableList.payment}" type="number" />--%>
                                        ___
                                    </display:column>
                                    <%--<display:column headerClass="table_header text-center" titleKey="payment.manager.table.development_phase1" style="width: 100px">--%>
                                    <%--${tableList.developmentPhase1}--%>
                                    <%--</display:column>--%>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.development_amount1" style="width: 200px">
                                        <fmt:formatNumber type="number" value="${tableList.developmentAmount1}" />
                                    </display:column>
                                    <%--<display:column headerClass="table_header text-center" titleKey="payment.manager.table.development_phase2" style="width: 100px">--%>
                                    <%--${tableList.developmentPhase2}--%>
                                    <%--</display:column>--%>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.development_amount2" style="width: 200px">
                                        <fmt:formatNumber type="number" value="${tableList.developmentAmount2}" />
                                    </display:column>
                                    <%--<display:column headerClass="table_header text-center" titleKey="payment.manager.table.development_phase3" style="width: 100px">--%>
                                    <%--${tableList.developmentPhase3}--%>
                                    <%--</display:column>--%>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.development_amount3" style="width: 200px">
                                        <fmt:formatNumber type="number" value="${tableList.developmentAmount3}" />
                                    </display:column>
                                    <%--<display:column headerClass="table_header text-center" titleKey="payment.manager.table.maintain_phase1" style="width: 100px">--%>
                                    <%--${tableList.maintainPhase1}--%>
                                    <%--</display:column>--%>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.maintain_amount1" style="width: 200px">
                                        <fmt:formatNumber type="number" value="${tableList.maintainAmount1}" />
                                    </display:column>
                                    <%--<display:column headerClass="table_header text-center" titleKey="payment.manager.table.maintain_phase2" style="width: 100px">--%>
                                    <%--${tableList.maintainPhase2}--%>
                                    <%--</display:column>--%>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.maintain_amount2" style="width: 200px">
                                        <fmt:formatNumber type="number" value="${tableList.maintainAmount2}" />
                                    </display:column>
                                    <%--<display:column headerClass="table_header text-center" titleKey="payment.manager.table.maintain_phase3" style="width: 100px">--%>
                                    <%--${tableList.maintainPhase3}--%>
                                    <%--</display:column>--%>
                                    <display:column headerClass="table_header text-center" titleKey="payment.manager.table.maintain_amount3" style="width: 200px">
                                        <fmt:formatNumber type="number" value="${tableList.maintainAmount3}" />
                                    </display:column>

                                    <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.payment.item" /></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.payment.item" /></display:setProperty>
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
        initScrollablePane();
        restructureTableHead();
        initDatePicker();
    });

    function initScrollablePane(){
        $('#tableListContainer').mCustomScrollbar({axis:"x"});
    }

    function resetForm(){
        $("input[type='text']").val('');
    }

    function searchForm(){
        $('#crudaction').val('${Constants.ACTION_SEARCH}');
        $('#listForm').submit();
    }

    function exportExcel(){
        $('#crudaction').val('${Constants.ACTION_EXPORT}');
        $('#listForm').submit();
    }

    function restructureTableHead(){
        var $tableList = $('#tableList');
        var $tableHeadContent = $("<thead>" +
                                        "<tr>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="label.stt" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.payment_status" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.payment_dateTime" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.shop_Code" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.shop_Name" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.isdn" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.name" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.emp_code" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.bus_type" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.cust_type" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.sta_dateTime" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.act_status" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.issue_month" /></th>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.payment" /></th>" +
                                            "<th class='table_header text-center middle-vertical' colspan='3'><fmt:message key="payment.manager.table.chu_ky_phat_trien" /></th>" +
                                            "<th class='table_header text-center middle-vertical' colspan='3'><fmt:message key="payment.manager.table.hoa_hong_chi_phi" /></th>" +
                                        "</tr>"+
                                        "<tr>" +
                                            "<th class='table_header text-center middle-vertical'><fmt:message key="payment.manager.table.development_amount1" /></th>" +
                                            "<th class='table_header text-center middle-vertical'><fmt:message key="payment.manager.table.development_amount2" /></th>" +
                                            "<th class='table_header text-center middle-vertical'><fmt:message key="payment.manager.table.development_amount3" /></th>" +
                                            "<th class='table_header text-center middle-vertical'><fmt:message key="payment.manager.table.maintain_amount1" /></th>" +
                                            "<th class='table_header text-center middle-vertical'><fmt:message key="payment.manager.table.maintain_amount2" /></th>" +
                                            "<th class='table_header text-center middle-vertical'><fmt:message key="payment.manager.table.maintain_amount3" /></th>" +
                                        "</tr>" +
                                    "</thead>");
        $tableList.find('thead').replaceWith($tableHeadContent);
    }

    function initDatePicker(){
        var $staDateFromEl = $("#staDateFrom");
        $staDateFromEl.datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    $staDateFromEl.hide();
                }).data('datepicker');

        $('#staDateFromIcon').click(function () {
            $staDateFromEl.focus();
            return true;
        });

        var $staDateToEl = $("#staDateTo");
        $staDateToEl.datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    $staDateToEl.hide();
                }).data('datepicker');

        $('#staDateToIcon').click(function () {
            $staDateToEl.focus();
            return true;
        });
    }
</script>