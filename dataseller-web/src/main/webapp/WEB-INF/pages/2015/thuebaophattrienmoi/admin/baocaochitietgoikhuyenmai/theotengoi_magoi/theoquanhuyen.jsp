<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 3/2/15
  Time: 2:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<head>
    <title><fmt:message key="baocaochitietgoikhuyenmai.headingpage" /></title>
    <meta name="heading" content="<fmt:message key="baocaochitietgoikhuyenmai.headingpage" />"/>
    <link href="<c:url value="/themes/promotion/css/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
</head>
<c:set var="prefix" value="/baocao" />
<security:authorize ifAnyGranted="ADMIN">
    <c:set var="prefix" value="/admin" />
</security:authorize>
<security:authorize ifAnyGranted="CHINHANH">
    <c:set var="prefix" value="/chinhanh" />
</security:authorize>
<c:url var="formUrl" value="${prefix}/thuebaophattrienmoi/baocaochitietgoikm_theomagoivatengoi_theoquanhuyen.html"/>
<body>
<div class="pageheader">
    <h2><i class="fa fa-edit"></i><fmt:message key="baocaochitietgoikhuyenmai.headingpage" /></h2>

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
                                    <label class="col-sm-2 control-label"><fmt:message key="label.loai_goi_KM" /></label>
                                    <div class="col-sm-8">
                                        <form:select id="promPackageMenu" path="pojo.promPackage.package_Id" cssStyle="width: 250px;">
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
                                    <label class="col-sm-2 control-label"><fmt:message key="label.chi_nhanh" /></label>
                                    <div class="col-sm-8">
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
                                    <label class="col-sm-2 control-label"><fmt:message key="label.quan_huyen" /></label>
                                    <div class="col-sm-8">
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
                                    <label class="col-sm-2 control-label"><fmt:message key="label.tinhtrang" /></label>
                                    <div class="col-sm-8">
                                        <label><form:radiobutton path="pojo.tinhTrang" value="-1"/>&nbsp;<fmt:message key="label.all" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.tinhTrang" value="${Constants.TINH_TRANG_THANH_CONG}"/>&nbsp;<fmt:message key="label.thanh_cong" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.tinhTrang" value="${Constants.TINH_TRANG_DUOC_CHI_TRA}"/>&nbsp;<fmt:message key="label.chi_tra" /></label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<label><form:radiobutton path="pojo.tinhTrang" value="${Constants.TINH_TRANG_KHONG_THANH_CONG}"/>&nbsp;<fmt:message key="label.khong_thanh_cong" /></label>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <div class="col-sm-8">
                                        <a class="btn btn-info" onclick="javascript: filterForm();"><fmt:message key="label.search" /></a>
                                        <a class="btn btn-info" onclick="javascript: reset();"><fmt:message key="label.reset" /></a>
                                        <c:if test="${not empty items.listResult}">
                                            <a class="btn btn-info" onclick="javascript: export2Excel();"><fmt:message key="label.export" /></a>
                                        </c:if>
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
                                           id="tableList" pagesize="${items.reportMaxPageItems}" export="false" class="tableVms table-hover" style="margin: 1em 0 1.5em; width: 1100px;">
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.stt" class="text-center" style="width: 50px;" >
                                    ${tableList_rowNum + (page * Constants.REPORT_MAXPAGEITEMS)}
                                </display:column>
                                <display:column headerClass="table_header_center nowrap" sortable="false" property="branch.branch_name" titleKey="label.chi_nhanh" class="nowrap" style="width: 150px;" />
                                <display:column headerClass="table_header_center nowrap" sortable="false" property="district.district_name" titleKey="label.quan_huyen" class="nowrap" style="width: 150px;" />
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.thang_dang_ky" class="nowrap text-center" style="width: 150px;" >
                                    <fmt:formatDate value="${tableList.thoiGianDK}" pattern="MM/yyyy" />
                                </display:column>
                                <display:column headerClass="table_header_center nowrap" sortable="false" property="promPackage.package_Name" titleKey="label.goi_cuoc" class="nowrap text-center" style="width: 150px;" />
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.thoi_gian_dang_ky" class="nowrap text-center" style="width: 150px;" >
                                    <fmt:formatDate value="${tableList.thoiGianDK}" pattern="${dateTimePattern}" />
                                </display:column>
                                <display:column headerClass="table_header_center nowrap" sortable="false" titleKey="label.tinhtrang" class="nowrap" style="width: 150px;" >
                                    <c:choose>
                                        <c:when test="${item.pojo.tinhTrang ne -1}">
                                            <c:if test="${item.pojo.tinhTrang eq 0}">
                                                <fmt:message key="label.khong_thanh_cong"/>
                                            </c:if>
                                            <c:if test="${item.pojo.tinhTrang eq 1}">
                                                <fmt:message key="label.thanh_cong"/>
                                            </c:if>
                                            <c:if test="${item.pojo.tinhTrang eq 2}">
                                                <fmt:message key="label.duoc_chi_tra"/>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${not empty tableList.tinhTrang && tableList.tinhTrang eq Constants.TINH_TRANG_KHONG_THANH_CONG}">
                                                    <fmt:message key="label.khong_thanh_cong" />
                                                </c:when>
                                                <c:when test="${not empty tableList.tinhTrang && tableList.tinhTrang eq Constants.TINH_TRANG_THANH_CONG }">
                                                    <c:choose>
                                                        <c:when test="${not empty tableList.promConditionStatus && tableList.promConditionStatus eq Constants.THANH_CONG_KHONG_DUOC_CHI_TRA}">
                                                            <fmt:message key="label.thanh_cong_nhung_khong_duoc_chi_tra"/>
                                                        </c:when>
                                                        <c:when test="${not empty tableList.promConditionStatus && tableList.promConditionStatus eq Constants.THANH_CONG_DUOC_CHI_TRA}">
                                                            <fmt:message key="label.duoc_chi_tra"/>
                                                        </c:when>
                                                    </c:choose>
                                                </c:when>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column headerClass="table_header_center" sortable="false" titleKey="label.lydo" style="width: 150px;" >
                                    <c:choose>
                                        <c:when test="${not empty tableList.tinhTrang && tableList.tinhTrang eq Constants.TINH_TRANG_KHONG_THANH_CONG}">
                                            ${tableList.transError}
                                        </c:when>
                                        <c:when test="${not empty tableList.tinhTrang && tableList.tinhTrang eq Constants.TINH_TRANG_THANH_CONG}">
                                            ${tableList.promConditionError}
                                        </c:when>
                                    </c:choose>
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
        setActiveMenu4Admin('#tbptm2015ReportChiTietTheoTenGoiAndMaGoiTab');

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
//            rebuildTableHeaders();
        checkMovePageLinks();
    });

    function rebuildTableHeaders(){
        $('#tableList thead tr').remove();
        $('#tableList thead').html("<tr>" +
                "<th rowspan='2' class='table_header_center '>STT</th>" +
                "<th rowspan='2' class='table_header_center '>Chi nhánh</th>" +
                "<th rowspan='2' class='table_header_center '>Quận huyện</th>" +
                "<th colspan='2' class='table_header_center '>Số thuê bao</th>" +
                "<th rowspan='2' class='table_header_center '>Gói Cước</th>" +
                "<th rowspan='2' class='table_header_center '>Tháng đăng ký</th>" +
                "<th rowspan='2' class='table_header_center '>Thời gian đăng ký</th>" +
                "<th rowspan='2' class='table_header_center '>Tình trạng</th>" +
                "<th rowspan='2' class='table_header_center '>Lý do</th>" +
                "</tr>" +
                "<tr>" +
                "<th class='table_header_center '>Mới</th>" +
                "<th class='table_header_center '>Tồn</th>" +
                "</tr>");
    }

    function checkMovePageLinks(){
        if(${not empty items.listResult}){
            $('#reportTableContainer .pagebanner').appendTo('#linksContainer');
            $('#reportTableContainer .pagelinks').appendTo('#linksContainer');
        }
    }

    function filterForm(){
        $('#crudaction').val('search');
        $('#reportForm').submit();
    }

    function reset(){
        selectFirstItemSelect2('#promPackageMenu')
        selectFirstItemSelect2('#branchMenu')
        selectFirstItemSelect2('#districtMenu')
        $('#fromDate').val('');
        $('#toDate').val('');
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
</script>
</body>