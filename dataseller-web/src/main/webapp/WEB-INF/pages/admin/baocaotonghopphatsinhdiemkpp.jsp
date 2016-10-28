<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="admin.tracuuthongtinkpp.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="admin.tracuuthongtinkpp.heading_page" />"/>
</head>

<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="TONGDAI">
    <c:set var="prefix" value="/tongdai" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/baocaochitietphatsinh.html"/>
<c:url var="viewDetailByItemUrl" value="${prefix}/baocaochitiethangmucphatsinhdiemkpp.html" />
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.tracuuthongtinkpp.heading_page" /></h2>

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
                <form:form commandName="item" id="reportForm" action="${formUrl}" method="post" autocomplete="off" validate="validate">
                    <div class="col-md-14">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.diembanhang" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="retailDealerMenu" path="dealer_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.select" /></option>
                                            <c:forEach items="${retailDealerList}" var="retailDealer">
                                                <option <c:if test="${item.dealer_Id eq retailDealer.dealer_Id}">selected="true"</c:if> value="${retailDealer.dealer_Id}">${fn:trim(retailDealer.dealer_code)} - ${fn:trim(retailDealer.dealer_name)}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.so_EZ" /></label>
                                    <div class="col-sm-8"><form:input path="soEZ" cssClass="nohtml form-control validate_phone_number" /></div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.hang_muc_phat_sinh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="itemMenu" path="item_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${itemList}" var="itemVar">
                                                <option <c:if test="${item.item_Id eq itemVar.item_Id}">selected="true"</c:if> value="${itemVar.item_Id}">${itemVar.item_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.tu_ngay" /></label>
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
                                    <label class="col-sm-2 control-label"><fmt:message key="label.den_ngay" /></label>
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
                                    <label class="col-sm-2s control-label"></label>
                                    <div class="col-sm-8">
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                        <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                        <c:if test="${not empty items.listResult}">
                                            <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult}">
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                       id="tableList" pagesize="${items.maxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 100%;">
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 5%;" >
                                ${tableList_rowNum + (page * Constants.MAXPAGEITEMS)}
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" property="branch_Name" titleKey="label.chi_nhanh" class="nowrap" style="width: 250px;" />
                            <display:column headerClass="table_header_center" sortable="false" property="district_Name" titleKey="label.quan_huyen" class="nowrap" style="width: 150px;" />
                            <display:column headerClass="table_header_center" sortable="false" property="tenLoaiPS" titleKey="Loại phát sinh điểm" class="text-center nowrap" style="width: 200px;" />
                            <display:column headerClass="table_header_center" sortable="false" titleKey="Kỳ" class="text-center nowrap" style="width: 50px;" >
                                <fmt:formatNumber type="number" value="${tableList.cycle}" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="Điểm tích luỹ" class="text-center nowrap" style="width: 100px;" >
                                <fmt:formatNumber type="number" value="${tableList.prom_point}" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="Số tiền tương ứng" class="text-center nowrap" style="width: 100px;" >
                                <fmt:formatNumber type="number" value="${tableList.soTienTuongUng}" maxFractionDigits="2" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="Số mã dự thưởng" class="text-center nowrap" style="width: 100px;" >
                                <fmt:formatNumber type="number" value="${tableList.soMaDuThuong}" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.action" class="text-center nowrap" style="width: 100px;" >
                                <fmt:formatDate var="fromDateVar" value="${item.fromDateTime}" pattern="${datePattern}" />
                                <fmt:formatDate var="toDateVar" value="${item.toDateTime}" pattern="${datePattern}" />
                                &nbsp;<a class="detail" href="${viewDetailByItemUrl}?dealer_Id=${tableList.dealer_Id}&item_Id=${tableList.item_Id}&cycle=${tableList.cycle}&fromDate=${fromDateVar}&toDate=${toDateVar}&fromStart=1"><fmt:message key="label.chi_tiet" /></a>
                            </display:column>
                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.hang_muc_ps"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.hang_muc_ps"/></display:setProperty>
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
        setActiveMenu4Admin('#menuDoiTienDBHTab', '#traCuuThongTinKPPTab');

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
            return validatePhoneNumber($.trim($('#soEZ').val()));
        }, "<fmt:message key="user.msg.invalid_phone_number"/>");
    });

    function reset(){
        selectFirstItemSelect2('#retailDealerMenu');
        selectFirstItemSelect2('#itemMenu');
        $('#fromDate').val('');
        $('#toDate').val('');
        $('#soEZ').val('');
    }

    function submitForm(){
        if($('#reportForm').valid()){
            $('#crudaction').val('search');
            $('#reportForm').submit();
        }
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }
</script>
</body>