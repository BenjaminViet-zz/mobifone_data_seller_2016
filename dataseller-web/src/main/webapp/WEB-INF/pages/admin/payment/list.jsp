<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="paymenthistory.heading"/></title>
    <meta name="menu" content="<fmt:message key="payment.management.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="${vms:getPrefixUrl()}" />
<c:set var="formUrl" value="${prefix}/payment/list.html" />
<c:set var="addUrl" value="${prefix}/payment/add.html" />
<c:set var="editUrl" value="${prefix}/payment/edit.html" />
<c:set var="historyUrl" value="${prefix}/payment-history/list.html" />

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="payment.management.page_title"/></h3>
    </div>

    <div class="title_right">
        <div class="action-bar">
            <a class="btn btn-primary" href="${addUrl}"><i class="fa fa-plus" aria-hidden="true"></i> <fmt:message key="payment.management.label.new_payment"/></a>
        </div>
    </div>
</div>
<div class="clearfix"></div>

<form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post"
           autocomplete="off" name="listForm">
    <c:if test="${not empty messageResponse}">
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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="KHDN"><fmt:message key="payment.management.label.khdn" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="KHDN" path="pojo.khdn.KHDNId" cssClass="form-control">
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${KHDNList}" var="KHDN">
                                    <option <c:if test="${item.pojo.khdn.KHDNId eq KHDN.KHDNId}">selected="true"</c:if> value="${KHDN.KHDNId}">${KHDN.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status"><fmt:message key="payment.management.label.payment_status"/>
                        </label>

                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="status" path="pojo.status" cssClass="form-control">
                                <option value="-1"><fmt:message key="label.all" /></option>
                                <option value="${Constants.PAYMENT_STATUS_CREATED}" <c:if test="${item.pojo.status eq Constants.PAYMENT_STATUS_CREATED}">selected="true"</c:if> ><fmt:message key="payment.management.label.payment_status_created" /></option>
                                <option value="${Constants.PAYMENT_STATUS_PAID}" <c:if test="${item.pojo.status eq Constants.PAYMENT_STATUS_PAID}">selected="true"</c:if> ><fmt:message key="payment.management.label.payment_status_paid" /></option>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();"><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset"/></a>
                            <a class="btn btn-primary" onclick="javascript: searchForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search"/></a>
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
                    <div id="tableListContainer" style="width: 100%">
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}" partialList="true" sort="external" size="${items.totalItems}"
                                       defaultsort="0" id="tableList" pagesize="${items.maxPageItems}" export="false" class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">
                            <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center width_50_px">
                                <div style="width: 50px;">${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</div>
                            </display:column>
                            <display:column headerClass="table_header text-center" titleKey="payment.management.label.khdn">
                                <div style="width: 200px;">${tableList.khdn.name}</div>
                            </display:column>
                            <display:column headerClass="table_header text-center" titleKey="payment.management.label.order">
                                <div style="width: 100px;">${tableList.order.orderId}</div>
                            </display:column>
                            <display:column headerClass="table_header text-center" class="text-right" titleKey="payment.management.label.order_amount">
                                <div style="width: 150px;"><fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.orderTotal}" /></div>
                            </display:column>
                            <display:column headerClass="table_header text-center" class="text-right" titleKey="payment.management.label.totalPaidAmount">
                                <div style="width: 150px;"><fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.totalPaidAmount}" /></div>
                            </display:column>
                            <display:column headerClass="table_header text-center" class="text-right" titleKey="payment.management.label.remainingAmount">
                                <div style="width: 150px;"><fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.remainingAmount}" /></div>
                            </display:column>
                            <display:column headerClass="table_header text-center" class="text-center" titleKey="payment.management.label.payment_date">
                                <div style="width: 150px;"><fmt:formatDate value="${tableList.paymentDate}" pattern="${datePattern}" /></div>
                            </display:column>
                            <display:column headerClass="table_header text-center" titleKey="payment.management.label.status" class="text-center">
                                <div style="width: 200px;">
                                    <c:choose>
                                        <c:when test="${tableList.status eq Constants.PAYMENT_STATUS_PAID}">
                                            <fmt:message key="payment.management.label.payment_status_paid" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${tableList.totalPaidAmount gt 0}">
                                                    <fmt:message key="payment.management.label.partial_paid" />
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="payment.management.label.payment_status_created" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </display:column>
                            <display:column headerClass="table_header text-center" titleKey="payment.management.label.created_by" class="text-center">
                                <div style="width: 200px;">${tableList.createdBy.displayName}</div>
                            </display:column>
                            <display:column headerClass="table_header  text-center" class="text-center" titleKey="label.action">
                                <div style="width: 100px;">
                                    <%--<a href="${historyUrl}?pojo.paymentId=${tableList.paymentId}" class="tip-top action-group" data-toggle="tooltip" title="<fmt:message key="payment.management.label.history_review" />"><i class="fa fa-pencil" aria-hidden="true"></i></a>--%>
                                    <c:if test="${tableList.status ne Constants.PAYMENT_STATUS_PAID}">
                                        <a href="${editUrl}?pojo.paymentId=${tableList.paymentId}" class="tip-top action-group" data-toggle="tooltip" title="<fmt:message key="label.edit" />"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                        <a class="tip-top action-group" data-toggle="tooltip" title="<fmt:message key="label.delete" />" onclick="javascript: deletePayment(${tableList.paymentId});"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
                                    </c:if>
                                </div>
                            </display:column>

                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.payment"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.payment"/></display:setProperty>
                        </display:table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <input id="crudaction" type="hidden" name="crudaction" value="<%=Constants.ACTION_SEARCH%>"/>
</form:form>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    var $tableContainer = $('#tableListContainer');

    $(document).ready(function(){
        initScrollablePane();
        initDatePicker();
    });

    function initScrollablePane(){
        $tableContainer.mCustomScrollbar({axis:"x"});
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


    function initDatePicker(){
        var $regDateFromEl = $("#regDateFrom");
        $regDateFromEl.datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
        }}).on('changeDate',function (ev) {
                $regDateFromEl.hide();
        }).data('datepicker');

        $('#regDateFromIcon').click(function () {
            $staDateFromEl.focus();
            return true;
        });

        var $regDateToEl = $("#regDateTo");
        $regDateToEl.datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
        }}).on('changeDate',function (ev) {
                    $regDateToEl.hide();
        }).data('datepicker');

        $('#regDateToIcon').click(function () {
            $regDateToEl.focus();
            return true;
        });

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

    function deletePayment(paymentId){
        bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
            if(r){
                document.location.href = '${formUrl}?pojo.paymentId=' + paymentId + '&crudaction=<%=Constants.ACTION_DELETE%>';
            }
        });
    }
</script>