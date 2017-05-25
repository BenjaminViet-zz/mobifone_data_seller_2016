<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="titlePage" value="payment.edit_page.management.heading" />

<head>
    <title><fmt:message key="${titlePage}" /></title>
    <meta name="menu" content="<fmt:message key="${titlePage}" />"/>
</head>

<c:set var="prefix" value="${vms:getPrefixUrl()}" />
<c:url var="formUrl" value="${prefix}/payment/add.html" />
<c:if test="${not empty item.pojo.paymentId}">
    <c:url var="formUrl" value="${prefix}/payment/edit.html" />
</c:if>
<c:url var="backUrl" value="${prefix}/payment/list.html" />

<div class="page-title">
    <div class="title_left">
        <h3>
            <fmt:message key="${titlePage}" />
        </h3>
    </div>
</div>

<div class="clearfix"></div>
<div id="message_section">
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
</div>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_content">
                <form:form commandName="item" cssClass="form-horizontal form-label-left" id="formEdit" action="${formUrl}" method="post" validate="validate">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="khdnMenu"><fmt:message key="payment.management.label.khdn" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="khdnMenu" path="pojo.khdn.KHDNId" cssClass="form-control required" onchange="javascript: reloadOrderListByChangeKHDNId();">
                                <option value="-1"><fmt:message key="label.choose" /></option>
                                <c:forEach items="${KHDNList}" var="KHDN">
                                    <option <c:if test="${item.pojo.khdn.KHDNId eq KHDN.KHDNId}">selected="true"</c:if> value="${KHDN.KHDNId}">${KHDN.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="orderMenu"><fmt:message key="payment.management.label.order" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="orderMenu" path="pojo.order.orderId" cssClass="form-control nohtml required">
                                <option value="-1"><fmt:message key="label.choose" /></option>
                                <c:forEach items="${orderList}" var="order">
                                </c:forEach>
                                    <option <c:if test="${item.pojo.order.orderId eq order.orderId}">selected="true"</c:if> value="${order.orderId}">${order.orderId}</option>
                            </form:select>
                        </div>
                    </div>

                    <security:authorize access="hasAuthority('PAYMENT_STATUS_MANAGER')">
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="paymentDate"><fmt:message key="payment.management.label.payment_date" />
                            </label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="input-append date" >
                                    <input name="paymentDate" id="paymentDate" class="prevent_type text-center form-control" type="text"
                                           value="<fmt:formatDate pattern="${datePattern}" value="${item.paymentDate}" />" placeholder="${symbolDateEmpty}"/>
                                    <span class="add-on" id="paymentDateIcon"><i class="icon-calendar"></i></span>
                                </div>
                            </div>
                        </div>
                    </security:authorize>

                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-success"><i class="fa fa-times" aria-hidden="true"></i> <fmt:message key="label.huy" /></a>&nbsp;
                            <a id="btnSave" class="btn btn-primary"><i class="fa fa-floppy-o" aria-hidden="true"></i>
                                <c:choose>
                                    <c:when test="${not empty item.pojo.paymentId}"><fmt:message key="label.update" /></c:when>
                                    <c:otherwise><fmt:message key="label.save" /></c:otherwise>
                                </c:choose>
                            </a>
                        </div>
                    </div>
                    <input id="crudaction" type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden id="paymentId" path="pojo.paymentId" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var $amount = $('#amount'),
        $form = $('#formEdit'),
        $khdnMenu = $('#khdnMenu'),
        $orderMenu = $('#orderMenu'),
        $btnSave = $('#btnSave');

    $(document).ready(function(){
        bindMask();
        bindEvents();
    });

    function bindEvents(){
        $btnSave.click(function(e){
            $form.submit();
        });
    }

    function bindMask(){
        $amount.mask('000,000,000,000,000,000', {
            reverse: true
        });
    }

    function reloadOrderListByChangeKHDNId(){
        try{
            $orderMenu.select2('destroy');
            $orderMenu.find('option:not(:first-child)').remove();

        }catch(e){}

        var params = {
            khdnId: $khdnMenu.val()
        }

        $.ajax({
            type: "get",
            dataType: "json",
            url: "<c:url value="/ajax/order/getListByKHDNid.html" />",
            data: params,
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

    function initDatePicker(){
        var $paymentDate = $("#paymentDate");

        if($paymentDate.length){
            $paymentDate.datepicker({
                dateFormat: 'dd/mm/yy',
                onRender: function (date) {
                }}).on('changeDate',function (ev) {
                        $paymentDate.hide();
                    }).data('datepicker');

            $('#paymentDateIcon').click(function () {
                $paymentDate.focus();
                return true;
            });
        }
    }
</script>