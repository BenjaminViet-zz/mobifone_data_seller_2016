<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="titlePage" value="payment_history.management.edit_page.heading" />

<head>
    <title><fmt:message key="${titlePage}" /></title>
    <meta name="menu" content="<fmt:message key="${titlePage}" />"/>
</head>

<c:set var="prefix" value="${vms:getPrefixUrl()}" />
<c:url var="formUrl" value="${prefix}/payment/add.html" />
<c:if test="${not empty item.pojo.paymentHistoryId}">
    <c:url var="formUrl" value="${prefix}/payment-history/edit.html" />
</c:if>
<c:url var="backUrl" value="${prefix}/payment-history/list.html" />

<div class="page-title">
    <div class="title_left">
        <h3>
            <fmt:message key="payment_history.management.edit_page.heading" />
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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="khdnId"><fmt:message key="payment.management.label.khdn" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <span id="khdnId" class="form-control" disabled="true">${item.pojo.payment.order.khdn.name}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="orderId"><fmt:message key="payment.management.label.order" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <span id="orderId" class="form-control" disabled="true">${item.pojo.payment.order.orderId}</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="orderTotal"><fmt:message key="payment.management.label.order_amount" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <span id="orderTotal" class="form-control" disabled="true"><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.payment.orderTotal}" /></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="totalPaidAmount"><fmt:message key="payment.management.label.totalPaidAmount" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <span id="totalPaidAmount" class="form-control" disabled="true"><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.payment.totalPaidAmount - (item.pojo.amount > 0 ? item.pojo.amount : 0)}" /></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="remainingAmount"><fmt:message key="payment.management.label.remainingAmount" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <span id="remainingAmount" class="form-control" disabled="true"><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.payment.remainingAmount + (item.pojo.amount > 0 ? item.pojo.amount : 0)}" /></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="amount"><fmt:message key="payment.management.label.amount" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="amount" type="text" name="pojo.amount" min="0" class="required nohtml form-control" value="<fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.amount}" />" />
                            <form:errors cssClass="error-inline-validate" path="pojo.amount" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="paymentDate"><fmt:message key="payment.management.label.payment_date" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class="input-append date" >
                                <input name="paymentDate" id="paymentDate" class="required prevent_type text-center form-control" type="text"
                                       value="<fmt:formatDate pattern="${datePattern}" value="${item.pojo.paymentDate}" />" placeholder="${symbolDateEmpty}"/>
                                <span class="add-on" id="paymentDateIcon"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a href="${backUrl}" class="btn btn-success"><i class="fa fa-times" aria-hidden="true"></i> <fmt:message key="label.huy" /></a>&nbsp;
                            <a id="btnSave" class="btn btn-primary"><i class="fa fa-floppy-o" aria-hidden="true"></i>
                                <fmt:message key="payment.management.label.save_and_update_payment" />
                            </a>
                        </div>
                    </div>
                    <input id="crudaction" type="hidden" name="crudaction" value="<%=Constants.ACTION_UPDATE%>" />
                    <form:hidden id="paymentHistoryId" path="pojo.paymentHistoryId" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<fmt:message var="message" key="payment.management.msg.payment_amount_must_be_less_than_or_equal_to_remaining_amount">
    <fmt:param>
        <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.payment.remainingAmount + (item.pojo.amount > 0 ? item.pojo.amount : 0)}" />
    </fmt:param>
</fmt:message>


<script type="text/javascript">
    var $amount = $('#amount'),
        $form = $('#formEdit'),
        $khdnMenu = $('#khdnMenu'),
        $orderMenu = $('#orderMenu'),
        $btnSave = $('#btnSave'),
        $paymentDate = $("#paymentDate");

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

    function checkAmountBeforeSubmit(){
        if($form.valid()){
            if($amount.length){
                var amount = eval($amount.val().replace(/\,/g, ''));
                var remainingAmount = eval('<fmt:formatNumber type="number" maxFractionDigits="2" value="${item.pojo.payment.remainingAmount + (item.pojo.amount > 0 ? item.pojo.amount : 0)}" />'.replace(/\,/g, ''));

                if(amount > remainingAmount){
                    bootbox.alert('<fmt:message key="label.alert_title" />', '${message}', function(){});
                    return false;
                }
            }
        }

        return true;
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

    function bindMask(){
        if($amount.length){
            $amount.mask('000,000,000,000,000,000', {
                reverse: true
            });
        }
    }

    function initDatePicker(){
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