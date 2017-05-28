<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="payment_history.management.list.heading"/></title>
    <meta name="menu" content="<fmt:message key="payment_history.management.list.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="${vms:getPrefixUrl()}" />
<c:set var="formUrl" value="${prefix}/payment-history/list.html" />
<c:set var="editUrl" value="${prefix}/payment-history/edit.html" />

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="payment_history.management.list.page_title"/></h3>
    </div>
</div>
<div class="clearfix"></div>

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
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="listForm" action="${formUrl}" method="post"
                           autocomplete="off" name="listForm">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="khdnMenu"><fmt:message key="payment_history.management.label.khdn" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select cssClass="form-control" id="khdnMenu" path="khdnId" onchange="javascript: reloadOrderMenu();">
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${KHDNList}" var="khdnDTO">
                                    <option <c:if test="${item.khdnId eq khdnDTO.KHDNId}">selected="true"</c:if> value="${khdnDTO.KHDNId}">${khdnDTO.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="orderMenu"><fmt:message key="payment_history.management.label.order" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select cssClass="form-control" id="orderMenu" path="orderId" >
                                <option value=""><fmt:message key="label.all" /></option>
                                <c:forEach items="${orderList}" var="orderDTO">
                                    <option <c:if test="${item.orderId eq orderDTO.orderId}">selected="true"</c:if> value="${orderDTO.orderId}">${orderDTO.orderId}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();"><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset"/></a>
                            <a class="btn btn-primary" onclick="javascript: searchForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search"/></a>
                        </div>
                    </div>
                    <input id="crudaction" type="hidden" name="crudaction" value="<%=Constants.ACTION_SEARCH%>"/>
                    <form:hidden id="paymentId" path="pojo.payment.paymentId"/>
                </form:form>
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
                                   defaultsort="0" id="tableList" pagesize="${items.maxPageItems}" export="false" class="table table-striped table-bordered" style="margin: 1em 0 1.5em; width: 1250px;">
                        <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center">
                            <div style="width: 50px;">
                                ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" class="text-right" titleKey="payment.management.label.order" >
                            <div style="width: 100px;">
                                ${tableList.payment.order.orderId}
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" class="text-right" titleKey="payment.management.label.order_amount">
                            <div style="width: 150px;"><fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.payment.orderTotal}" /></div>
                        </display:column>
                        <display:column headerClass="table_header text-center" class="text-center" titleKey="payment_history.management.label.created_or_updated_date" >
                            <div style="width: 150px;">
                                <c:choose>
                                    <c:when test="${not empty tableList.lastModifiedDate}">
                                        <fmt:formatDate value="${tableList.lastModifiedDate}" pattern="${datePattern}" />
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatDate value="${tableList.createdDate}" pattern="${datePattern}" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" class="text-right" titleKey="payment_history.management.label.amount" >
                            <div style="width: 150px;">
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.amount}" />
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" class="text-center" titleKey="payment_history.management.label.paymentDate">
                            <div style="width: 150px;">
                                <fmt:formatDate value="${tableList.paymentDate}" pattern="${datePattern}" />
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" titleKey="payment_history.management.label.lastUpdatedOrderCreatedBy">
                            <div style="width: 250px;">
                                <c:choose>
                                    <c:when test="${not empty tableList.modifiedBy}">
                                        ${tableList.modifiedBy.displayName}
                                    </c:when>
                                    <c:otherwise>
                                        ${tableList.createdBy.displayName}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </display:column>
                        <display:column headerClass="table_header text-center" class="text-center" titleKey="payment_history.management.label.status">
                            <div style="width: 250px;">
                                <c:choose>
                                    <c:when test="${not empty tableList.amount || not empty tableList.paymentDate}">
                                        <fmt:message key="payment_history.management.label.status_update_payment_info" />
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="payment_history.management.label.status_update_info" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </display:column>
                        <display:column headerClass="table_header  text-center" class="text-center" titleKey="label.action" >
                            <div style="width: 100px;">
                                <security:authorize access="hasAuthority('PAYMENT_STATUS_MANAGER')">
                                    <c:if test="${tableList.payment.status ne Constants.PAYMENT_STATUS_PAID}">
                                        <a href="${editUrl}?pojo.paymentHistoryId=${tableList.paymentHistoryId}" class="tip-top action-group" data-toggle="tooltip" title="<fmt:message key="label.edit" />"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                                        <a class="tip-top action-group" data-toggle="tooltip" title="<fmt:message key="label.delete" />" onclick="javascript: deletePaymentHistory(${tableList.paymentHistoryId});"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
                                    </c:if>
                                </security:authorize>
                            </div>
                        </display:column>
                        <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.paymen_history.item"/></display:setProperty>
                        <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.paymen_history.item"/></display:setProperty>
                    </display:table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    var $khdnMenu = $('#khdnMenu'),
        $orderMenu = $('#orderMenu'),
        $form = $('#listForm'),
        $crudaction = $('#crudaction'),
        $tableContainer = $('#tableListContainer'),
        $tableList = $('#tableList'),
        $paymentId = $('#paymentId');

    $(document).ready(function(){
        initScrollablePane();
    });

    function initScrollablePane(){
        $tableContainer.mCustomScrollbar({axis:"x"});
    }

    function resetForm(){
        $("input[type='text']").val('');
    }

    function searchForm(){
        $crudaction.val('${Constants.ACTION_SEARCH}');

        if($khdnMenu.val() != '' || $orderMenu.val() != ''){
            $paymentId.val('');
        }

        $form.submit();
    }

    function reloadOrderMenu(){
        try{
            $orderMenu.select2('destroy');
            $orderMenu.find('option:not(:first-child)').remove();

        }catch(e){}

        var parmas = {
            khdnId: $khdnMenu.val()
        }

        $.ajax({
            url: "<c:url value="/ajax/order/getListByKHDNidHasPayment.html" />",
            type:"get",
            dataType: "JSON",
            data: parmas,
            success: function(r){
                if(r.res){
                    $.each(r.orderList, function(index, order){
                        $orderMenu.append("<option value=" + order.orderId+ ">" + $.trim(order.orderId) + "</option>");
                        $orderMenu.select2();
                    });
                }else{
                    bootbox.alert('<fmt:message key="label.alert_title" />', '<fmt:message key="payment.edit_page.management.msg.error_reload_by_ajax_to_update_order_list" />', function(){});
                    return false;
                }
            }

        });
    }

    <security:authorize access="hasAuthority('PAYMENT_STATUS_MANAGER')">
        function deletePaymentHistory(paymentHistoryId){
            bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                if(r){
                    document.location.href = '${formUrl}?pojo.paymentHistoryId=' + paymentHistoryId + '&crudaction=<%=Constants.ACTION_DELETE%>';
                }
            });
        }
    </security:authorize>
</script>