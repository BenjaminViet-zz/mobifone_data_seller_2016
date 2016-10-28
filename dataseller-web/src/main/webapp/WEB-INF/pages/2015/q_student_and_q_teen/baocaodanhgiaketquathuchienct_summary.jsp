<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="admin.baocaodanhgiaketquathuchienct.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="admin.baocaodanhgiaketquathuchienct.heading_page" />"/>
</head>

<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/qstudent/baocaodanhgiaketquathuchienct.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.baocaodanhgiaketquathuchienct.heading_page" /></h2>

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
                                    <label class="col-sm-12 control-label"><span class="text-note">Dữ liệu danh sách tham gia chương trình có từ ngày 01/03/2015</span></label>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="branchMenu" path="branchId" cssStyle="width: 250px;" onchange="javascript: ajaxGetDistrictList(); ">
                                            <security:authorize ifAnyGranted="ADMIN,BAOCAO">
                                                <option value=""><fmt:message key="label.all" /></option>
                                            </security:authorize>
                                            <c:forEach items="${branchList}" var="branch">
                                                <option <c:if test="${item.branchId eq branch.branch_Id}">selected="true"</c:if> value="${branch.branch_Id}">${branch.branch_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.quan_huyen" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="districtMenu" path="districtId" cssStyle="width: 250px;" >
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${districtList}" var="district">
                                                <option <c:if test="${item.districtId eq district.district_Id}">selected="true"</c:if> value="${district.district_Id}">${district.district_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="admin.baocaodanhgiaketquathuchienct.label.thoi_gian_tham_gia_tu_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="fromDateThamGia" id="fromDateThamGia" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.fromDateThamGia}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateThamGiaIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="admin.baocaodanhgiaketquathuchienct.label.thoi_gian_tham_gia_den_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="toDateThamGia" id="toDateThamGia" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.toDateThamGia}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toDateThamGiaIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="admin.baocaodanhgiaketquathuchienct.label.thoi_gian_bao_cao_tu_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="fromDateReportFilter" id="fromDateReportFilter" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.fromDateReportFilter}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="fromDateReportFilterIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="admin.baocaodanhgiaketquathuchienct.label.thoi_gian_bao_cao_den_ngay" /></label>
                                    <div class="col-sm-8">
                                        <div class="input-append date" >
                                            <input name="toDateReportFilter" id="toDateReportFilter" class="prevent_type text-center form-control" type="text"
                                                   value="<fmt:formatDate pattern="${datePattern}" value="${item.toDateReportFilter}" />" placeholder="${symbolDateEmpty}"/>
                                            <span class="add-on" id="toDateReportFilterIcon"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Loại báo cáo:</label>
                                    <div class="col-sm-8">
                                        <label><form:radiobutton id="reportSummaryD" path="reportSummary" value="${Constants.REPORT_DANHGIAKETQUATHUCHIENCT_THEONGAY}"/>&nbsp;Chi tiết theo ngày phát sinh</label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton id="reportSummaryR" path="reportSummary" value="${Constants.REPORT_DANHGIAKETQUATHUCHIENCT_SUMMARY}"/>&nbsp;Tổng hợp</label>
                                    </div>
                                </div>
                            </div>

                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-info" onclick="javascript: submitForm();"><fmt:message key="label.search" /></a>
                                    <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                    <c:if test="${not empty reportData}">
                                        <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <c:if test="${not empty reportData}">
                        <table class="tableVms table-hover" border="1">
                            <tr >
                                <th  class="table_header_center "><fmt:message key="label.stt" /></th>
                                <th class="table_header "><fmt:message key="label.tieu_chi" /></th>
                                <th class="table_header_center "><fmt:message key="label.thue_bao" /></th>
                            </tr>
                            <tr class="even">
                                <td class="text-center">1</td>
                                <td >Số lượng thuê bao tham gia chương trình</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi1_tb}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center">2</td>
                                <td >Số lượng thuê bao tham gia chương trình trên VLR</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi2_tb}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center">3</td>
                                <td >Số tiền tiêu dùng tài khoản chính tháng 3/2015</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi3_td_tkc}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >Thoại</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi3_t}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >SMS</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi3_s}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >Data</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi3_d}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >GTGT</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi3_v}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >RMQT outbound</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi3_r}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >Khác</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi3_k}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center">4</td>
                                <td >Số tiền tiêu dùng tài khoản thưởng tháng 3/2015</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi4_td_tkt}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >Thoại</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi4_t}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >SMS</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi4_s}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >Data</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi4_d}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >GTGT</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi4_v}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >RMQT outbound</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi4_r}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >Khác</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi4_k}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center">5</td>
                                <td >Số tiền tiêu dùng tài khoản chính trong thời gian triển khai</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi5_td_tkc}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >Thoại</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi5_t}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >SMS</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi5_s}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >Data</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi5_d}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >GTGT</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi5_v}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >RMQT outbound</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi5_r}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >Khác</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi5_k}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center">6</td>
                                <td >Số tiền tiêu dùng tài khoản thưởng trong thời gian triển khai</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi6_td_tkt}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >Thoại</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi6_t}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >SMS</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi6_s}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >Data</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi6_d}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >GTGT</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi6_v}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >RMQT outbound</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi6_r}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >Khác</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi6_k}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center">7</td>
                                <td >Số lượng thuê bao đủ điều kiện cấp mã đổi thưởng</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi7_sl_tb_ddk_cap_ma}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >1 mã</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi7_1_ma}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >2 mã</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi7_2_ma}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >3 mã</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi7_3_ma}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >Trên 3 mã</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi7_tren_3_ma}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center">8</td>
                                <td >Số lượng thuê bao đã đổi thưởng</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi8_sl_tb_da_doi_thuong}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >1 mã</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi8_1_phieu}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >2 mã</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi8_2_phieu}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center"></td>
                                <td >3 mã</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi8_3_phieu}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center"></td>
                                <td >Trên 3 mã</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi8_tren_3_phieu}" /></td>
                            </tr>
                            <tr class="even">
                                <td class="text-center">9</td>
                                <td >Số lượng mã đổi thưởng đã phát sinh</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi9_sl_ma_doi_da_ps}" /></td>
                            </tr>
                            <tr class="odd">
                                <td class="text-center">10</td>
                                <td >Số lượng phiếu quà tặng đã đổi</td>
                                <td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="0" value="${reportData.tieuChi10_sl_phieu_qua_tang_da_doi}" /></td>
                            </tr>
                        </table>
                    </c:if>
                    <input id="crudaction" type="hidden" name="crudaction" />
                </form:form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        setActiveMenu4Admin('#qStudent2015ReportDanhGiaKetQuaThucHienCTTab');

        var fromDateThamGiaVar = $("#fromDateThamGia").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromDateThamGiaVar.hide();
                }).data('datepicker');

        $('#fromDateThamGiaIcon').click(function () {
            $('#fromDateThamGia').focus();
            return true;
        });

        var toDateThamGiaVar = $("#toDateThamGia").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toDateThamGiaVar.hide();
                }).data('datepicker');

        $('#toDateThamGiaIcon').click(function () {
            $('#toDateThamGia').focus();
            return true;
        });

        var fromDateReportFilterVar = $("#fromDateReportFilter").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    fromDateReportFilterVar.hide();
                }).data('datepicker');

        $('#fromDateReportFilterIcon').click(function () {
            $('#fromDateReportFilter').focus();
            return true;
        });

        var toDateReportFilterVar = $("#toDateReportFilter").datepicker({
            dateFormat: 'dd/mm/yy',
            onRender: function (date) {
            }}).on('changeDate',function (ev) {
                    toDateReportFilterVar.hide();
                }).data('datepicker');

        $('#toDateReportFilterIcon').click(function () {
            $('#toDateReportFilter').focus();
            return true;
        });
    });

    function submitForm(){
        var allowSubmit = true;
        if($('#reportSummaryD').is(':checked')){
            if($.trim($('#fromDateReportFilter').val()) == ''){
                allowSubmit = false;
                bootbox.alert('<fmt:message key="label.alert_title" />', 'Cần chọn ngày bắt đầu phát sinh nhằm giảm thiểu những ngày không có phát sinh dữ liệu để tạo report nhanh hơn.', '<fmt:message key="label.dong_y" />', function(r){});
            }
        }
        if(allowSubmit){
            $('#crudaction').val('search');
            $('#searchForm').submit();
        }
    }

    function export2Excel(){
        $('#crudaction').val('export');
        $('#searchForm').submit();
    }

    function reset(){
        $('#fromDateThamGia').val('');
        $('#toDateThamGia').val('');
        $('#fromDateReportFilter').val('');
        $('#toDateReportFilter').val('');
    }

    function ajaxGetDistrictList() {
        <security:authorize ifAnyGranted="ADMIN">
        $('#districtMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#districtMenu');
        $.ajax({
            url: '<c:url value="/ajax/qStudent/getByBranchId.html" />',
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
        </security:authorize>
    }
</script>
</body>