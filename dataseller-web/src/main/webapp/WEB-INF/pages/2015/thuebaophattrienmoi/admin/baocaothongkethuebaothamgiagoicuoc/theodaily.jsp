<%@ page import="com.benluck.vms.mobifonedataseller.security.util.SecurityUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2/26/15
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="baocaothongkethuebaoTGGC.headingpage" /></title>
    <meta name="heading" content="<fmt:message key="baocaothongkethuebaoTGGC.headingpage" />"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>
<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/thuebaophattrienmoi/baocaothongkethuebao_thamgiagoicuoc_theodaily.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="baocaothongkethuebaoTGGC.headingpage" /></h2>

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
                    <div class="col-md-10">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.loai_goi_KM" /></label>
                                    <div class="col-sm-7">
                                        <form:select id="packageMenu" path="pojo.promPackage.package_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${promPackage}" var="promPackage">
                                                <option <c:if test="${item.pojo.promPackage.package_Id eq promPackage.package_Id}">selected="true"</c:if> value="${promPackage.package_Id}">${promPackage.package_Name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-7">
                                        <form:select id="branchMenu" path="pojo.branch.branch_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetDistrictList(); ajaxGetRetailDealerListByBranchId();">
                                            <security:authorize ifAnyGranted="ADMIN,BAOCAO">
                                                <option value=""><fmt:message key="label.all" /></option>
                                            </security:authorize>
                                            <c:forEach items="${branchList}" var="branch">
                                                <option <c:if test="${item.pojo.branch.branch_Id eq branch.branch_Id}">selected="true"</c:if> value="${branch.branch_Id}">${branch.branch_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.quan_huyen" /></label>
                                    <div class="col-sm-7">
                                        <form:select id="districtMenu" path="pojo.district.district_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetRetailDealerList();">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${districtList}" var="district">
                                                <option <c:if test="${item.pojo.district.district_Id eq district.district_Id}">selected="true"</c:if> value="${district.district_Id}">${district.district_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.diembanhang" /></label>
                                    <div class="col-sm-7">
                                        <form:select id="retailDealerMenu" path="pojo.retailDealer.dealer_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${retailDealerList}" var="retailDealer">
                                                <option <c:if test="${item.pojo.retailDealer.dealer_Id eq retailDealer.dealer_Id}">selected="true"</c:if> value="${retailDealer.dealer_Id}">${fn:trim(retailDealer.dealer_code)}-${fn:trim(retailDealer.dealer_name)}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.so_EZ" /></label>
                                    <div class="col-sm-7">
                                        <form:input id="soEZ" path="pojo.soEZ" cssClass="nohtml validate_phone_number form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.ngay_tong_hop_tu" /></label>
                                    <div class="col-sm-7">
                                        <div class="input-append date" >
                                            <input name="fromDate" id="fromDate"  class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.fromDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.ngay_tong_hop_den" /></label>
                                    <div class="col-sm-7">
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
                                    <label class="col-sm-3 control-label"><fmt:message key="label.ngay_giao_dich_tu" /></label>
                                    <div class="col-sm-7">
                                        <div class="input-append date" >
                                            <input name="fromTransDate" id="fromTransDate"  class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.fromTransDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromTransDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.ngay_giao_dich_den" /></label>
                                    <div class="col-sm-7">
                                        <div class="input-append date" >
                                            <input name="toTransDate" id="toTransDate" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.toTransDate}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toTransDateIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.tinh_trang_giao_dich" /></label>
                                    <div class="col-sm-7">
                                        <label><form:radiobutton path="pojo.tinhTrang" value="-1"/>&nbsp;<fmt:message key="label.all" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.tinhTrang" value="${Constants.TINH_TRANG_THANH_CONG}"/>&nbsp;<fmt:message key="label.thanh_cong" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.tinhTrang" value="${Constants.TINH_TRANG_KHONG_THANH_CONG}"/>&nbsp;<fmt:message key="label.khong_thanh_cong" /></label>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><fmt:message key="label.tinh_trang_chi_tra" /></label>
                                    <div class="col-sm-7">
                                        <label><form:radiobutton path="pojo.tinhTrangChiTra" value="-1"/>&nbsp;<fmt:message key="label.all" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.tinhTrangChiTra" value="${Constants.TINH_TRANG_CHUA_XET}"/>&nbsp;<fmt:message key="label.chua_xet" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.tinhTrangChiTra" value="${Constants.TINH_TRANG_DUOC_CHI_TRA}"/>&nbsp;<fmt:message key="label.duoc_chi_tra" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.tinhTrangChiTra" value="${Constants.TINH_TRANG_KHONG_DUOC_CHI_TRA}"/>&nbsp;<fmt:message key="label.khong_duoc_chi_tra" /></label>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <div class="col-sm-7">
                                        <a class="btn btn-info" onclick="javascript: filterForm();"><fmt:message key="label.search" /></a>
                                        <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                        <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                    </div>
                                </div>
                            </div>
                            <hr/>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty items.listResult}">
                    <div id="reportTableContainer" style="width: 100%;">
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                       id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 2800px;">
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" property="branch.branch_name" titleKey="label.chi_nhanh" class="nowrap" style="width: 150px;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" property="district.district_name" titleKey="label.quan_huyen" class="nowrap" style="width: 150px;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" property="retailDealer.dealer_code" titleKey="label.ma_diem_ban_hang" class="nowrap" style="width: 150px;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" property="retailDealer.dealer_name" titleKey="label.diem_ban_hang" class="nowrap" style="width: 150px;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" property="soEZ" titleKey="label.so_EZ" class="nowrap text-center" style="width: 150px;" />
                            <display:column headerClass="table_header_center nowrap" sortable="false" property="soThueBao" titleKey="label.thue_bao" class="nowrap text-center" style="width: 150px;" >
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay_kich_hoat" class="nowrap text-center" style="width: 150px;" >
                                <fmt:formatDate value="${tableList.registrationTrans.active_datetime}" pattern="${datePattern}" />
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" property="promPackage.package_Name" titleKey="label.goi_cuoc" class="nowrap" style="width: 150px;" >
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.thoi_gian_dang_ky" class="nowrap text-center" style="width: 150px;" >
                                <fmt:formatDate value="${tableList.thoiGianDK}" pattern="${dateTimePattern}" />
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.ngay_tong_hop" class="nowrap text-center" style="width: 150px;" >
                                <fmt:formatDate value="${tableList.ngayTongHop}" pattern="${datePattern}" />
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.vi_tri_dang_ky" class="nowrap text-center" style="width: 150px;" >
                                <c:choose>
                                    <c:when test="${tableList.registrationTrans.reg_Position eq '1'}">
                                        <fmt:message key="label.hop_le" />
                                    </c:when>
                                    <c:when test="${tableList.registrationTrans.reg_Position eq '0'}">
                                        <fmt:message key="label.khong_hop_le" />
                                    </c:when>
                                </c:choose>
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.tinhtrang" class="nowrap" style="width: 200px;" >
                                <c:choose>
                                    <c:when test="${item.pojo.tinhTrang ne -1 || item.pojo.tinhTrangChiTra ne -1}">
                                        <c:if test="${empty item.pojo.tinhTrangChiTra || item.pojo.tinhTrangChiTra eq -1}">
                                            <c:if test="${item.pojo.tinhTrang eq Constants.TINH_TRANG_KHONG_THANH_CONG}">
                                                <fmt:message key="label.khong_thanh_cong"/>
                                            </c:if>
                                            <c:if test="${item.pojo.tinhTrang eq Constants.TINH_TRANG_THANH_CONG}">
                                                <fmt:message key="label.thanh_cong"/>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${item.pojo.tinhTrangChiTra eq Constants.TINH_TRANG_CHUA_XET}">
                                            <fmt:message key="label.chua_xet"/>
                                        </c:if>
                                        <c:if test="${item.pojo.tinhTrangChiTra eq Constants.TINH_TRANG_KHONG_DUOC_CHI_TRA}">
                                            <fmt:message key="label.khong_duoc_chi_tra"/>
                                        </c:if>
                                        <c:if test="${item.pojo.tinhTrangChiTra eq Constants.TINH_TRANG_DUOC_CHI_TRA}">
                                            <fmt:message key="label.duoc_chi_tra"/>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${not empty tableList.tinhTrang && tableList.tinhTrang ne Constants.TINH_TRANG_THANH_CONG}">
                                                <fmt:message key="label.khong_thanh_cong" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${not empty tableList.promConditionStatus && (tableList.promConditionStatus eq Constants.PROM_CONDITION_STATUS_2 || tableList.promConditionStatus eq Constants.PROM_CONDITION_STATUS_0)}">
                                                        <fmt:message key="label.thanh_cong_nhung_khong_duoc_chi_tra"/>
                                                    </c:when>
                                                    <c:when test="${not empty tableList.promConditionStatus && tableList.promConditionStatus eq Constants.PROM_CONDITION_STATUS_1}">
                                                        <fmt:message key="label.duoc_chi_tra"/>
                                                    </c:when>
                                                    <c:otherwise>${tableList.promConditionError}</c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.lydo" style="width: 150px;" >
                                <c:choose>
                                    <c:when test="${not empty tableList.tinhTrang && tableList.tinhTrang eq Constants.TINH_TRANG_KHONG_THANH_CONG}">
                                        ${tableList.transError}
                                    </c:when>
                                    <c:otherwise>${tableList.promConditionError}</c:otherwise>
                                </c:choose>
                            </display:column>
                            <display:column headerClass="table_header_center nowrap"  property="serial" titleKey="label.serial" class="nowrap text-center" style="width: 150px;" />
                            <display:column headerClass="table_header_center nowrap"  property="registrationTrans.sales_shop_code" titleKey="label.ma_xuat_kho" class="nowrap text-center" style="width: 150px;" />
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.thoai" class="text-center" style="width: 150px;" >
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.registrationTrans.calling_amount}" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.sms" class="text-center" style="width: 150px;" >
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.registrationTrans.sms_amount}" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.data" class="text-center" style="width: 150px;" >
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.registrationTrans.data_amount}" />
                            </display:column>
                            <display:column headerClass="table_header_center" sortable="false" titleKey="label.khac" class="text-center" style="width: 150px;" >
                                <fmt:formatNumber type="number" maxFractionDigits="2" value="${tableList.registrationTrans.others_amount}" />
                            </display:column>
                            <display:column headerClass="table_header_center nowrap"  property="registrationTrans.event_code" titleKey="label.ma_su_kiem" class="nowrap text-center" style="width: 150px;" />
                            <display:column headerClass="table_header_center nowrap"  property="registrationTrans.event_pos_code" titleKey="label.ma_diem_ban_hang1" class="nowrap text-center" style="width: 150px;" />
                            <display:column headerClass="table_header_center nowrap"  property="registrationTrans.event_pos_name" titleKey="label.ten-diem_ban_hang1" class="nowrap" style="width: 150px;" />


                            <display:setProperty name="paging.banner.item_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                            <display:setProperty name="paging.banner.items_name"><fmt:message key="display_table.footer.label.du_lieu"/></display:setProperty>
                        </display:table>
                    </div>
                    <div id="linksContainer">

                    </div>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#tbptm2015ReportThongKeThueBaoThamGiaGoiCuocTab');

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

        var fromTransDate = $("#fromTransDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromTransDate.hide();
        }).data('datepicker');

        $('#fromTransDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        var toTransDate = $("#toTransDate").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toTransDate.hide();
        }).data('datepicker');

        $('#toTransDateIcon').click(function () {
            $('#signedDate').focus();
            return true;
        });

        jQuery.validator.addClassRules('validate_phone_number', {
            validatePhoneNumber: true
        });

        jQuery.validator.addMethod("validatePhoneNumber", function () {
            return validatePhoneNumber($.trim($('#soEZ').val()));
        }, "<fmt:message key="user.msg.invalid_ez"/>");

        $('#reportTableContainer').mCustomScrollbar({axis:"x"});
        checkMovePageLinks();
        rebuildTableHeaders();
    });

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
            $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
        }
    }


    function rebuildTableHeaders(){
        $('#tableList thead tr').remove();
        $('#tableList thead').html("<tr>" +
        "<th rowspan='2' class='table_header_center '>STT</th>" +
        "<th rowspan='2' class='table_header_center '>Chi nhánh</th>" +
        "<th rowspan='2' class='table_header_center '>Quận huyện</th>" +
        "<th rowspan='2' class='table_header_center nowrap'>Mã điểm bán hàng</th>" +
        "<th rowspan='2' class='table_header_center nowrap'>Điểm bán hàng</th>" +
        "<th rowspan='2' class='table_header_center '>Số EZ</th>" +
        "<th rowspan='2' class='table_header_center '>Thuê bao</th>" +
        "<th rowspan='2' class='table_header_center '>Ngày kích hoat</th>" +
        "<th rowspan='2' class='table_header_center '>Gói cước</th>" +
        "<th rowspan='2' class='table_header_center '>Thời gian đăng ký</th>" +
        "<th rowspan='2' class='table_header_center '>Ngày tổng hợp</th>" +
        "<th rowspan='2' class='table_header_center '>Vị trí đăng ký</th>" +
        "<th rowspan='2' class='table_header_center '>Tình trạng</th>" +
        "<th rowspan='2' class='table_header_center '>Lý do</th>" +
        "<th rowspan='2' class='table_header_center '>Serial</th>" +
        "<th rowspan='2' class='table_header_center '>Mã xuất kho</th>" +
        "<th colspan='4' class='table_header_center nowrap'>Sản lượng tiêu thụ</th>" +
        "<th colspan='3' class='table_header_center nowrap'>Thông tin Sự kiện</th>" +
        "</tr>" +
        "<tr>" +
        "<th class='table_header_center '>Thoại</th>" +
        "<th class='table_header_center '>SMS</th>" +
        "<th class='table_header_center '>Data</th>" +
        "<th class='table_header_center '>Khác</th>" +
        "<th class='table_header_center '>Mã sự kiện</th>" +
        "<th class='table_header_center '>Mã điểm bán hàng</th>" +
        "<th class='table_header_center '>Tên điểm bán hàng</th>" +
        "</tr>");
    }

    function filterForm(){
        $('#crudaction').val('search');
        $('#reportForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#packageMenu')
        selectFirstItemSelect2('#branchMenu')
        selectFirstItemSelect2('#retailDealerMenu')
        selectFirstItemSelect2('#districtMenu')
        $('#fromDate').val('');
        $('#toDate').val('');
        $('#fromPaymentDate').val('');
        $('#toPaymentDate').val('');
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#reportForm').submit();
    }
    function ajaxGetDistrictList() {
        $('#districtMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#districtMenu');
        $.ajax({
            url: '<c:url value="/ajax/thuebaotm/getByBranchId.html" />',
            type: 'get',
            dataType: 'json',
            data: {chiNhanh: $('#branchMenu').val()},
            success: function (res) {
                if (res.districtList != null) {
                    $(res.districtList).each(function (index, el) {
                        $('#districtMenu').append("<option value=" + el.district_Id + ">" + el.district_name + "</option>");
                    });
                }
            }
        });
    }

    function ajaxGetRetailDealerListByBranchId() {
        $('#retailDealerMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#retailDealerMenu');
        $.ajax({
            url: '<c:url value="/ajax/thuebaotm/getRetailDealerListByBranchId.html" />',
            type: 'get',
            dataType: 'json',
            data: {branchId: $('#branchMenu').val()},
            success: function (res) {
                if (res.retailDealerList != null) {
                    $(res.retailDealerList).each(function (index, el) {
                        $('#retailDealerMenu').append("<option value=" + el.dealer_Id + ">" + $.trim(el.dealer_code) + ' - ' + $.trim(el.dealer_name) + "</option>");
                    });
                }
            }
        });
    }

    function ajaxGetRetailDealerList(){
        $('#retailDealerMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#retailDealerMenu');
        var branchId = '';
        <security:authorize ifNotGranted="ADMIN,BAOCAO">
        branchId = '<%=SecurityUtils.getPrincipal().getBranchId()%>';
        </security:authorize>
        <security:authorize ifAnyGranted="ADMIN,BAOCAO">
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
</script>
</body>