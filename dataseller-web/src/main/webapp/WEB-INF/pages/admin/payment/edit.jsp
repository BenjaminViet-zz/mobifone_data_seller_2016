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
                    <c:choose>
                        <c:when test="${not empty item.pojo.paymentId}">
                            <form:hidden path="pojo.order.khdn.KHDNId" />
                            <form:hidden path="pojo.order.orderId" />
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="khdnId"><fmt:message key="payment.management.label.khdn" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <span id="khdnId" class="form-control" disabled="true">${item.pojo.order.khdn.name}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="orderId"><fmt:message key="payment.management.label.order" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <span id="orderId" class="form-control" disabled="true">${item.pojo.order.orderId}</span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="orderTotal"><fmt:message key="payment.management.label.order_amount" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <span id="orderTotal" class="form-control" disabled="true"><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.orderTotal}" /></span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="totalPaidAmount"><fmt:message key="payment.management.label.totalPaidAmount" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <span id="totalPaidAmount" class="form-control" disabled="true"><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.totalPaidAmount}" /></span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="remainingAmount"><fmt:message key="payment.management.label.remainingAmount" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <span id="remainingAmount" class="form-control" disabled="true"><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.remainingAmount}" /></span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="amount"><fmt:message key="payment.management.label.amount" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input id="amount" path="pojo.amount" min="0" cssClass="nohtml form-control required" />
                                    <form:errors cssClass="error-inline-validate" path="pojo.amount" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="paymentDate"><fmt:message key="payment.management.label.payment_date" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <div class="input-append date" >
                                        <input name="paymentDate" id="paymentDate" class="prevent_type text-center form-control required nohtml" type="text"
                                               value="<fmt:formatDate pattern="${datePattern}" value="${item.paymentDate}" />" placeholder="${symbolDateEmpty}"/>
                                        <span class="add-on" id="paymentDateIcon"><i class="icon-calendar"></i></span>
                                        <form:errors cssClass="error-inline-validate" path="paymentDate" />
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="khdnMenu"><fmt:message key="payment.management.label.khdn" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:select id="khdnMenu" path="pojo.order.khdn.KHDNId" cssClass="form-control nohtml" onchange="javascript: reloadOrderListByChangeKHDNId();">
                                        <option value="-1"><fmt:message key="label.choose" /></option>
                                        <c:forEach items="${KHDNList}" var="KHDN">
                                            <option <c:if test="${item.pojo.order.khdn.KHDNId eq KHDN.KHDNId}">selected="true"</c:if> value="${KHDN.KHDNId}">${KHDN.name}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="orderMenu"><fmt:message key="payment.management.label.order" />
                                </label>
                                <div id="orderMenuContainer" class="col-md-6 col-sm-6 col-xs-12">
                                    <form:select id="orderMenu" path="pojo.order.orderId" cssClass="form-control nohtml required" onchange="javascript: updateOrderTotal();">
                                        <option value="-1"><fmt:message key="label.choose" /></option>
                                    </form:select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="orderTotal"><fmt:message key="payment.management.label.order_amount" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <span id="orderTotal" class="form-control" disabled="true"><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.orderTotal}" /></span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="amount"><fmt:message key="payment.management.label.amount" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input id="amount" path="pojo.amount" min="0" cssClass="nohtml form-control required" />
                                    <form:errors cssClass="error-inline-validate" path="pojo.amount" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="paymentDate"><fmt:message key="payment.management.label.payment_date" />
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <div class="input-append date" >
                                        <input name="paymentDate" id="paymentDate" class="prevent_type text-center form-control required nohtml" type="text"
                                               value="<fmt:formatDate pattern="${datePattern}" value="${item.paymentDate}" />" placeholder="${symbolDateEmpty}"/>
                                        <span class="add-on" id="paymentDateIcon"><i class="icon-calendar"></i></span>
                                        <form:errors cssClass="error-inline-validate" path="paymentDate" />
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-success"><i class="fa fa-times" aria-hidden="true"></i> <fmt:message key="label.huy" /></a>&nbsp;
                            <a id="btnSave" class="btn btn-primary"><i class="fa fa-floppy-o" aria-hidden="true"></i>
                                <c:choose>
                                    <c:when test="${not empty item.pojo.paymentId}">
                                        <fmt:message key="payment.management.label.save_and_update_payment" />
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="payment.management.label.save_payment" />
                                    </c:otherwise>
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
        $orderTotal = $('#orderTotal'),
        $btnSave = $('#btnSave'),
        $paymentDate = $('#paymentDate'),
        $orderMenuContainer = $('#orderMenuContainer');

    $(document).ready(function(){
        initDatePicker();
        bindMask();
        bindEvents();
    });

    function bindEvents(){
        $btnSave.click(function(e){
            if(checkAmountBeforeSubmit()){
                if(checkRequireFieldsBeforeSubmit()){
                    $form.submit();
                }
            }
        });
    }

    function checkRequireFieldsBeforeSubmit(){
        if($form.valid()){
            if(($amount.val() !== '' && !checkIfPaymentDateIsValid())){
                bootbox.alert('<fmt:message key="label.alert_title" />', '<fmt:message key="payment_history.management.msg.require_payment_date_value" />', function(){});
                return false;
            }else if($amount.val() == '' && checkIfPaymentDateIsValid()){
                bootbox.alert('<fmt:message key="label.alert_title" />', '<fmt:message key="payment_history.management.msg.require_amount" />', function(){});
                return false;
            }
            return true;
        }
        return false;
    }

    function checkIfPaymentDateIsValid(){
        if($.trim($paymentDate.val()) !== ''){
            var paymentDateArr = $.trim($paymentDate.val()).split('/');
            if(paymentDateArr.length == 3){
                try{
                    var paymentDateJSObj = new Date(eval(paymentDateArr[2]), eval(paymentDateArr[1]) - 1, eval(paymentDateArr[0]));
                    return true;
                }catch(e){
                    return false;
                }
            }
            return false;
        }
        return false;
    }


    function checkAmountBeforeSubmit(){
        <c:choose>
            <c:when test="${not empty item.pojo.paymentId}">
                if($amount.length){
                    var amount = eval($amount.val().replace(/\,/g, ''));
                    var remainingAmount = eval('<fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.remainingAmount}" />'.replace(/\,/g, ''));

                    if(amount > remainingAmount){
                        <fmt:message var="message" key="payment.management.msg.payment_amount_must_be_less_than_or_equal_to_remaining_amount">
                            <fmt:param>
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.remainingAmount}" />
                            </fmt:param>
                        </fmt:message>

                        bootbox.alert('<fmt:message key="label.alert_title" />', '${message}', function(){});
                        return false;
                    }
                }
            </c:when>
            <c:otherwise>
                if($amount.length){
                    var amount = eval($amount.val().replace(/\,/g, ''));
                    var orderTotal = $.trim($orderTotal.text());

                    if(amount > orderTotal){
                        var message = '<fmt:message var="message" key="payment.management.msg.payment_amount_must_be_less_than_or_equal_to_remaining_amount" />';
                        bootbox.alert('<fmt:message key="label.alert_title" />', message.replace('{0}', orderTotal), function(){});
                        return false;
                    }
                }
            </c:otherwise>
        </c:choose>

        return true;
    }

    function bindMask(){
        if($amount.length){
            $amount.mask('000,000,000,000,000,000', {
                reverse: true
            });
        }
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
                        var orderTotal = order.quantity * order.unitPrice;
                        $orderMenu.append("<option data-orderTotal=" + orderTotal + " value=" + order.orderId+ ">" + $.trim(order.orderId) + "</option>");
                    });
                }else{
                    bootbox.alert('<fmt:message key="label.alert_title" />', '<fmt:message key="payment.edit_page.management.msg.error_reload_by_ajax_to_update_order_list" />', function(){});
                    return false;
                }
                $orderMenu.select2();

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

    function updateOrderTotal(){
        if($orderMenu.length){
            if($orderMenu.val() !== '-1'){
                var $option = $orderMenu.find('option:selected');
                var orderTotal = $option.attr('data-orderTotal');
                $orderTotal.text(numberWithCommas(orderTotal * 1));
            }else{
                $orderTotal.text('0');
            }
        }
    }
</script>