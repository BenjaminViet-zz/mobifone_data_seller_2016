<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title><fmt:message key="admin.baocaotonghopchuongtrinhkpp.heading_page" /></title>
    <meta name="heading" content="<fmt:message key="admin.baocaotonghopchuongtrinhkpp.heading_page" />"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>

<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/baocaotonghopketquachuongtrinh_kpp_theoquanhuyen.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="admin.baocaotonghopchuongtrinhkpp.heading_page" /></h2>

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
                                        <form:select id="branchMenu" path="branch_Id" cssStyle="width: 250px;" onchange="javascript: ajaxGetDistrictList();">
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
                                    <label class="col-sm-4 control-label"><fmt:message key="label.quan_huyen" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="districtMenu" path="district_Id" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${districtList}" var="district">
                                                <option <c:if test="${item.district_Id eq district.district_Id}">selected="true"</c:if> value="${district.district_Id}">${district.district_name}</option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><fmt:message key="label.giai_doan" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="giaiDoanMenu" path="giaiDoan" cssStyle="width: 250px;" onchange="javascript: setRange4DataPicker();">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <c:forEach items="${giaiDoanList}" var="giaiDoan">
                                                <option <c:if test="${item.giaiDoan eq giaiDoan}">selected="true"</c:if> value="${giaiDoan}">${giaiDoan}</option>
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
                                    <label class="col-sm-4 control-label"><fmt:message key="label.trang_thai_tra_dat" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="trangThaiDatMenu" path="trang_thai_dat" cssStyle="width: 250px;">
                                            <option value=""><fmt:message key="label.all" /></option>
                                            <option <c:if test="${item.trang_thai_dat eq Constants.BC_TONG_HOP_KQ_CHUONG_TRINH_KPP_TRANG_THAI_TRA_THUONG_KHONG_DAT}">selected="true"</c:if> value="${Constants.BC_TONG_HOP_KQ_CHUONG_TRINH_KPP_TRANG_THAI_TRA_THUONG_KHONG_DAT}"><fmt:message key="label.khong_dat" /></option>
                                            <option <c:if test="${item.trang_thai_dat eq Constants.BC_TONG_HOP_KQ_CHUONG_TRINH_KPP_TRANG_THAI_TRA_THUONG_DAT}">selected="true"</c:if> value="${Constants.BC_TONG_HOP_KQ_CHUONG_TRINH_KPP_TRANG_THAI_TRA_THUONG_DAT}"><fmt:message key="label.dat" /></option>
                                        </form:select>
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
                        <div id="reportTableContainer" style="width: 100%;">
                            <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                           partialList="true" sort="external" size="${items.totalItems }" defaultsort="0"
                                           id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 2550px;">
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                    ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" property="branch_Name" titleKey="" class="nowrap" style="width: 250px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="district_Name" titleKey="" class="nowrap" style="width: 150px;" />
                                <display:column headerClass="table_header_center" sortable="false" property="giaiDoan" titleKey="label.giai_doan" class="nowrap text-center" style="width: 100px;" />
                                <c:if test="${not empty item.fromDateTime || not empty item.toDateTime}">
                                    <display:column headerClass="table_header_center" sortable="false" titleKey="label.ngay" class="nowrap text-center" style="width: 50px;" >
                                        <fmt:formatDate value="${tableList.ngay}" pattern="${datePattern}" />
                                    </display:column>
                                </c:if>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.sl_dbh_tham_gia" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.soLuongDBHThamGia}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.sl_dbh_dat" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.soLuongDBHDat}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.sl_btg_mua_tu_mobifone" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.soLuongBTG_MuaTuMobifone}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.doanh_thu_mua_the" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.doanhThuMuaThe}" maxFractionDigits="2" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.sl_vas" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.soLuong_VAS}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.doanh_thu" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.doanhThu_VAS}" maxFractionDigits="2" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.sl_ton_ban_truc_tiep_btg" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.soLuongTon_BanTrucTiepBTG}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.sl_moi_ban_truc_tiep_btg" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.soLuongMoi_BanTrucTiepBTG}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.cuoc_ps_bo_ton_ban_truc_tiep_btg" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.cuocPSBoTon_BanTrucTiepBTG}" maxFractionDigits="2" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.cuoc_ps_bo_moi_ban_truc_tiep_btg" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.cuocPSBoMoi_BanTrucTiepBTG}" maxFractionDigits="2" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.sl_gioi_thieu_kh" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.soLuong_GioiThieuKH}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.sl_ps_cuoc_gioi_thieu_kh" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.soLuongCuocPS_GioiThieuKH}" />
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="admin.baocaotonghopchuongtrinhkpp.label.cuoc_ps_gioi_thieu_kh" class="nowrap text-right" style="width: 150px;" >
                                    <fmt:formatNumber type="number" value="${tableList.cuocPS_GioiThieuKH}" maxFractionDigits="2" />
                                </display:column>
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
        setActiveMenu4Admin('#tdcg2014ReportKetQuaCTKPPTab');

        if(${not empty item.giaiDoan}){
            var month = eval($.trim('${item.giaiDoan}')),
                    startDate = new Date(2014, month - 1, 1),
                    endDate = new Date(2014, month, 0);
            initDatePickerWithMinAndMaxDate('#fromDate', startDate, endDate);
            initDatePickerWithMinAndMaxDate('#toDate', startDate, endDate);
        }else{
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
        }

        $('#reportTableContainer').mCustomScrollbar({axis:"x"});

        rebuildTableHeaders();
        checkMovePageLinks();
    });

    function rebuildTableHeaders(){
        if(${not empty items.listResult}){
            var ngayHeader = ${not empty item.fromDateTime || not empty item.toDateTime} ? "<th rowspan='2' class='table_header_center'>Ngày</th>" : "";

            $('#tableList thead tr').remove();
            $('#tableList thead').html("<tr>" +
                                            "<th rowspan='2' class='table_header_center'>STT</th>" +
                                            "<th rowspan='2' class='table_header_center'>Chi Nhánh</th>" +
                                            "<th rowspan='2' class='table_header_center'>Quận/Huyện</th>" +
                                            "<th rowspan='2' class='table_header_center'>Giai Đoạn</th>" +
                                            ngayHeader +
                                            "<th rowspan='2' class='table_header_center'>SL ĐBH Tham Gia</th>" +
                                            "<th rowspan='2' class='table_header_center'>SL ĐBH Đạt</th>" +
                                            "<th rowspan='2' class='table_header_center'>SL BTG Mua Từ MobiFone</th>" +
                                            "<th rowspan='2' class='table_header_center'>Doanh thu mua thẻ</th>" +
                                            "<th colspan='2' class='table_header_center'>VAS</th>" +
                                            "<th colspan='4' class='table_header_center'>Bán Trực Tiếp BTG</th>" +
                                            "<th colspan='3' class='table_header_center'>Giới Thiệu Khách Hàng</th>" +
                                        "</tr>" +
                                        "<tr>" +
                                            "<th class='table_header_center'>Số Lượng</th>" +
                                            "<th class='table_header_center'>Doanh Thu</th>" +
                                            "<th class='table_header_center'>Số Lượng Tồn</th>" +
                                            "<th class='table_header_center'>Số Lượng Mới</th>" +
                                            "<th class='table_header_center'>Cước PS (DTTT) Bộ Tồn</th>" +
                                            "<th class='table_header_center'>Cước PS (DTTT) Bộ Mới</th>" +
                                            "<th class='table_header_center'>Số Lượng</th>" +
                                            "<th class='table_header_center'>SL Phát Sinh Cước</th>" +
                                            "<th class='table_header_center'>Cước Phát Sinh</th>" +
                                        "</tr>");
        }
    }

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
            $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
        }
    }

    function ajaxGetDistrictList(){
        $('#districtMenu').find('option:not(:first-child)').remove();
        selectFirstItemSelect2('#districtMenu');
        $.ajax({
            url: '<c:url value="/ajax/admin/getDistrictListByBranch.html" />',
            type: 'get',
            dataType: 'json',
            data: {branchId: $('#branchMenu').val()},
            success: function(res){
                if(res.districtList != null){
                    $(res.districtList).each(function(index, el){
                        $('#districtMenu').append("<option value=" +el.district_Id+ ">" +el.district_name+ "</option>");
                    });
                }
            }
        });
    }

    function setRange4DataPicker(){
        $('#fromDate').datepicker( "destroy" );
        $('#toDate').datepicker( "destroy" );
        if($('#giaiDoanMenu').val() != ''){
            var month = eval($.trim($('#giaiDoanMenu').val())),
                    startDate = new Date(2014, month - 1, 1),
                    endDate = new Date(2014, month, 0);
            initDatePickerWithMinAndMaxDate('#fromDate', startDate, endDate);
            initDatePickerWithMinAndMaxDate('#toDate', startDate, endDate);
        }else{
            var startDate = new Date(2000, 1, 1),
                    endDate = new Date(2015, 1, 0);
            initDatePickerWithMinAndMaxDate('#fromDate', startDate, endDate);
            initDatePickerWithMinAndMaxDate('#toDate', startDate, endDate);
        }
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
        selectFirstItemSelect2('#districtMenu');
        selectFirstItemSelect2('#trangThaiDatMenu');
        selectFirstItemSelect2('#giaiDoanMenu');
        $('#fromDate').val('');
        $('#toDate').val('');
    }
</script>
</body>