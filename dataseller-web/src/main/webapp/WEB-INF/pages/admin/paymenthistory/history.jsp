<%@ page import="com.benluck.vms.mobifonedataseller.common.Constants" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<head>
    <title><fmt:message key="paymenthistory.heading"/></title>
    <meta name="menu" content="<fmt:message key="paymenthistory.heading" />"/>
    <link href="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">

    <style>
        #tablelist.mobile td.width_50_px{
            width: 50px;
        }
        #tablelist.mobile td.width_150_px{
            width: 150px;
        }
        #tablelist.mobile td.width_300_px{
            width: 500px;
        }
    </style>
</head>

<c:set var="prefix" value="/user"/>
<security:authorize access="hasAnyAuthority('ADMIN')">
    <c:set var="prefix" value="/admin"/>
</security:authorize>

<c:set var="formUrl" value="${prefix}/payment/payment-history.html" />

<div class="page-title">
    <div class="title_left">
        <h3><fmt:message key="paymenthistory.heading"/></h3>
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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="isdn"><fmt:message key="paymenthistory.isdn"/>
                        </label>

                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="isdn" path="pojo.isdn" cssClass="form-control "/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="tin"><fmt:message key="paymenthistory.tin"/>
                        </label>

                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <form:input id="tin" path="pojo.tin" cssClass="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="regDateFrom"><fmt:message key="paymenthistory.regdatetime_from"/></label>

                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class="input-append date">
                                <input name="regDateFrom" id="regDateFrom" class="prevent_type text-center form-control" type="text" value="<fmt:formatDate pattern="${datePattern}" value="${item.regDateFrom}" />" placeholder="${symbolDateEmpty}"/>
                                <span class="add-on" id="regDateFromIcon"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="regDateTo"><fmt:message key="paymenthistory.regdatetime_to"/></label>

                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class="input-append date">
                                <input name="regDateTo" id="regDateTo" class="prevent_type text-center form-control" type="text" value="<fmt:formatDate pattern="${datePattern}" value="${item.regDateTo}" />" placeholder="${symbolDateEmpty}"/>
                                <span class="add-on" id="regDateToIcon"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="staDateFrom"><fmt:message key="paymenthistory.stadatetime_from"/></label>

                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class="input-append date">
                                <input name="staDateFrom" id="staDateFrom" class="prevent_type text-center form-control" type="text" value="<fmt:formatDate pattern="${datePattern}" value="${item.staDateFrom}" />" placeholder="${symbolDateEmpty}"/>
                                <span class="add-on" id="staDateFromIcon"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="staDateTo"><fmt:message key="paymenthistory.stadatetime_to"/></label>

                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <div class="input-append date">
                                <input name="staDateTo" id="staDateTo" class="prevent_type text-center form-control" type="text" value="<fmt:formatDate pattern="${datePattern}" value="${item.staDateTo}" />" placeholder="${symbolDateEmpty}"/>
                                <span class="add-on" id="staDateToIcon"><i class="icon-calendar"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group last">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-success" onclick="javascript: resetForm();"><i class="fa fa-refresh" aria-hidden="true"></i> <fmt:message key="label.reset"/></a>
                            <a class="btn btn-primary" onclick="javascript: searchForm();"><i class="fa fa-search" aria-hidden="true"></i> <fmt:message key="label.search"/></a>
                            <c:if test="${item.crudaction == 'search' && item.listResult.size() > 0}">
                                <a class="btn btn-primary" onclick="javascript: exportExcel();"><i class="fa fa-file-excel-o" aria-hidden="true"></i> <fmt:message key="label.button.export"/></a>
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
                                <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}" partialList="true" sort="external" size="${items.totalItems}"
                                               defaultsort="0" id="tableList" pagesize="${items.maxPageItems}" export="false" class="table table-striped table-bordered" style="margin: 1em 0 1.5em;">
                                    <display:column headerClass="table_header text-center" titleKey="label.stt" class="text-center width_50_px" style="width: 5%">${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}</display:column>
                                    <display:column headerClass="table_header text-center" property="isdn" class="text-center width_150_px" titleKey="paymenthistory.isdn" style="width: 15%;" />
                                    <display:column headerClass="table_header text-center" property="name" class="text-center width_300_px" titleKey="paymenthistory.name" style="width: 35%;" />
                                    <display:column headerClass="table_header text-center" property="tin" class="text-center width_150_px" titleKey="paymenthistory.tin" style="width: 15%;" />
                                    <display:column headerClass="table_header text-center" titleKey="paymenthistory.regDate" class="text-center width_150_px" style="width: 15%;">
                                    <fmt:formatDate value="${tableList.regDateTime}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:column headerClass="table_header text-center" titleKey="paymenthistory.staDate" class="text-center width_150_px" style="width: 15%;">
                                    <fmt:formatDate value="${tableList.staDateTime}" pattern="${datePattern}" />
                                    </display:column>
                                    <display:setProperty name="paging.banner.item_name"><fmt:message
                                            key="display_table.footer.label.payment.item"/></display:setProperty>
                                    <display:setProperty name="paging.banner.items_name"><fmt:message
                                            key="display_table.footer.label.payment.item"/></display:setProperty>
                                </display:table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="please_choose_filter"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <input id="crudaction" type="hidden" name="crudaction" value="<%=Constants.ACTION_SEARCH%>"/>
</form:form>

<script type="text/javascript" src="<c:url value="/themes/admin/mCustomScrollBar/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        initScrollablePane();
        initDatePicker();
    });

    function initScrollablePane(){
        if($(window).width() > min_desktop_screen_width){
            return;
        }

        var $tableContainer = $('#tableListContainer');
        if($tableContainer.length){
            $('#tableList').addClass('mobile').width(1050);
            $tableContainer.mCustomScrollbar({axis:"x"});
        }
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
</script>