<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="code_payment_fee.heading" /></title>
    <meta name="menu" content="<fmt:message key="code_history.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="/user" />
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="historyUrl" value="${prefix}/payment/management.html"/>

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="payment.manager.page_title" /></h3>
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
                <div class="x_content danhSachChiPhi">
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
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">
                            <fmt:message key="code_history.management.filter.payment_status" />
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:select cssClass="form-control" id="status" path="pojo.paymentStatus" cssStyle="width: 150px;">
                                <option <c:if test="${Constants.COST_PAYMENT_NOT_PAID eq item.pojo.paymentStatus}">selected="true"</c:if> value="${Constants.COST_PAYMENT_NOT_PAID}"><fmt:message key="code_history.management.filter.payment_status_not_paid" /></option>
                                <option <c:if test="${Constants.COST_PAYMENT_PAID eq item.pojo.paymentStatus}">selected="true"</c:if> value="${Constants.COST_PAYMENT_PAID}"><fmt:message key="code_history.management.filter.payment_status_paid" /></option>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();" ><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset" /></a>
                            <a class="btn btn-primary" onclick="javascript: submitForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search" /></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${item.pojo.paymentStatus eq Constants.COST_PAYMENT_NOT_PAID && not empty items.listResult && items.listResult.size() > 0}">
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_content chiTra">
                        <div class="form-group">
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="paymentDate"><fmt:message key="label.ngay_chi_tra" /></label>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="input-append date" >
                                    <input name="paymentDate" id="paymentDate" class="prevent_type text-center form-control" type="text"
                                           value="<fmt:formatDate pattern="${datePattern}" value="${item.paymentDate}" />" placeholder="${symbolDateEmpty}"/>
                                    <span class="add-on" id="paymentDateIcon"><i class="icon-calendar"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group last">
                            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                <a class="btn btn-success" id="resetFormChiTra" onclick="javascript: resetFormChiTra();" ><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset" /></a>
                                <a class="btn btn-danger" id="btnChiTra"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.payment" /></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_content">
                    <c:choose>
                        <c:when test="${item.crudaction eq 'search'}">
                            <c:set var="tableWidth" value="${3850}" />
                            <c:if test="${item.pojo.paymentStatus eq Constants.COST_PAYMENT_PAID}">
                                <c:set var="tableWidth" value="${3950}" />
                            </c:if>
                            <div id="tableListContainer" style="width: 100%;">
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                               partialList="true" sort="external" size="${items.totalItems}" defaultsort="0"
                                               id="tableList" pagesize="${items.maxPageItems}" export="false"
                                               class="table table-striped table-bordered" style="width: ${tableWidth}px; margin: 1em 0 1.5em;">
                                    <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center" style="width: 50px" >${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                                    <c:if test="${item.pojo.paymentStatus eq Constants.COST_PAYMENT_NOT_PAID}">
                                        <display:column headerClass="table_header text-center" class="text-center" style="width: 50px" >
                                            <input type="checkbox" id="${tableList.costId}" class="" style="width: 15px;" name="checkList" value="${tableList.costId}" onclick="checkAllIfOne('listForm', 'checkList', this, 'allCheck')" />
                                        </display:column>
                                    </c:if>

                                    <display:column headerClass="table_header text-center" class="text-center" titleKey="payment.manager.table.payment_status" style="width: 200px;">
                                        <c:choose>
                                            <c:when test="${tableList.paymentStatus eq Constants.COST_PAYMENT_PAID}">
                                                <fmt:message key="payment.manager.table.payment_status_paid" />
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="payment.manager.table.payment_status_not_paid" />
                                            </c:otherwise>
                                        </c:choose>
                                    </display:column>

                                    <c:if test="${item.pojo.paymentStatus eq Constants.COST_PAYMENT_PAID}">
                                        <display:column headerClass="table_header text-center" titleKey="payment.manager.table.payment_dateTime" class="text-center" style="width: 200px;">
                                            <fmt:formatDate value="${tableList.paymentDate}" pattern="${datePattern}" />
                                        </display:column>
                                    </c:if>

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
    var $btnChiTra = $('#btnChiTra');

    $(document).ready(function(){
        initScrollablePane();
        initDatePicker();
        restructureTableHead();
        disableBtnChiTra();
        bindEvent();
    });

    function bindEvent(){
        $('#paymentDate').datepicker().on("input change", function (e) {
            $('#tableList input[type="checkbox"]').each(function(index, element) {
                if ($(element).is(":checked") && $("#paymentDate").val() != '') {
                    enableBtnChiTra();
                } else {
                    disableBtnChiTra();
                }
            });
        });

        $('input[type="checkbox"]').on('change', function(){
            if( $(this).is(":checked") && $("#paymentDate").val() != '' ) {
                enableBtnChiTra();
            } else {
                disableBtnChiTra();
            }
        });
    }

    function enableBtnChiTra(){
        $btnChiTra.removeClass("disabled")
                .css('pointer-events', 'auto');
    }

    function disableBtnChiTra(){
        $btnChiTra.addClass("disabled")
                .css('pointer-events', 'visible');
    }

    function resetForm(){
        $(".danhSachChiPhi input[type='text']").val('');
    }

    function resetFormChiTra(){
        $(".chiTra input[type='text']").val('');
        disableBtnChiTra();
    }

    function submitForm(){
        var selectedCustId = $('#KHDN').val();
        if(selectedCustId != '-1'){
            $('#listForm').submit();
        }else{
            bootbox.alert('<fmt:message key="label.alert_title" />', '<fmt:message key="code_history.search_popup" />', function(){});
        }
    }

    function initScrollablePane(){
        $('#tableListContainer').mCustomScrollbar({axis:"x"});
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

        <c:if test="${item.pojo.paymentStatus eq Constants.COST_PAYMENT_NOT_PAID && not empty items.listResult && items.listResult.size() > 0}">
            var $paymentDateEl = $("#paymentDate");
            $paymentDateEl.datepicker({
                dateFormat: 'dd/mm/yy',
                onRender: function (date) {
                }}).on('changeDate',function (ev) {
                        $paymentDateEl.hide();
                    }).data('datepicker');

            $('#paymentDateIcon').click(function () {
                $paymentDateEl.focus();
                return true;
            });
        </c:if>
    }

    function restructureTableHead(){
        var $tableList = $('#tableList');
        var thPaymentDate = "";
        var thCheckAll = "";
        if(${item.pojo.paymentStatus eq Constants.COST_PAYMENT_NOT_PAID}){
            thCheckAll = "<th class='table_header text-center middle-vertical' rowspan='2'><input style=\"width: 15px;\" type=\"checkbox\" class=\"\" name=\"allCheck\" id=\"allCheck\" onclick=\"checkAll('listForm', 'checkList', this)\"></th>";
        }else if(${item.pojo.paymentStatus eq Constants.COST_PAYMENT_PAID}){
            thPaymentDate = "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.payment_dateTime" /></th>";
        }

        var $tableHeadContent = $("<thead>" +
                                        "<tr>" +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="label.stt" /></th>" +
                                            thCheckAll +
                                            "<th class='table_header text-center middle-vertical' rowspan='2'><fmt:message key="payment.manager.table.payment_status" /></th>" +
                                            thPaymentDate +
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

    $('#btnChiTra').on('click', function(e){
        e.preventDefault();
        if( $(this).hasClass('disabled') ){
            return;
        } else {
            bootbox.confirm('<fmt:message key="label.confirm_title" />', '<fmt:message key="label.confirm_operation_content" />', '<fmt:message key="label.huy" />', '<fmt:message key="label.dong_y" />', function(r){
                if(r){
                    <c:if test="${item.pojo.paymentStatus eq Constants.COST_PAYMENT_NOT_PAID && not empty items.listResult && items.listResult.size() > 0}">
                    var $notPaidPaymentListEl = $("input[name='checkList']:checked");

                    if($notPaidPaymentListEl.length == 0){
                        return;
                    }

                    $('#crudaction').val('${Constants.ACTION_UPDATE}');
                    $('#listForm').submit();
                    </c:if>
                }
            });
        }
    })
</script>