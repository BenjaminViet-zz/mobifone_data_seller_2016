<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="admin.baocaochitiethangmucpsc_kpp_ban_truc_tiep_btg_va_psc.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="admin.baocaochitiethangmucpsc_kpp_ban_truc_tiep_btg_va_psc.heading_page" />"/>
</head>

<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/baocaochitiethangmucpsc_ban_truc_tiep_btg_va_psc_theochinhanh.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.baocaochitiethangmucpsc_kpp_ban_truc_tiep_btg_va_psc.heading_page" /></h2>

</div>
<div class="contentpanel">
    <div class="panel panel-default">

        <div class="panel-body panel-body-nopadding">
            <c:if test ="${not empty messageResponse}">
                <div class="alert alert-${alertType}">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                        ${messageResponse}
                </div>
            </c:if>
            <div class="row-fluid report-filter">
                <form:form commandName="item" id="searchForm" action="${formUrl}" method="post" autocomplete="off">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="branchMenu" path="branch_Id" cssStyle="width: 250px;" >
                                            <security:authorize ifAnyGranted="ADMIN,BAOCAO">
                                                <option value=""><fmt:message key="label.all" /></option>
                                            </security:authorize>
                                            <c:forEach items="${branchList}" var="branch">
                                                <option <c:if test="${item.branch_Id eq branch.branch_Id}">selected="true"</c:if> value="${branch.branch_Id}">${branch.branch_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.tu_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="fromDate" id="fromDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.fromDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.den_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="toDate" id="toDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.toDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                    <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                    <c:if test="${not empty items.listResult}">
                                        <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult}">
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                       id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 10%;" >
                                ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" property="branch_Name" titleKey="label.chi_nhanh" class="nowrap" style="width: 40%;" />
                            <display:column headerClass="table_header_center" sortable="false" titleKey="" class="nowrap text-right" style="width: 10%;;" >
                                <fmt:formatNumber type="number" value="${tableList.cuocPS_thoai}" maxFractionDigits="2" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="" class="nowrap text-right" style="width: 10%;" >
                                <fmt:formatNumber type="number" value="${tableList.cuocPS_sms}" maxFractionDigits="2" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="" class="nowrap text-right" style="width: 10%;" >
                                <fmt:formatNumber type="number" value="${tableList.cuocPS_data}" maxFractionDigits="2" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="" class="nowrap text-right" style="width: 10%;" >
                                <fmt:formatNumber type="number" value="${tableList.cuocPS_khac}" maxFractionDigits="2" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="" class="nowrap text-right" style="width: 10%;" >
                                <fmt:formatNumber type="number" value="${tableList.cuocPS_total}" maxFractionDigits="2" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="" class="nowrap text-right" style="width: 10%;" >
                                <fmt:formatNumber type="number" value="${tableList.khuyen_mai}" maxFractionDigits="2" />
                            </display:column>
                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                        </display:table>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#tdcg2014BCChiTietHangMucPSCTab');

        var fromDateVar = $("#fromDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromDateVar.hide();
                }).data('datepicker');

        $('#fromDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        var toDateVar = $("#toDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toDateVar.hide();
                }).data('datepicker');

        $('#toDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        jQuery.validator.addClassRules('validate_phone_number', {
            validatePhoneNumber: true
        });

        jQuery.validator.addMethod("validatePhoneNumber", function () {
            return validatePhoneNumber($.trim($('#sothuebao').val()));
        }, "<fmt:message key="user.msg.invalid_phone_number"/>");

        rebuildTableHeaders();
    });

    function rebuildTableHeaders(){
        $('#tableList thead tr').remove();
        $('#tableList thead').html("<tr>" +
                "<th rowspan='2' class='table_header_center '>STT</th>" +
                "<th rowspan='2' class='table_header_center '>Chi nhánh</th>" +
                "<th colspan='6' class='table_header_center '>Cước Phát Sinh / DTTT</th>" +
                "</tr>" +
                "tr>" +
                "<th class='table_header_center '>Thoại</th>" +
                "<th class='table_header_center '>SMS</th>" +
                "<th class='table_header_center '>Data</th>" +
                "<th class='table_header_center '>Khác</th>" +
                "<th class='table_header_center '>Tổng</th>" +
                "<th class='table_header_center '>Khuyến Mãi</th>" +
                "</tr>");
    }

    function submitForm(){
        $('#crudaction').val('search');
        $('#searchForm').submit();
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#searchForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#branchMenu');
        selectFirstItemSelect2('#itemMenu');
        $('#fromDate').val('');
        $('#toDate').val('');
    }
</script>
</body>