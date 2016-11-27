<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="titlePage" value="old_order.heading_page" />
<c:if test="${not empty item.pojo.orderId}">
    <c:set var="titlePage" value="old_order.heading_page" />
</c:if>

<head>
    <title><fmt:message key="${titlePage}" /></title>
    <meta name="menu" content="<fmt:message key="${titlePage}" />"/>
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>

<c:url var="formUrl" value="${prefix}/oldorder/add.html" />
<c:if test="${not empty item.pojo.orderId}">
    <c:url var="formUrl" value="${prefix}/order/edit.html" />
</c:if>

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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="KHDN"><fmt:message key="admin.donhang.label.KHDN" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="KHDN" path="pojo.khdn.KHDNId" cssClass="form-control required" onchange="javascript: updateTotalPaidPackageRemainingValue();">
                                <option value="-1"><fmt:message key="label.choose" /></option>
                                <c:forEach items="${KHDNList}" var="KHDN">
                                    <option data-isdn="${KHDN.stb_vas}" <c:if test="${item.pojo.khdn.KHDNId eq KHDN.KHDNId}">selected="true"</c:if> value="${KHDN.KHDNId}">${KHDN.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="packageData"><fmt:message key="admin.donhang.label.tenGoiCuoc" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select id="packageData" path="pojo.packageData.packageDataId" cssClass="form-control required" onchange="javascript: checkPackageDataCardCodeGeneration();">
                                <option value="-1"><fmt:message key="label.choose" /></option>
                                <c:forEach items="${packageDataList}" var="packageData">
                                    <option data-unitPrice="<fmt:formatNumber type="number" maxFractionDigits="0" value="${packageData.value}" />" <c:if test="${item.pojo.packageData.packageDataId eq packageData.packageDataId}">selected="true"</c:if> value="${packageData.packageDataId}">${packageData.name}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="quantity"><fmt:message key="admin.donhang.label.quantity" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="quantity" type="text" name="pojo.quantity" min="1" class="form-control required money" value="<fmt:formatNumber type="number" maxFractionDigits="0" value="${item.pojo.quantity}" /> " />
                            <form:errors for="quantity" path="pojo.quantity" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="unitPrice"><fmt:message key="admin.donhang.label.UnitPrice" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="unitPrice" type="text" name="pojo.unitPrice" min="1" readonly="readonly"  class="form-control required money" value="<fmt:formatNumber type="number" maxFractionDigits="0" value="${item.pojo.unitPrice}" /> " />
                            <form:errors for="unitPrice" path="pojo.unitPrice" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" ><fmt:message key="admin.donhang.label.total" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input id="calcOrderTotal" type="text" readonly="readonly" class="form-control calcOrderTotal"/>
                            <form:errors for="calcOrderTotal" cssClass="error-inline-validate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="issuedDate"><fmt:message key="admin.donhang.label.issueDate" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12 xdisplay_inputx form-group has-feedback">
                            <input type="text" class="form-control has-feedback-left data_picker" name="issuedDate" value="<fmt:formatDate pattern="${datePattern}" value="${item.pojo.issuedDate}" />" id="issuedDate" aria-describedby="inputSuccess2Status4">
                            <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                            <%--<span id="inputSuccess2Status4" class="sr-only">(success)</span>--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="shippingDate"><fmt:message key="admin.donhang.label.shippingDate" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12 xdisplay_inputx form-group has-feedback">
                            <input type="text" class="form-control has-feedback-left data_picker" name="shippingDate" value="<fmt:formatDate pattern="${datePattern}" value="${item.pojo.shippingDate}" />" id="shippingDate" aria-describedby="inputSuccess2Status4">
                            <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                            <%--<span id="inputSuccess2Status4" class="sr-only">(success)</span>--%>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <c:set var="allowUpdateOrInsert" value="${true}" />
                            <c:if test="${packageDataIdListHasGeneratedCardCode.size() eq 0}">
                                <c:set var="allowUpdateOrInsert" value="${false}" />
                            </c:if>
                            <a id="btnSave" <c:if test="${!allowUpdateOrInsert}"> disabled="disabled" </c:if> class="btn btn-primary"><i class="fa fa-floppy-o" aria-hidden="true"></i>
                            <c:choose>
                                <c:when test="${not empty item.pojo.orderId}"><fmt:message key="label.update" /></c:when>
                                <c:otherwise><fmt:message key="label.save" /></c:otherwise>
                            </c:choose>
                            </a>
                        </div>
                    </div>
                    <input type="hidden" name="crudaction" value="insert-update" />
                    <form:hidden id="orderId" path="pojo.orderId" />
                    <form:hidden path="pojo.cardCodeProcessStatus" />
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var totalRemainingPaidPackageValue = ${totalRemainingPaidPackageValue};
    var $message_sectionEl = $('#message_section');

    var packageDataIdsHasGenerateCardCodeList = [];
    <c:if test="${packageDataIdListHasGeneratedCardCode.size() > 0}">
            <c:forEach items="${packageDataIdListHasGeneratedCardCode}" var="packageDataId">
    packageDataIdsHasGenerateCardCodeList.push('${packageDataId}');
    </c:forEach>
    </c:if>

    $(document).ready(function(){
        storeDOMData();
        bindMask();
        bindEvent();
    });

    function checkPackageDataCardCodeGeneration(){
    <c:if test="${packageDataIdListHasGeneratedCardCode.size() > 0}">
            var selectedPackageDataId = $('#packageData').val();
        if(packageDataIdsHasGenerateCardCodeList.length > 0 && selectedPackageDataId != ''){
            if(packageDataIdsHasGenerateCardCodeList.indexOf(selectedPackageDataId) == -1){
                $('#page_message_title').removeClass('hide').find('span:first').html('<fmt:message key="packagedatacodegen.this_package_data_has_not_yet_generate_card_code" />');
                $('#btnSave').attr('disabled', 'disabled');
            }else{
                $('#page_message_title').addClass('hide').find('span:first').html('');
                $('#btnSave').removeAttr('disabled');
            }
        }else{
            $('#page_message_title').addClass('hide');
        }
    </c:if>
    }

    function updateTotalPaidPackageRemainingValue(){
        var $khdnSelectMenu =  $('#KHDN');
        var selectedKHDNId = $khdnSelectMenu.val();
        if(selectedKHDNId == -1){
            return;
        }

        var isdn = $khdnSelectMenu.find('option:selected').data('isdn');
        $.ajax({
            url: '<c:url value="/ajax/calculateTotalPaidPackageValue.html" />',
            type: 'get',
            data: {isdn: isdn},
            success: function(r){
                totalRemainingPaidPackageValue = r.value;
                checkOrderCost();
            }
        });
    }

    function storeDOMData(){
        $('#KHDN').find("option:not(:first-child)").each(function(index, el){
            var $optEl = $(el);
            $optEl.data("isdn", $optEl.attr('data-isdn')).removeAttr('data-isdn');
        });

        $('option:not(:first-child)', '#packageData').each(function(idx, el){
            $(el).data("unitPrice", $(el).attr('data-unitPrice')).removeAttr('data-unitPrice');
        });

    }

    function checkOrderCost(){
        var orderCost = eval($('#calcOrderTotal').val().replace(/\,/g, ''));
        if(orderCost > totalRemainingPaidPackageValue){
            $message_sectionEl.html("<div class=\"row\">" +
                    "<div class=\"col-md-12 col-sm-12 col-xs-12\">" +
                    "<div class=\"x_panel\">" +
                    "<div class=\"x_content\">" +
                    "<div class=\"alert alert-warning alert-dismissible fade in\">" +
                    "<a class=\"close\" data-dismiss=\"alert\" href=\"#\">&times;</a>" +
                    "<fmt:message key='donhang.popup.exceed_remaining_paid_package' />" +
                    "</div>" +
                    "<div class=\"clear\"></div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>");
        }else{
            $message_sectionEl.html('');
        }
    }

    function bindMask(){
        var $totalEl = $('.calcOrderTotal');
        $totalEl.val(eval($('#quantity').val().replace(/\,/g, '')) * eval($('#unitPrice').val().replace(/\,/g, '')));
        $totalEl.mask('000,000,000,000,000,000', {
            reverse: true
        });

        $('#quantity').keyup(function() {
            $('.calcOrderTotal').val( numberWithCommas( $('#quantity').val().replace(/\,/g, '')*1 * $('#unitPrice').val().replace(/\,/g, '')*1 )  );
            checkOrderCost();
        });

        $('#unitPrice').keyup(function() {
            $('.calcOrderTotal').val( numberWithCommas( $('#quantity').val().replace(/\,/g, '')*1 * $('#unitPrice').val().replace(/\,/g, '')*1 )  );
            checkOrderCost();
        });

        $('#packageData').on('change', function(){
            $('#unitPrice').val($(this).find('option:selected').data('unitPrice'));
            $('.calcOrderTotal').val( numberWithCommas( $('#quantity').val().replace(/\,/g, '')*1 * $('#unitPrice').val().replace(/\,/g, '')*1 )  );
            /*jQueryMask();*/
        });
    }

    function bindEvent(){
        $("#btnSave").click(function(e){
            var statusVal = $('#status').val();
            if(statusVal == '${Constants.ORDER_STATUS_FINISH}'){
                bootbox.confirm('<fmt:message key="donhang.popup.title" />', '<fmt:message key="donhang.popup.content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                    if(r && $('#formEdit').valid() ){
                        $("#formEdit").submit();
                    }
                });
            }else{
                $("#formEdit").submit();
            }
        });
    }
</script>