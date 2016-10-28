<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="chitradbh.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="chitradbh.heading_page" />"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
    <style>
        .tableVms th.order1 a {
            padding-right: 0px;
        }
    </style>
</head>
<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/chitradiembanhang.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="chitradbh.heading_page" /></h2>

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
                <form:form commandName="item" id="searchForm" action="${formUrl}" method="post" autocomplete="off" validate="validate">
                    <div class="col-md-14">
                        <div class="panel panel-default">
                            <security:authorize ifAnyGranted="ADMIN,BAOCAO">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                        <div class="col-sm-8">
                                            <form:select id="branchMenu" path="branchId" cssStyle="width: 250px;" onchange="javascript: ajaxGetDistrictList(); ajaxGetRetailDealerListByBranchId();">
                                                <option value=""><fmt:message key="label.all" /></option>
                                                <c:forEach items="${branchList}" var="branch">
                                                    <option <c:if test="${item.branchId eq branch.branch_Id}">selected="true"</c:if> value="${branch.branch_Id}">${branch.branch_name}</option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                            </security:authorize>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.quan_huyen" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="districtMenu" path="district.district_Id" cssStyle="width: 150px;" onchange="javascript: ajaxGetRetailDealerList();">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${districtList}" var="district">
                                                <option <c:if test="${item.district.district_Id eq district.district_Id}">selected="true"</c:if> value="${district.district_Id}">${district.district_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.diembanhang" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="retailDealerMenu" path="dealer_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
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
                                    <div class="col-sm-8">
                                        <form:input id="soEZ" path="pojo.ez_Isdn" cssClass="nohtml validate_phone_number form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ngay_tong_hop_tu" /></label>
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
                                    <label class="col-sm-2 control-label"><fmt:message key="label.ngay_tong_hop_den" /></label>
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
                                    <label class="col-sm-2 control-label"><fmt:message key="label.trang_thai_chi_tra" /></label>
                                    <div class="col-sm-8">
                                        <label><form:radiobutton path="pojo.payment_Status" value="-1"/>&nbsp;<fmt:message key="label.all" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.payment_Status" value="${Constants.TB_PTM_2015_REG_PAYMENT_STATUS_DA_CHI}"/>&nbsp;<fmt:message key="label.da_chi_tra" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.payment_Status" value="${Constants.TB_PTM_2015_REG_PAYMENT_STATUS_CHUA_CHI}"/>&nbsp;<fmt:message key="label.chua_chi_tra" /></label>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-8">
                                        <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                        <c:if test="${not empty items.listResult}">
                                            <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                            <security:authorize ifAnyGranted="ADMIN,CHINHANH">
                                                <a class="btn btn-primary mr5 btnChiTra" data-toggle="modal" data-target=".chiTraModal"><fmt:message key="label.chi_tra" /></a>
                                            </security:authorize>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult}">
                        <div id="reportTableContainer" style="width: 100%;">
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                           partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                           id="tableList" pagesize="${not empty hasFilter && hasFilter ? items.totalItems : items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 1720px;">
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                    ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center nowrap" sortable="false" title="<input type=\"checkbox\" name=\"allCheck\" id=\"allCheck\" onclick=\"checkAll('searchForm', 'checkList', this)\">" class="text-center" style="width: 20;">
                                    <c:if test="${(not empty tableList.soTienDuDK && vms:compare2Double(tableList.soTienDuDK, 0) > 0) && not empty tableList.payment_Status && tableList.payment_Status eq Constants.TB_PTM_2015_REG_PAYMENT_STATUS_CHUA_CHI}">
                                        <input type="checkbox" name="checkList" value="${tableList.retailDealer.dealer_Id}_${tableList.ez_Isdn}_${tableList.totalTrans_ThoaDKCT}_${fn:substring(tableList.sum_Date, 0, 10)}" onclick="checkAllIfOne('searchForm', 'checkList', this, 'allCheck')">
                                    </c:if>
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" property="retailDealer.branch_name" titleKey="label.chi_nhanh" class="nowrap" style="width: 250px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="retailDealer.district_name" titleKey="label.quan_huyen" class="nowrap" style="width: 250px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="retailDealer.dealer_code" titleKey="label.ma_diem_ban_hang" style="width: 200px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="retailDealer.dealer_name" titleKey="label.ten-diem_ban_hang" style="width: 250px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="ez_Isdn" titleKey="label.so_EZ" class="text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" titleKey="chitradbh.label.tong_tien_quy_doi" class="text-center" style="width: 300px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.tongTienQuyDoi}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="chitradbh.label.so_tien_du_dk" class="text-center" style="width: 250px;" >
                                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.soTienDuDK}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.chot_ky" class="text-center" style="width: 100px;" >
                                    <fmt:formatDate value="${tableList.sum_Date}" pattern="${datePattern}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.vi_tri_dang_ky" class="text-center" style="width: 200px;" >
                                    <c:choose>
                                        <c:when test="${not empty tableList.sum_Date}">
                                            <fmt:message key="label.da_chot_ky" />
                                        </c:when>
                                        <c:when test="${empty tableList.sum_Date}">
                                            <fmt:message key="label.chua_chot_ky" />
                                        </c:when>
                                    </c:choose>
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.trang_thai_chi_tra" class="text-center" style="width: 200px;" >
                                    <c:choose>
                                        <c:when test="${not empty tableList.payment_Status && tableList.payment_Status eq '1'}">
                                            <fmt:message key="label.chitra.da_chi_tra" />
                                        </c:when>
                                        <c:otherwise><fmt:message key="label.chitra.chua_chi_tra" /></c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.ngay_chi_tra" class="text-center" style="width: 150px;" >
                                    <fmt:formatDate value="${tableList.payment_Date}" pattern="${datePattern}" />
                                </display:column>

                                <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                                <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                            </display:table>
                        </div>
                        <div id="linksContainer">

                        </div>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />

                    <!-- Modal -->
                    <div class="modal fade chiTraModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                                    <h4 class="modal-title">Xác nhận chi trả </h4>
                                </div>
                                <div class="modal-body">
                                    <div class="panel-body">
                                        <div id="infoDiv" class="form-group">
                                            <label class="col-sm-2 control-label"></label>
                                            <div class="col-sm-8">
                                                <span id="msgInfoModalPayment" class="text-note"></span>
                                            </div>
                                        </div>
                                        <div id="startPaymentInfoDiv" class="form-group">
                                            <label class="col-sm-2 control-label"></label>
                                            <div class="col-sm-8">
                                                Ngày chi trả nên bắt đầu từ: <b><span id="ngayChiTraInfo" class="text-note"></span></b>
                                            </div>
                                        </div>
                                        <div id="startTotalPaymentInfoDiv" class="form-group">
                                            <label class="col-sm-2 control-label"></label>
                                            <div class="col-sm-8">
                                                Tổng tiền thanh toán: <b><span id="tongTienThanhToan" class="text-note"></span></b>
                                            </div>
                                        </div>
                                        <div id="ngayChiTraInputDate" class="form-group">
                                            <label class="col-sm-2 control-label"><fmt:message key="label.ngay_chi_tra" /></label>
                                            <div class="col-sm-8">
                                                <div class="input-append date" >
                                                    <input name="ngayChiTra" id="ngayChiTra" class="prevent_type text-center form-control" type="text" placeholder="${symbolDateEmpty}"/>
                                                    <span class="add-on" id="ngayChiTraIcon"><i class="icon-calendar"></i></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label"></label>
                                            <div class="col-sm-8">
                                                <a id="submitPaymentBtn" class="btn btn-success btnSubmit" onclick="javascript: validateBeforeSubmit(this);"><fmt:message key="label.chi_tra" /></a>&nbsp;
                                                <a class="btn btn-danger" onclick="javascript: hideModel(this);"><fmt:message key="label.huy" /></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script type="text/javascript">
    var allow_payment = false;
    $(document).ready(function(){
        setActiveMenu4Admin('#tbptm2015PaymentChiKhuyenKhichTab');
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

        $('#reportTableContainer').mCustomScrollbar({axis:"x"});

        jQuery.validator.addClassRules('validate_phone_number', {
            validatePhoneNumber: true
        });

        jQuery.validator.addMethod("validatePhoneNumber", function () {
            return validatePhoneNumber($.trim($('#soEZ').val()));
        }, "<fmt:message key="user.msg.invalid_ez"/>");

        checkMovePageLinks();
    });

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
            $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
        }
    }

    function hideModel(fromEl){
        $(fromEl).closest('.chiTraModal').modal('hide');
    }

    function findLastestSumDate(){
        var lastestSumDate = null;
        $('#tableList tbody tr').each(function(index, trEl){
            var hasChosen4PaymentEl = $(trEl).find('td').eq(1).find("input[type='checkbox']");
            if(typeof hasChosen4PaymentEl != 'undefined' && $(hasChosen4PaymentEl).is(':checked')){
                var sumDateStr = $(trEl).find('td').eq(9).html();
                if($.trim(sumDateStr) != ''){
                    var sumDate = new Date(sumDateStr.split('/')[2], sumDateStr.split('/')[1], sumDateStr.split('/')[0]);
                    if(lastestSumDate == null || (lastestSumDate != null && sumDate > lastestSumDate)){
                        lastestSumDate = sumDate;
                    }
                }
            }
        });
        return lastestSumDate;
    }

    function calTotalPayment() {
        var totalPayment = 0;
        $('#tableList tbody tr').each(function(index, trEl){
            var hasChosen4PaymentEl = $(trEl).find('td').eq(1).find("input[type='checkbox']");
            if(typeof hasChosen4PaymentEl != 'undefined' && $(hasChosen4PaymentEl).is(':checked')){
                var soTienDuDKStr = $.trim($(trEl).find('td').eq(8).html());
                if($.trim(soTienDuDKStr) != ''){
                    totalPayment += eval($.trim(soTienDuDKStr).replace(/,+/g,''));
                }
            }
        });
        return totalPayment;
    }

    $('.chiTraModal').on('show.bs.modal', function (e) {
        if(!checkIfNoChecked4Payment()){
            $('#msgInfoModalPayment').html('<fmt:message key="chitradbh.errors.required_at_least_one_dbh" />');
            $('#infoDiv').removeClass('hide');
            $('#infoDiv').removeClass('hide');
            $('#ngayChiTraInputDate').addClass('hide');
            $('#submitPaymentBtn').addClass('hide');
            $('#startPaymentInfoDiv').addClass('hide');
            $('#startTotalPaymentInfoDiv').addClass('hide');
            allow_payment = false;
        }else{
            $('#infoDiv').addClass('hide');
            $('#msgInfoModalPayment').html('');
            $('#startPaymentInfoDiv').removeClass('hide');
            $('#startTotalPaymentInfoDiv').removeClass('hide');
            $('#ngayChiTraInputDate').removeClass('hide');
            $('#submitPaymentBtn').removeClass('hide');
            var lastestSumDate = findLastestSumDate();
            if(lastestSumDate != null){
                $('#ngayChiTraInfo').html(lastestSumDate.getDate() + '/' + lastestSumDate.getMonth() + '/' + lastestSumDate.getFullYear());
                allow_payment = true;
            }else{
                $('#ngayChiTraInfo').html('Error Javascript!');
                $('#startPaymentInfoDiv').addClass('hide');
                $('#startTotalPaymentInfoDiv').addClass('hide');
                allow_payment = false;
            }
            initializePaymentDate();

            var totalPayment = calTotalPayment();
            if(totalPayment != null){
                allow_payment = true;
                $('#tongTienThanhToan').html(totalPayment);
                currencyFormat4El('#tongTienThanhToan');
                $('#submitPaymentBtn').removeClass('hide');
            }else{
                $('#tongTienThanhToan').html('0');
                $('#submitPaymentBtn').addClass('hide');
                allow_payment = false;
            }
        }
    });

    function initializePaymentDate(){
        if(allow_payment){
            $("#ngayChiTra").datepicker('destroy');
            var lastestSumDate = $('#ngayChiTraInfo').html();
            var startDate = new Date(lastestSumDate.split('/')[2], lastestSumDate.split('/')[1] - 1, lastestSumDate.split('/')[0]);

            var ngayChiTraVar = $("#ngayChiTra").datepicker({
                dateFormat: 'dd/mm/yy',
                minDate: startDate,
                onRender: function (date) {
                }}).on('changeDate',function (ev) {
                        ngayChiTraVar.hide();
                    }).data('datepicker');

            $('#ngayChiTraIcon').click(function () {
                $('#ngayChiTra').focus();
                return true;
            });
        }else{
            $("#ngayChiTra").datepicker('destroy');
        }
    }

    function validateBeforeSubmit(btnEl){
        var modal = $(btnEl).closest('.chiTraModal');
        if(!checkIfNoChecked4Payment()){
            $('#msgInfoModalPayment').html('<fmt:message key="chitradbh.errors.required_at_least_one_dbh" />');
            $('#infoDiv').removeClass('hide');
        }else{
            $('#msgInfoModalPayment').html('');
            $('#infoDiv').addClass('hide');
            if($.trim($(modal).find('#ngayChiTra').val()) === ''){
                $('#msgInfoModalPayment').html('<fmt:message key="chitradbh.errors.required_ngay_chi_tra" />');
                $('#infoDiv').removeClass('hide');
            }else{
                if(allow_payment){
                    $(btnEl).addClass('hide');
                    $('#crudaction').val('chi-tra');
                    $('#searchForm').submit();
                }
            }
        }
    }

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
            $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
        }
    }

    function submitForm(){
        if($('#searchForm').valid()){
            $('#crudaction').val('search');
            $('#searchForm').submit();
        }
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#searchForm').submit();
    }

    function checkIfNoChecked4Payment(){
        var result = false;
        $("#tableList input[name*='checkList']").each(function(index, el){
            if($(el).is(':checked')){
                result = true;
            }
        });
        return result;
    }

    <security:authorize ifAnyGranted="ADMIN">
    function ajaxGetDistrictList(){
        $('#districtMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#districtMenu');
        $.ajax({
            url: '<c:url value="/ajax/thuebaotm/getByBranchId.html" />',
            type: 'get',
            dataType: 'json',
            data: {chiNhanh: $('#branchMenu').val()},
            success: function(res){
                if(res.districtList != null){
                    $(res.districtList).each(function(index, el){
                        $('#districtMenu').append("<option value=" +el.district_Id+ ">" +el.district_name+ "</option>");
                    });
                }
            }
        });
    }
    </security:authorize>

    function ajaxGetRetailDealerList(){
        $('#retailDealerMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#retailDealerMenu');
        var branchId = '';
        <security:authorize ifNotGranted="ADMIN">
        branchId = '<%=SecurityUtils.getPrincipal().getBranchId()%>';
        </security:authorize>
        <security:authorize ifAnyGranted="ADMIN">
        branchId = $('#branchMenu').val();
        </security:authorize>
        $.ajax({
            url: '<c:url value="/ajax/thuebaotm/getRetailDealerList.html" />',
            type: 'get',
            dataType: 'json',
            data: {branchId: branchId, districtId: $('#districtMenu').val()},
            success: function(res){
                if(res.retailDealerList != null){
                    $(res.retailDealerList).each(function(index, el){
                        $('#retailDealerMenu').append("<option value=" +el.dealer_Id+ ">" + $.trim(el.dealer_code) + ' - ' + $.trim(el.dealer_name) +  "</option>");
                    });
                }
            }
        });
    }

    function ajaxGetRetailDealerListByBranchId(){
        $('#retailDealerMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#retailDealerMenu');
        $.ajax({
            url: '<c:url value="/ajax/thuebaotm/getRetailDealerListByBranchId.html" />',
            type: 'get',
            dataType: 'json',
            data: {branchId: $('#branchMenu').val()},
            success: function(res){
                if(res.retailDealerList != null){
                    $(res.retailDealerList).each(function(index, el){
                        $('#retailDealerMenu').append("<option value=" +el.dealer_Id+ ">" + $.trim(el.dealer_code) + ' - ' + $.trim(el.dealer_name) + "</option>");
                    });
                }
            }
        });
    }
</script>
</body>
</html>