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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="KHDN"><fmt:message key="admin.donhang.label.KHDN" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="KHDN" path="pojo.custId" cssClass="form-control">
                                <option value=""><fmt:message key="label.choose" /></option>
                                <c:forEach items="${KHDNList}" var="KHDN">
                                    <option <c:if test="${item.pojo.custId eq KHDN.custId}">selected="true"</c:if> value="${KHDN.custId}">${KHDN.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javacsript: resetForm();" ><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset" /></a>
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
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.developmentPhase1}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" class="text-center" titleKey="general_expense_report.development_phase2" style="width: 10%" >
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.developmentPhase2}" />
                                </display:column>
                                <display:column headerClass="table_header text-center" class="text-center" titleKey="general_expense_report.development_phase3" style="width: 10%" >
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.developmentPhase3}" />
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
                                    <fmt:formatNumber type="number" maxFractionDigits="0" value="${tableList.developmentPhase1 + tableList.developmentPhase2 + tableList.developmentPhase3 + tableList.maintainAmount1 + tableList.maintainAmount2 + tableList.maintainAmount3}" />
                                </display:column>
                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.expense" /></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.expense" /></display:setProperty>
                            </display:table>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="no_data_found" />
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <input id="crudaction" type="hidden" name="crudaction" value="<%=Constants.ACTION_SEARCH%>" />
</form:form>

<script language="javascript" type="text/javascript">
    function resetForm(){
        $("input[type='text']").val('');
        selectFirstItemSelect2('#userGroupMenu');
    }

    function submitForm(){
        var selectedCustId = $('#KHDN').val();
        if(selectedCustId != ''){
            $('#listForm').submit();
        }else{
            bootbox.alert('<fmt:message key="label.alert_title" />', '<fmt:message key="general_expense_report.search_popup" />', function(){});
        }
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